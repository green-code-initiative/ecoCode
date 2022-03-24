/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2021 SonarQube Community
 *  
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

import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.plugins.groovy.TestUtils;
import org.sonar.plugins.groovy.foundation.Groovy;

public class JaCoCoConfigurationTest {

  private MapSettings settings = TestUtils.jacocoDefaultSettings();
  private JaCoCoConfiguration jacocoSettings;
  private DefaultFileSystem fileSystem;

  @Before
  public void setUp() {
    fileSystem = new DefaultFileSystem(Paths.get("."));
    jacocoSettings = new JaCoCoConfiguration(settings.asConfig(), fileSystem);
  }

  @Test
  public void shouldExecuteOnProject() {
    // no files
    assertThat(jacocoSettings.shouldExecuteOnProject(true)).isFalse();
    assertThat(jacocoSettings.shouldExecuteOnProject(false)).isFalse();

    fileSystem.add(TestInputFileBuilder.create("", "src/foo/bar.java").setLanguage("java").build());
    assertThat(jacocoSettings.shouldExecuteOnProject(true)).isFalse();
    assertThat(jacocoSettings.shouldExecuteOnProject(false)).isFalse();

    fileSystem.add(
        TestInputFileBuilder.create("", "src/foo/bar.groovy").setLanguage(Groovy.KEY).build());
    assertThat(jacocoSettings.shouldExecuteOnProject(true)).isTrue();
    assertThat(jacocoSettings.shouldExecuteOnProject(false)).isFalse();

    settings.setProperty(JaCoCoConfiguration.REPORT_MISSING_FORCE_ZERO, true);
    assertThat(jacocoSettings.shouldExecuteOnProject(true)).isTrue();
    assertThat(jacocoSettings.shouldExecuteOnProject(false)).isTrue();
  }

  @Test
  public void defaults() {
    assertThat(jacocoSettings.getReportPath()).isEqualTo("target/jacoco.exec");
    assertThat(jacocoSettings.getItReportPath()).isEqualTo("target/jacoco-it.exec");
  }

  @Test
  public void shouldReturnItReportPathWhenModified() {
    settings.setProperty(JaCoCoConfiguration.IT_REPORT_PATH_PROPERTY, "target/it-jacoco-test.exec");
    assertThat(jacocoSettings.getItReportPath()).isEqualTo("target/it-jacoco-test.exec");
  }

  @Test
  public void shouldReturnReportPathWhenModified() {
    settings.setProperty(JaCoCoConfiguration.REPORT_PATH_PROPERTY, "jacoco.exec");
    assertThat(jacocoSettings.getReportPath()).isEqualTo("jacoco.exec");
  }
}
