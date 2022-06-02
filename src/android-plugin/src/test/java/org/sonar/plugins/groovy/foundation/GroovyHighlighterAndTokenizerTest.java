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
package org.sonar.plugins.groovy.foundation;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.plugins.groovy.TestUtils;

public class GroovyHighlighterAndTokenizerTest {

  @Test
  public void should_highlight_keywords() throws Exception {
    File file = TestUtils.getResource("/org/sonar/plugins/groovy/foundation/Greet.groovy");

    SensorContextTester context = SensorContextTester.create(file.getParentFile());
    InputFile inputFile =
        TestInputFileBuilder.create("", file.getParentFile(), file)
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .setContents(new String(Files.readAllBytes(file.toPath()), "UTF-8"))
            .build();
    context.fileSystem().add(inputFile);

    GroovyHighlighterAndTokenizer highlighter = new GroovyHighlighterAndTokenizer(inputFile);
    context = Mockito.spy(context);
    highlighter.processFile(context);

    assertThat(context.highlightingTypeAt(":Greet.groovy", 1, 0)).containsOnly(TypeOfText.KEYWORD);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 2, 2)).containsOnly(TypeOfText.KEYWORD);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 4, 2)).containsOnly(TypeOfText.KEYWORD);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 4, 25)).containsOnly(TypeOfText.STRING);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 4, 32)).containsOnly(TypeOfText.STRING);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 7, 0))
        .containsOnly(TypeOfText.STRUCTURED_COMMENT);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 8, 1))
        .containsOnly(TypeOfText.STRUCTURED_COMMENT);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 9, 1))
        .containsOnly(TypeOfText.STRUCTURED_COMMENT);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 10, 0))
        .containsOnly(TypeOfText.ANNOTATION);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 10, 21))
        .containsOnly(TypeOfText.ANNOTATION);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 12, 2)).containsOnly(TypeOfText.KEYWORD);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 12, 13))
        .containsOnly(TypeOfText.CONSTANT);
    assertThat(context.highlightingTypeAt(":Greet.groovy", 12, 17))
        .containsOnly(TypeOfText.COMMENT);
    Mockito.verify(context, Mockito.times(1)).newHighlighting();
  }

  @Test
  public void should_tokenize_for_cpd() throws Exception {
    File file = TestUtils.getResource("/org/sonar/plugins/groovy/foundation/Greet.groovy");

    SensorContextTester context = SensorContextTester.create(file.getParentFile());
    InputFile inputFile =
        TestInputFileBuilder.create("", file.getParentFile(), file)
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .initMetadata(new String(Files.readAllBytes(file.toPath()), "UTF-8"))
            .build();
    context.fileSystem().add(inputFile);

    GroovyHighlighterAndTokenizer highlighter = new GroovyHighlighterAndTokenizer(inputFile);
    context = Mockito.spy(context);
    highlighter.processFile(context);

    assertThat(context.cpdTokens(":Greet.groovy"))
        .extracting("value")
        .containsExactly(
            "classGreet{",
            "defname",
            "Greet(who){name=who}",
            "defsalute(){printlnLITERALnameLITERALnameLITERAL}",
            "}",
            "/** * Javadoc style */",
            "@groovy.beans.Bindable",
            "classCool{",
            "doublex=1.4// Comment",
            "}");
    Mockito.verify(context, Mockito.times(1)).newCpdTokens();
  }

  @Test
  public void should_highlight_nothing_if_file_is_missing() throws Exception {
    File file = TestUtils.getResource("/org/sonar/plugins/groovy/foundation/Greet.groovy");

    SensorContextTester context = SensorContextTester.create(file.getParentFile());
    InputFile inputFile =
        TestInputFileBuilder.create("", "Greet-fake.groovy")
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .build();
    context.fileSystem().add(inputFile);

    GroovyHighlighterAndTokenizer highlighter = new GroovyHighlighterAndTokenizer(inputFile);

    context = Mockito.spy(context);
    highlighter.processFile(context);

    Mockito.verify(context, Mockito.never()).newHighlighting();
  }

  @Test
  public void should_highlight_only_partially_if_file_can_not_be_lexed() throws Exception {
    File file = TestUtils.getResource("/org/sonar/plugins/groovy/foundation/Error.groovy");

    SensorContextTester context = SensorContextTester.create(file.getParentFile());
    InputFile inputFile =
        TestInputFileBuilder.create("", file.getParentFile(), file)
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .initMetadata(new String(Files.readAllBytes(file.toPath()), "UTF-8"))
            .build();
    context.fileSystem().add(inputFile);

    GroovyHighlighterAndTokenizer highlighter = new GroovyHighlighterAndTokenizer(inputFile);

    context = Mockito.spy(context);
    highlighter.processFile(context);

    assertThat(context.highlightingTypeAt(":Error.groovy", 1, 0)).containsOnly(TypeOfText.KEYWORD);
    assertThat(context.highlightingTypeAt(":Error.groovy", 2, 2)).containsOnly(TypeOfText.KEYWORD);
    assertThat(context.highlightingTypeAt(":Error.groovy", 3, 2)).isEmpty();
    Mockito.verify(context, Mockito.times(1)).newHighlighting();
  }
}
