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
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.notifications.AnalysisWarnings;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.plugins.groovy.TestUtils;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.foundation.GroovyFileSystem;

public class JaCoCoItSensorTest {

  @Rule public final TemporaryFolder tmpDir = new TemporaryFolder();

  private MapSettings settings = TestUtils.jacocoDefaultSettings();
  private JaCoCoSensor sensor;

  @Before
  public void setUp() throws IOException {
    Path outputDir = tmpDir.newFolder().toPath();
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
    settings.setProperty(JaCoCoConfiguration.IT_REPORT_PATH_PROPERTY, "jacoco-it.exec");

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
  public void testDescription() {
    DefaultSensorDescriptor defaultSensorDescriptor = new DefaultSensorDescriptor();
    sensor.describe(defaultSensorDescriptor);
    assertThat(defaultSensorDescriptor.languages()).containsOnly(Groovy.KEY);
  }
}
