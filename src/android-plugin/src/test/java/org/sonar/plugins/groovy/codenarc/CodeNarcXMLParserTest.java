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
package org.sonar.plugins.groovy.codenarc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.plugins.groovy.codenarc.CodeNarcXMLParser.CodeNarcViolation;

public class CodeNarcXMLParserTest {

  @Test
  public void should_parse_report() throws Exception {
    FileSystem fileSystem = Mockito.mock(FileSystem.class);
    Mockito.when(fileSystem.predicates()).thenReturn(Mockito.mock(FilePredicates.class));
    Mockito.when(fileSystem.hasFiles(nullable(FilePredicate.class))).thenReturn(true);
    List<CodeNarcViolation> violations =
        CodeNarcXMLParser.parse(
            FileUtils.toFile(getClass().getResource("parsing/sample.xml")), fileSystem);

    assertThat(violations.size()).isEqualTo(17);

    CodeNarcViolation violation = violations.get(0);
    assertThat(violation.getRuleName()).isEqualTo("EmptyElseBlock");
    assertThat(violation.getFilename())
        .isEqualTo("[sourcedir]/org/codenarc/sample/domain/SampleDomain.groovy");
    assertThat(violation.getLine()).isEqualTo(24);
    assertThat(violation.getMessage()).isEqualTo("");

    violation = violations.get(1);
    assertThat(violation.getRuleName()).isEqualTo("EmptyIfStatement");
    assertThat(violation.getFilename())
        .isEqualTo("[sourcedir]/org/codenarc/sample/domain/SampleDomain.groovy");
    assertThat(violation.getLine()).isEqualTo(21);
    assertThat(violation.getMessage()).isEqualTo("");
  }

  @Test
  public void should_not_fail_if_line_number_not_specified() throws Exception {
    FileSystem fileSystem = Mockito.mock(FileSystem.class);
    Mockito.when(fileSystem.predicates()).thenReturn(Mockito.mock(FilePredicates.class));
    Mockito.when(fileSystem.hasFiles(any(FilePredicate.class))).thenReturn(true);
    List<CodeNarcViolation> violations =
        CodeNarcXMLParser.parse(
            FileUtils.toFile(getClass().getResource("parsing/line-number-not-specified.xml")),
            fileSystem);

    assertThat(violations.size()).isEqualTo(1);

    CodeNarcViolation violation = violations.get(0);
    assertThat(violation.getRuleName()).isEqualTo("CyclomaticComplexity");
    assertThat(violation.getFilename()).isEqualTo("org/example/Example.groovy");
    assertThat(violation.getLine()).isNull();
    assertThat(violation.getMessage())
        .isEqualTo("The cyclomatic complexity for class [org.example.Example] is [27.0]");
  }
}
