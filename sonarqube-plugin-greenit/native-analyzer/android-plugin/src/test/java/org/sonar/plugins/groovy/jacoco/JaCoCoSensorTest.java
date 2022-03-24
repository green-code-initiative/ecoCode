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
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.notifications.AnalysisWarnings;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.plugins.groovy.TestUtils;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.foundation.GroovyFileSystem;

public class JaCoCoSensorTest {

  @Rule public final TemporaryFolder tmpDir = new TemporaryFolder();

  private MapSettings settings = TestUtils.jacocoDefaultSettings();
  private JaCoCoSensor sensor;

  private void initWithJaCoCoVersion(String jacocoVersion) throws IOException {
    Path outputDir = tmpDir.newFolder().toPath();

    Files.copy(
        TestUtils.getResource(getClass(), "../" + jacocoVersion + "/jacoco-ut.exec"),
        outputDir.resolve("jacoco-ut.exec"));
    Files.copy(
        TestUtils.getResource(getClass(), "../Hello.class.toCopy"),
        outputDir.resolve("Hello.class"));
    Files.copy(
        TestUtils.getResource(getClass(), "../Hello$InnerClass.class.toCopy"),
        outputDir.resolve("Hello$InnerClass.class"));

    settings.setProperty(JaCoCoConfiguration.SONAR_GROOVY_BINARIES, ".");
    settings.setProperty(JaCoCoConfiguration.REPORT_PATH_PROPERTY, "jacoco-ut.exec");

    DefaultFileSystem fileSystem = new DefaultFileSystem(outputDir);
    InputFile inputFile =
        TestInputFileBuilder.create("", "example/Hello.groovy")
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .setLines(50)
            .build();
    fileSystem.add(inputFile);
    JaCoCoConfiguration configuration = new JaCoCoConfiguration(settings.asConfig(), fileSystem);

    sensor =
        new JaCoCoSensor(
            configuration,
            new GroovyFileSystem(fileSystem),
            new PathResolver(),
            settings.asConfig(),
            mock(AnalysisWarnings.class));
  }

  @Test
  public void testDescription() throws IOException {
    initWithJaCoCoVersion("JaCoCoSensor_0_7_5");
    DefaultSensorDescriptor defaultSensorDescriptor = new DefaultSensorDescriptor();
    sensor.describe(defaultSensorDescriptor);
    assertThat(defaultSensorDescriptor.languages()).containsOnly(Groovy.KEY);
  }

  @Test
  public void testReadExecutionDataWithJacoco074() throws IOException {
    initWithJaCoCoVersion("JaCoCoSensor_0_7_4");

    Path workDir = tmpDir.newFolder().toPath();
    SensorContextTester context = SensorContextTester.create(workDir);
    context.setSettings(settings);
    context.fileSystem().setWorkDir(workDir);

    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              sensor.execute(context);
            });
    assertThat(e.getMessage()).isEqualTo(JaCoCoReportReader.INCOMPATIBLE_JACOCO_ERROR);
  }

  @Test
  public void testReadExecutionDataWithJacoco075() throws IOException {
    initWithJaCoCoVersion("JaCoCoSensor_0_7_5");

    Path workDir = tmpDir.newFolder().toPath();
    SensorContextTester context = SensorContextTester.create(workDir);
    context.setSettings(settings);
    context.fileSystem().setWorkDir(workDir);

    sensor.execute(context);

    verifyMeasures(context);
  }

  private void verifyMeasures(SensorContextTester context) {
    int[] oneHitlines = {9, 10, 14, 15, 17, 21, 29, 32, 33, 42, 47};
    int[] zeroHitlines = {25, 30, 38};
    int[] conditionLines = {14, 29, 30};
    int[] coveredConditions = {2, 1, 0};

    for (int zeroHitline : zeroHitlines) {
      assertThat(context.lineHits(":example/Hello.groovy", zeroHitline)).isZero();
    }
    for (int oneHitline : oneHitlines) {
      assertThat(context.lineHits(":example/Hello.groovy", oneHitline)).isEqualTo(1);
    }
    for (int i = 0; i < conditionLines.length; i++) {
      int conditionLine = conditionLines[i];
      assertThat(context.conditions(":example/Hello.groovy", conditionLine)).isEqualTo(2);
      assertThat(context.coveredConditions(":example/Hello.groovy", conditionLine))
          .isEqualTo(coveredConditions[i]);
    }
  }
}
