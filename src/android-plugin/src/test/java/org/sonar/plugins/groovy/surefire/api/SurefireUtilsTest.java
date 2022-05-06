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
package org.sonar.plugins.groovy.surefire.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;
import org.junit.Test;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.plugins.groovy.TestUtils;

public class SurefireUtilsTest {

  private FileSystem fs =
      new DefaultFileSystem(TestUtils.getResource(getClass(), "shouldGetReportsFromProperty"));
  private PathResolver pathResolver = new PathResolver();

  /*@Test
  public void should_get_reports_from_property() {
    MapSettings settings = new MapSettings();
    settings.setProperty(SurefireUtils.SUREFIRE_REPORT_PATHS_PROPERTY, "target/surefire");
    assertThat(SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver).size())
        .isEqualTo(1);
    assertThat(
            SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver)
                .get(0)
                .exists())
        .isTrue();
    assertThat(
            SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver)
                .get(0)
                .isDirectory())
        .isTrue();
  }

  @Test
  public void should_get_2_reports_from_property() {
    MapSettings settings = new MapSettings();
    settings.setProperty(
        SurefireUtils.SUREFIRE_REPORT_PATHS_PROPERTY, "target/surefire, target/surefire2");
    assertThat(SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver).size())
        .isEqualTo(2);
    assertThat(
            SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver)
                .get(0)
                .exists())
        .isTrue();
    assertThat(
            SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver)
                .get(0)
                .isDirectory())
        .isTrue();
    assertThat(
            SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver)
                .get(1)
                .exists())
        .isTrue();
    assertThat(
            SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver)
                .get(1)
                .isDirectory())
        .isTrue();
  }*/

  @Test
  public void return_default_value_if_property_unset() throws Exception {
    List<File> directories =
        SurefireUtils.getReportDirectories(new MapSettings().asConfig(), fs, pathResolver);
    assertThat(directories.size()).isEqualTo(1);
    assertThat(directories.get(0).getCanonicalPath())
        .endsWith("target" + File.separator + "surefire-reports");
    assertThat(directories.get(0).exists()).isFalse();
    assertThat(directories.get(0).isDirectory()).isFalse();
  }

  @Test
  public void return_default_value_if_can_not_read_file() throws Exception {
    MapSettings settings = new MapSettings();
    settings.setProperty(SurefireUtils.SUREFIRE_REPORT_PATHS_PROPERTY, "../target/\u0000:surefire");
    List<File> directories =
        SurefireUtils.getReportDirectories(settings.asConfig(), fs, pathResolver);
    assertThat(directories.size()).isEqualTo(1);
    assertThat(directories.get(0).getCanonicalPath())
        .endsWith("target" + File.separator + "surefire-reports");
    assertThat(directories.get(0).exists()).isFalse();
    assertThat(directories.get(0).isDirectory()).isFalse();
  }
}
