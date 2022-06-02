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
package org.sonar.plugins.groovy.surefire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.plugins.groovy.foundation.Groovy;

public class GroovySurefireParserTest {

  private GroovySurefireParser parser;
  private Groovy groovy;

  @Before
  public void before() {
    FileSystem fs = new DefaultFileSystem(Paths.get("."));

    MapSettings settings = new MapSettings();
    settings.setProperty(Groovy.FILE_SUFFIXES_KEY, ".groovy,grvy");
    groovy = new Groovy(settings.asConfig());

    parser = spy(new GroovySurefireParser(groovy, fs));

    doAnswer(
            invocation ->
                TestInputFileBuilder.create("", (String) invocation.getArguments()[0]).build())
        .when(parser)
        .getUnitTestInputFile(anyString());
  }

  @Test
  public void shouldStoreZeroTestsWhenDirectoriesIsEmpty() {
    SensorContext context = mock(SensorContext.class);
    parser.collect(context, Collections.emptyList());
    verify(context, never()).newMeasure();
  }

  @Test
  public void shouldStoreZeroTestsWhenDirectoryIsNonExisting() {
    SensorContext context = mock(SensorContext.class);
    parser.collect(context, getDir("nonExistingReportsDirectory"));
    verify(context, never()).newMeasure();
  }

  @Test
  public void shouldStoreZeroTestsWhenDirectoryIsAFile() {
    SensorContext context = mock(SensorContext.class);
    parser.collect(context, getDir("file.txt"));
    verify(context, never()).newMeasure();
  }

  @Test
  public void shouldAggregateReports() {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));

    parser.collect(context, getDir("multipleReports"));

    // Only 5 tests measures should be stored, no more: the TESTS-AllTests.xml must not be read as
    // there's 1 file result per unit test (SONAR-2841).
    assertThat(context.measures(":ch.hortis.sonar.mvn.mc.MetricsCollectorRegistryTest")).hasSize(5);
    assertThat(context.measures(":ch.hortis.sonar.mvn.mc.CloverCollectorTest")).hasSize(5);
    assertThat(context.measures(":ch.hortis.sonar.mvn.mc.CheckstyleCollectorTest")).hasSize(5);
    assertThat(context.measures(":ch.hortis.sonar.mvn.SonarMojoTest")).hasSize(5);
    assertThat(context.measures(":ch.hortis.sonar.mvn.mc.JDependsCollectorTest")).hasSize(5);
    assertThat(context.measures(":ch.hortis.sonar.mvn.mc.JavaNCSSCollectorTest")).hasSize(5);
  }

  // SONAR-2841: if there's only a test suite report, then it should be read.
  @Test
  public void shouldUseTestSuiteReportIfAlone() {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));

    parser.collect(context, getDir("onlyTestSuiteReport"));

    assertThat(context.measures(":org.sonar.SecondTest")).hasSize(5);
    assertThat(context.measures(":org.sonar.JavaNCSSCollectorTest")).hasSize(5);
  }

  /** See http://jira.codehaus.org/browse/SONAR-2371 */
  @Test
  public void shouldInsertZeroWhenNoReports() {
    SensorContext context = mock(SensorContext.class);
    parser.collect(context, getDir("noReports"));
    verify(context, never()).newMeasure();
  }

  @Test
  public void shouldNotInsertZeroOnFiles() {
    SensorContext context = mock(SensorContext.class);
    parser.collect(context, getDir("noTests"));

    verify(context, never()).newMeasure();
  }

  @Test
  public void shouldMergeInnerClasses() {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    parser.collect(context, getDir("innerClasses"));

    assertThat(
            context
                .measure(
                    ":org.apache.commons.collections.bidimap.AbstractTestBidiMap",
                    CoreMetrics.TESTS)
                .value())
        .isEqualTo(7);
    assertThat(
            context
                .measure(
                    ":org.apache.commons.collections.bidimap.AbstractTestBidiMap",
                    CoreMetrics.TEST_ERRORS)
                .value())
        .isEqualTo(1);
    assertThat(
            context.measures(
                ":org.apache.commons.collections.bidimap.AbstractTestBidiMap$TestBidiMapEntrySet"))
        .isEmpty();
  }

  @Test
  public void shouldMergeNestedInnerClasses() {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    parser.collect(context, getDir("nestedInnerClasses"));

    assertThat(
            context
                .measure(":org.sonar.plugins.surefire.NestedInnerTest", CoreMetrics.TESTS)
                .value())
        .isEqualTo(3);
  }

  @Test
  public void shouldNotCountNegativeTests() {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    parser.collect(context, getDir("negativeTestTime"));
    // Test times : -1.120, 0.644, 0.015 -> computed time : 0.659, ignore negative time.

    assertThat(context.measure(":java.Foo", CoreMetrics.SKIPPED_TESTS).value()).isEqualTo(0);
    assertThat(context.measure(":java.Foo", CoreMetrics.TESTS).value()).isEqualTo(6);
    assertThat(context.measure(":java.Foo", CoreMetrics.TEST_ERRORS).value()).isEqualTo(0);
    assertThat(context.measure(":java.Foo", CoreMetrics.TEST_FAILURES).value()).isEqualTo(0);
    assertThat(context.measure(":java.Foo", CoreMetrics.TEST_EXECUTION_TIME).value())
        .isEqualTo(659);
  }

  private List<File> getDir(String dirname) {
    return Collections.singletonList(
        new File(
            "src/test/resources/org/sonar/plugins/groovy/surefire/SurefireParserTest/" + dirname));
  }

  @Test
  public void shouldGenerateCorrectPredicate() {
    DefaultFileSystem fs = new DefaultFileSystem(Paths.get("."));
    InputFile inputFile =
        TestInputFileBuilder.create("", "src/test/org/sonar/JavaNCSSCollectorTest.groovy")
            .setLanguage(Groovy.KEY)
            .setType(Type.TEST)
            .build();
    fs.add(inputFile);

    parser = new GroovySurefireParser(groovy, fs);

    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context.setFileSystem(fs);
    parser.collect(context, getDir("onlyTestSuiteReport"));

    assertThat(
            context
                .measure(":src/test/org/sonar/JavaNCSSCollectorTest.groovy", CoreMetrics.TESTS)
                .value())
        .isEqualTo(11);
  }
}
