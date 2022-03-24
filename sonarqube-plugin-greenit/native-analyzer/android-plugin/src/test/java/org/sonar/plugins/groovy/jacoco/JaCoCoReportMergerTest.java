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

import java.io.File;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.sonar.plugins.groovy.TestUtils;

public class JaCoCoReportMergerTest {

  @Rule public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule public ExpectedException exception = ExpectedException.none();

  @Test
  public void mergeDifferentFormatShouldFail1() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage(JaCoCoReportReader.INCOMPATIBLE_JACOCO_ERROR);
    merge("jacoco-0.7.5.exec", "jacoco-it-0.7.4.exec");
  }

  @Test
  public void mergeDifferentFormatShouldFail2() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage(JaCoCoReportReader.INCOMPATIBLE_JACOCO_ERROR);
    merge("jacoco-0.7.4.exec", "jacoco-it-0.7.5.exec");
  }

  @Test
  public void merge_same_format_should_not_fail() {
    merge("jacoco-0.7.5.exec", "jacoco-it-0.7.5.exec");
  }

  private void merge(String file1, String file2) {
    File current =
        TestUtils.getResource(
            "/org/sonar/plugins/groovy/jacoco/JaCoCo_incompatible_merge/" + file1);
    File previous =
        TestUtils.getResource(
            "/org/sonar/plugins/groovy/jacoco/JaCoCo_incompatible_merge/" + file2);
    JaCoCoReportMerger.mergeReports(
        testFolder.getRoot().toPath().resolve("dummy"), current, previous);
  }
}
