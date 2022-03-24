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
package org.sonar.plugins.groovy.jacoco;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.notifications.AnalysisWarnings;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.plugins.groovy.TestUtils;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.foundation.GroovyFileSystem;

public class JaCoCoOverallSensorTest {

  @Rule public final TemporaryFolder tmpDir = new TemporaryFolder();
  @Rule public final MockitoRule mockito = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

  private JaCoCoSensor sensor;
  private InputFile inputFile;
  private MapSettings settings = new MapSettings();
  private SensorContextTester context;
  @Mock private AnalysisWarnings analysisWarnings;

  @Before
  public void before() throws IOException {
    Path outputDir = tmpDir.newFolder().toPath();

    Files.copy(
        TestUtils.getResource(getClass(), "../JaCoCoSensor_0_7_5/jacoco-ut.exec"),
        outputDir.resolve("jacoco-ut.exec"));
    Files.copy(
        TestUtils.getResource(getClass(), "../JaCoCoSensor_0_7_5/jacoco-ut.exec"),
        outputDir.resolve("jacoco-it.exec"));
    Files.copy(
        TestUtils.getResource(getClass(), "../Hello.class.toCopy"),
        outputDir.resolve("Hello.class"));
    Files.copy(
        TestUtils.getResource(getClass(), "../Hello$InnerClass.class.toCopy"),
        outputDir.resolve("Hello$InnerClass.class"));

    settings.setProperty(JaCoCoConfiguration.SONAR_GROOVY_BINARIES, ".");

    context = SensorContextTester.create(outputDir);
    context.fileSystem().setWorkDir(tmpDir.newFolder().toPath());
    context.setSettings(settings);

    inputFile =
        TestInputFileBuilder.create("", "example/Hello.groovy")
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .setLines(50)
            .build();
    context.fileSystem().add(inputFile);

    JaCoCoConfiguration configuration =
        new JaCoCoConfiguration(settings.asConfig(), context.fileSystem());
    sensor =
        new JaCoCoSensor(
            configuration,
            new GroovyFileSystem(context.fileSystem()),
            new PathResolver(),
            settings.asConfig(),
            analysisWarnings);
  }

  @Test
  public void testDescription() {
    DefaultSensorDescriptor defaultSensorDescriptor = new DefaultSensorDescriptor();
    sensor.describe(defaultSensorDescriptor);
    assertThat(defaultSensorDescriptor.languages()).containsOnly(Groovy.KEY);
  }

  @Test
  public void shouldExecuteOnProjectWithItExisting() {
    configReports(false, true);
    sensor.execute(context);
    assertThat(context.coveredConditions(inputFile.key(), 14), is(equalTo(2)));
    verify(analysisWarnings).addUnique(anyString());
  }

  @Test
  public void shouldExecuteOnProjectWithUtExisting() {
    configReports(true, false);
    sensor.execute(context);
    assertThat(context.coveredConditions(inputFile.key(), 14), is(equalTo(2)));
    verify(analysisWarnings).addUnique(anyString());
  }

  @Test
  public void shouldNotExecuteOnProjectWithoutExecutionData() {
    configReports(false, false);
    sensor.execute(context);
    assertNull(context.coveredConditions(inputFile.key(), 14));
    verify(analysisWarnings, never()).addUnique(anyString());
  }

  @Test
  public void shouldNotExecuteIfJaCoCoXmlConfigured() {
    settings.setProperty(JaCoCoSensor.JACOCO_XML_PROPERTY, "report.xml");

    configReports(true, true);
    sensor.execute(context);
    assertNull(context.coveredConditions(inputFile.key(), 14));
    verify(analysisWarnings, never()).addUnique(anyString());
  }

  private void configReports(boolean utReport, boolean itReport) {
    settings.setProperty(
        JaCoCoConfiguration.REPORT_PATH_PROPERTY, utReport ? "jacoco-ut.exec" : "notexist-ut.exec");
    settings.setProperty(
        JaCoCoConfiguration.IT_REPORT_PATH_PROPERTY,
        itReport ? "jacoco-it.exec" : "notexist-it.exec");
  }
}
