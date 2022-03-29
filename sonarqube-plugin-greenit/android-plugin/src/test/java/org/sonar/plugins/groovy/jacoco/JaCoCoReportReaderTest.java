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
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Fail;
import org.jacoco.core.data.IExecutionDataVisitor;
import org.jacoco.core.data.ISessionInfoVisitor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.sonar.plugins.groovy.TestUtils;

import static org.mockito.Mockito.mock;

public class JaCoCoReportReaderTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  private File dummy = new File("DummyFile.dummy");

  @Test
  public void reading_unexisting_file_should_fail() {
    expectedException.expect(IllegalArgumentException.class);
    new JaCoCoReportReader(dummy);
  }

  @Test
  public void reading_file_no_tfound_should_do_nothing() {
    new JaCoCoReportReader(null).readJacocoReport(mock(IExecutionDataVisitor.class), mock(ISessionInfoVisitor.class));
  }

  @Test
  public void notExistingClassFilesShouldNotBeAnalyzed() {
    File report = TestUtils.getResource("/org/sonar/plugins/groovy/jacoco/JaCoCo_incompatible_merge/jacoco-0.7.5.exec");
    Collection<File> classFile = Arrays.asList(dummy);
    new JaCoCoReportReader(report).analyzeFiles(null, classFile);
  }

  @Test
  public void analyzing_a_deleted_file_should_fail() throws Exception {
    File report = testFolder.newFile("jacoco.exec");
    FileUtils.copyFile(TestUtils.getResource("/org/sonar/plugins/groovy/jacoco/JaCoCo_incompatible_merge/jacoco-0.7.5.exec"), report);
    JaCoCoReportReader jacocoReportReader = new JaCoCoReportReader(report);
    expectedException.expect(IllegalArgumentException.class);
    if (!report.delete()) {
      Fail.fail("report was not deleted, unable to complete test.");
    }
    ExecutionDataVisitor edv = new ExecutionDataVisitor();
    jacocoReportReader.readJacocoReport(edv, edv);
  }

  @Test
  public void incorrect_binary_format_should_fail() throws Exception {
    File report = TestUtils.getResource("/org/sonar/plugins/groovy/jacoco/Hello.class.toCopy");
    expectedException.expect(IllegalArgumentException.class);
    new JaCoCoReportReader(report);
  }

  @Test
  public void unknown_exec_file_should_fail() {
    expectedException.expect(IllegalArgumentException.class);
    new JaCoCoReportReader(new File("unknown.exec"));
  }

}
