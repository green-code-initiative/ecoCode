/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2021 SonarQube Community
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.groovy.codenarc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.foundation.GroovyFileSystem;

public class CodeNarcSensorTest {
  @Rule public TemporaryFolder temp = new TemporaryFolder();

  private SensorContextTester sensorContextTester;
  private MapSettings settings = new MapSettings();

  @Before
  public void setUp() throws Exception {
    sensorContextTester = SensorContextTester.create(temp.newFolder());
    sensorContextTester.fileSystem().setWorkDir(temp.newFolder().toPath());

    sensorContextTester.setSettings(settings);
  }

  @Test
  public void test_description() {
    CodeNarcSensor sensor =
        new CodeNarcSensor(null, new GroovyFileSystem(sensorContextTester.fileSystem()));
    DefaultSensorDescriptor defaultSensorDescriptor = new DefaultSensorDescriptor();
    sensor.describe(defaultSensorDescriptor);
    assertThat(defaultSensorDescriptor.languages()).containsOnly(Groovy.KEY);
  }

  @Test
  public void should_parse() throws Exception {

    ActiveRulesBuilderWrapper activeRulesBuilder =
        new ActiveRulesBuilderWrapper()
            .addRule("BooleanInstantiation")
            .addRule("DuplicateImport")
            .addRule("EmptyCatchBlock")
            .addRule("EmptyElseBlock")
            .addRule("EmptyFinallyBlock")
            .addRule("EmptyForStatement")
            .addRule("EmptyIfStatement")
            .addRule("EmptyTryBlock")
            .addRule("EmptyWhileStatement")
            .addRule("ImportFromSamePackage")
            .addRule("ReturnFromFinallyBlock")
            .addRule("StringInstantiation")
            .addRule("ThrowExceptionFromFinallyBlock")
            .addRule("UnnecessaryGroovyImport")
            .addRule("UnusedImport");
    sensorContextTester.setActiveRules(activeRulesBuilder.build());

    Path reportUpdated = getReportWithUpdatedSourceDir();
    settings.setProperty(
        CodeNarcSensor.CODENARC_REPORT_PATHS, reportUpdated.toAbsolutePath().toString());

    addFileWithFakeContent("src/org/codenarc/sample/domain/SampleDomain.groovy");
    addFileWithFakeContent("src/org/codenarc/sample/service/NewService.groovy");
    addFileWithFakeContent("src/org/codenarc/sample/service/OtherService.groovy");
    addFileWithFakeContent("src/org/codenarc/sample/service/SampleService.groovy");

    CodeNarcSensor sensor =
        new CodeNarcSensor(
            sensorContextTester.activeRules(),
            new GroovyFileSystem(sensorContextTester.fileSystem()));
    sensor.execute(sensorContextTester);

    assertThat(sensorContextTester.allIssues()).hasSize(17);
  }

  @Test
  public void should_parse_but_not_add_issue_if_rule_not_found() throws Exception {

    ActiveRulesBuilderWrapper activeRulesBuilder =
        new ActiveRulesBuilderWrapper().addRule("UnknownRule");
    sensorContextTester.setActiveRules(activeRulesBuilder.build());

    Path reportUpdated = getReportWithUpdatedSourceDir();
    settings.setProperty(
        CodeNarcSensor.CODENARC_REPORT_PATHS, reportUpdated.toAbsolutePath().toString());

    addFileWithFakeContent("src/org/codenarc/sample/domain/SampleDomain.groovy");
    addFileWithFakeContent("src/org/codenarc/sample/service/NewService.groovy");
    addFileWithFakeContent("src/org/codenarc/sample/service/OtherService.groovy");
    addFileWithFakeContent("src/org/codenarc/sample/service/SampleService.groovy");

    CodeNarcSensor sensor =
        new CodeNarcSensor(
            sensorContextTester.activeRules(),
            new GroovyFileSystem(sensorContextTester.fileSystem()));
    sensor.execute(sensorContextTester);

    assertThat(sensorContextTester.allIssues()).isEmpty();
  }

  @Test
  public void should_parse_but_not_add_issue_if_inputFile_not_found() throws Exception {

    ActiveRulesBuilderWrapper activeRulesBuilder =
        new ActiveRulesBuilderWrapper().addRule("BooleanInstantiation");
    sensorContextTester.setActiveRules(activeRulesBuilder.build());

    Path reportUpdated = getReportWithUpdatedSourceDir();
    settings.setProperty(
        CodeNarcSensor.CODENARC_REPORT_PATHS, reportUpdated.toAbsolutePath().toString());

    addFileWithFakeContent("src/org/codenarc/sample/domain/Unknown.groovy");

    CodeNarcSensor sensor =
        new CodeNarcSensor(
            sensorContextTester.activeRules(),
            new GroovyFileSystem(sensorContextTester.fileSystem()));
    sensor.execute(sensorContextTester);

    assertThat(sensorContextTester.allIssues()).isEmpty();
  }

  @Test
  public void should_run_code_narc() throws IOException {

    addFileWithContent("src/sample.groovy", "package source\nclass SourceFile1 {\n}");

    ActiveRulesBuilderWrapper activeRulesBuilder =
        new ActiveRulesBuilderWrapper()
            .addRule("org.codenarc.rule.basic.EmptyClassRule")
            .setInternalKey("EmptyClass");
    sensorContextTester.setActiveRules(activeRulesBuilder.build());

    CodeNarcSensor sensor =
        new CodeNarcSensor(
            sensorContextTester.activeRules(),
            new GroovyFileSystem(sensorContextTester.fileSystem()));
    sensor.execute(sensorContextTester);

    assertThat(sensorContextTester.allIssues()).hasSize(1);
  }

  @Test
  public void should_do_nothing_when_can_not_find_report_path() throws Exception {

    settings.setProperty(CodeNarcSensor.CODENARC_REPORT_PATHS, "../missing_file.xml");

    addFileWithFakeContent("src/org/codenarc/sample/domain/Unknown.groovy");

    ActiveRulesBuilderWrapper activeRulesBuilder =
        new ActiveRulesBuilderWrapper().addRule("org.codenarc.rule.basic.EmptyClassRule");
    sensorContextTester.setActiveRules(activeRulesBuilder.build());

    CodeNarcSensor sensor =
        new CodeNarcSensor(
            sensorContextTester.activeRules(),
            new GroovyFileSystem(sensorContextTester.fileSystem()));
    sensor.execute(sensorContextTester);

    assertThat(sensorContextTester.allIssues()).isEmpty();
  }

  @Test
  public void should_run_code_narc_with_multiple_files() throws IOException {

    addFileWithContent("src/sample.groovy", "package source\nclass SourceFile1 {\n}");
    addFileWithContent("src/foo/bar/qix/sample.groovy", "package source\nclass SourceFile1 {\n}");

    ActiveRulesBuilderWrapper activeRulesBuilder =
        new ActiveRulesBuilderWrapper()
            .addRule("org.codenarc.rule.basic.EmptyClassRule")
            .setInternalKey("EmptyClass");
    sensorContextTester.setActiveRules(activeRulesBuilder.build());

    CodeNarcSensor sensor =
        new CodeNarcSensor(
            sensorContextTester.activeRules(),
            new GroovyFileSystem(sensorContextTester.fileSystem()));
    sensor.execute(sensorContextTester);

    assertThat(sensorContextTester.allIssues()).hasSize(2);
  }

  private Path getReportWithUpdatedSourceDir() throws IOException {
    Path reportUpdated = temp.newFile().toPath();
    String newSourceDir =
        sensorContextTester
            .fileSystem()
            .baseDirPath()
            .resolve("src")
            .toAbsolutePath()
            .toString()
            .replaceAll("\\\\", "/");
    try (InputStream report = getClass().getResourceAsStream("parsing/sample.xml");
        Writer reportWriter = Files.newBufferedWriter(reportUpdated)) {
      IOUtils.write(
          IOUtils.toString(report, StandardCharsets.UTF_8)
              .replaceAll(Pattern.quote("[sourcedir]"), newSourceDir),
          reportWriter);
    }
    return reportUpdated;
  }

  private void addFileWithFakeContent(String path)
      throws UnsupportedEncodingException, IOException {
    File sampleFile = FileUtils.toFile(getClass().getResource("parsing/Sample.groovy"));
    sensorContextTester
        .fileSystem()
        .add(
            TestInputFileBuilder.create(sensorContextTester.module().key(), path)
                .setLanguage(Groovy.KEY)
                .setType(Type.MAIN)
                .setContents(new String(Files.readAllBytes(sampleFile.toPath()), "UTF-8"))
                .build());
  }

  private void addFileWithContent(String path, String content) {
    InputFile inputFile =
        TestInputFileBuilder.create(sensorContextTester.module().key(), path)
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .setContents(content)
            .build();
    sensorContextTester.fileSystem().add(inputFile);
  }
}
