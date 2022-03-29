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

import groovyjarjarantlr.Token;
import groovyjarjarantlr.TokenStream;
import groovyjarjarantlr.TokenStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.antlr.GroovySourceToken;
import org.codehaus.groovy.antlr.parser.GroovyLexer;
import org.codehaus.groovy.antlr.parser.GroovyTokenTypes;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.cpd.NewCpdTokens;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class GroovyHighlighterAndTokenizer {

  private static final Logger LOG = Loggers.get(GroovyHighlighterAndTokenizer.class);

  private static final int[] KEYWORDS = {
    GroovyLexer.LITERAL_as,
    GroovyLexer.LITERAL_assert,
    GroovyLexer.LITERAL_boolean,
    GroovyLexer.LITERAL_break,
    GroovyLexer.LITERAL_byte,
    GroovyLexer.LITERAL_case,
    GroovyLexer.LITERAL_catch,
    GroovyLexer.LITERAL_char,
    GroovyLexer.LITERAL_class,
    GroovyLexer.LITERAL_continue,
    GroovyLexer.LITERAL_def,
    GroovyLexer.LITERAL_default,
    GroovyLexer.LITERAL_double,
    GroovyLexer.LITERAL_else,
    GroovyLexer.LITERAL_enum,
    GroovyLexer.LITERAL_extends,
    GroovyLexer.LITERAL_false,
    GroovyLexer.LITERAL_finally,
    GroovyLexer.LITERAL_float,
    GroovyLexer.LITERAL_for,
    GroovyLexer.LITERAL_if,
    GroovyLexer.LITERAL_implements,
    GroovyLexer.LITERAL_import,
    GroovyLexer.LITERAL_in,
    GroovyLexer.LITERAL_instanceof,
    GroovyLexer.LITERAL_int,
    GroovyLexer.LITERAL_interface,
    GroovyLexer.LITERAL_long,
    GroovyLexer.LITERAL_native,
    GroovyLexer.LITERAL_new,
    GroovyLexer.LITERAL_null,
    GroovyLexer.LITERAL_package,
    GroovyLexer.LITERAL_private,
    GroovyLexer.LITERAL_protected,
    GroovyLexer.LITERAL_public,
    GroovyLexer.LITERAL_return,
    GroovyLexer.LITERAL_short,
    GroovyLexer.LITERAL_static,
    GroovyLexer.LITERAL_super,
    GroovyLexer.LITERAL_switch,
    GroovyLexer.LITERAL_synchronized,
    GroovyLexer.LITERAL_this,
    GroovyLexer.LITERAL_threadsafe,
    GroovyLexer.LITERAL_throw,
    GroovyLexer.LITERAL_throws,
    GroovyLexer.LITERAL_trait,
    GroovyLexer.LITERAL_transient,
    GroovyLexer.LITERAL_true,
    GroovyLexer.LITERAL_try,
    GroovyLexer.LITERAL_void,
    GroovyLexer.LITERAL_volatile,
    GroovyLexer.LITERAL_while
  };

  private static final int[] STRINGS = {
    GroovyLexer.STRING_CH,
    GroovyLexer.STRING_CONSTRUCTOR,
    GroovyLexer.STRING_CTOR_END,
    GroovyLexer.STRING_CTOR_MIDDLE,
    GroovyLexer.STRING_CTOR_START,
    GroovyLexer.STRING_LITERAL,
    GroovyLexer.STRING_NL,
    GroovyLexer.REGEX_MATCH,
    GroovyLexer.REGEX_FIND,
    GroovyLexer.REGEXP_LITERAL,
    GroovyLexer.REGEXP_CTOR_END,
    GroovyLexer.REGEXP_SYMBOL,
    GroovyLexer.DOLLAR_REGEXP_LITERAL,
    GroovyLexer.DOLLAR_REGEXP_CTOR_END,
    GroovyLexer.DOLLAR_REGEXP_SYMBOL,
    GroovyLexer.DOLLAR,
    GroovyLexer.ESCAPED_DOLLAR,
    GroovyLexer.ESCAPED_SLASH
  };

  private static final int[] CONSTANTS = {
    GroovyLexer.DIGIT,
    GroovyLexer.DIGITS_WITH_UNDERSCORE,
    GroovyLexer.DIGITS_WITH_UNDERSCORE_OPT,
    GroovyLexer.HEX_DIGIT,
    GroovyLexer.NUM_BIG_DECIMAL,
    GroovyLexer.NUM_BIG_INT,
    GroovyLexer.NUM_DOUBLE,
    GroovyLexer.NUM_FLOAT,
    GroovyLexer.NUM_INT,
    GroovyLexer.NUM_LONG
  };

  private static final int[] COMMENTS = {
    GroovyLexer.ML_COMMENT,
    GroovyLexer.SH_COMMENT,
    GroovyLexer.SL_COMMENT
  };

  private static final List<TypeOfTextToTokenTypes> HIGHLIGHTING_MAPPING = Arrays.asList(
    new TypeOfTextToTokenTypes(TypeOfText.KEYWORD, KEYWORDS),
    new TypeOfTextToTokenTypes(TypeOfText.STRING, STRINGS),
    new TypeOfTextToTokenTypes(TypeOfText.CONSTANT, CONSTANTS),
    new TypeOfTextToTokenTypes(TypeOfText.COMMENT, COMMENTS));

  private final InputFile inputFile;
  private final File file;
  private boolean isAnnotation;

  public GroovyHighlighterAndTokenizer(InputFile inputFile) {
    this.inputFile = inputFile;
    this.file = inputFile.file();
  }

  public void processFile(SensorContext context) {
    List<GroovyToken> tokens = new ArrayList<>();
    isAnnotation = false;

    try (InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), context.fileSystem().encoding())) {
      GroovyLexer groovyLexer = new GroovyLexer(streamReader);
      groovyLexer.setWhitespaceIncluded(true);
      TokenStream tokenStream = groovyLexer.plumb();
      Token token = tokenStream.nextToken();

      int type = token.getType();
      while (type != Token.EOF_TYPE) {
        String text = token.getText();
        TypeOfText typeOfText = typeOfText(type, text).orElse(null);
        GroovySourceToken gst = (GroovySourceToken) token;
        if (StringUtils.isNotBlank(text)) {
          tokens.add(new GroovyToken(token.getLine(), token.getColumn(), gst.getLineLast(), gst.getColumnLast(), getImage(token, text), typeOfText));
        }
        token = tokenStream.nextToken();
        type = token.getType();
      }
    } catch (TokenStreamException e) {
      LOG.error("Unexpected token when lexing file: " + file.getName(), e);
    } catch (IOException e) {
      LOG.error("Unable to read file: " + file.getName(), e);
    }

    if (!tokens.isEmpty()) {
      boolean isNotTest = inputFile.type() != InputFile.Type.TEST;
      NewCpdTokens cpdTokens = isNotTest ? context.newCpdTokens().onFile(inputFile) : null;
      NewHighlighting highlighting = context.newHighlighting().onFile(inputFile);
      for (GroovyToken groovyToken : tokens) {
        if (isNotTest) {
          cpdTokens = cpdTokens.addToken(groovyToken.startLine, groovyToken.startColumn, groovyToken.endLine, groovyToken.endColumn, groovyToken.value);
        }
        if (groovyToken.typeOfText != null) {
          highlighting = highlighting.highlight(groovyToken.startLine, groovyToken.startColumn, groovyToken.endLine, groovyToken.endColumn, groovyToken.typeOfText);
        }
      }
      highlighting.save();
      if (isNotTest) {
        cpdTokens.save();
      }
    }
  }

  private String getImage(Token token, String text) {
    if (token.getType() == GroovyTokenTypes.STRING_LITERAL
      || token.getType() == GroovyTokenTypes.STRING_CTOR_START
      || token.getType() == GroovyTokenTypes.STRING_CTOR_MIDDLE
      || token.getType() == GroovyTokenTypes.STRING_CTOR_END) {
      return "LITERAL";
    }
    return text;
  }

  private Optional<TypeOfText> typeOfText(int type, String text) {
    TypeOfText result = null;
    for (TypeOfTextToTokenTypes mapping : HIGHLIGHTING_MAPPING) {
      if (Arrays.stream(mapping.tokenTypes).anyMatch(tokenType -> tokenType == type)) {
        result = mapping.typeOfText;
        break;
      }
    }

    if (result == TypeOfText.COMMENT && text.startsWith("/**")) {
      result = TypeOfText.STRUCTURED_COMMENT;
    } else if (result == null && (type == GroovyLexer.AT || isAnnotation)) {
      isAnnotation = isPartOfAnnotation(type);
      result = isAnnotation ? TypeOfText.ANNOTATION : null;
    }

    return Optional.ofNullable(result);
  }

  private static boolean isPartOfAnnotation(int type) {
    return type == GroovyLexer.AT || type == GroovyLexer.IDENT || type == GroovyLexer.DOT;
  }

  private static class GroovyToken {
    final int startLine;
    final int startColumn;
    final int endLine;
    final int endColumn;
    final String value;
    @Nullable
    final TypeOfText typeOfText;

    public GroovyToken(int startLine, int startColumn, int endLine, int endColumn, String value, @Nullable TypeOfText typeOfText) {
      this.startLine = startLine;
      this.startColumn = startColumn - 1;
      this.endLine = endLine;
      this.endColumn = endColumn - 1;
      this.value = value;
      this.typeOfText = typeOfText;
    }
  }

  private static class TypeOfTextToTokenTypes {
    final int[] tokenTypes;
    final TypeOfText typeOfText;

    public TypeOfTextToTokenTypes(TypeOfText typeOfText, int[] tokenTypes) {
      this.tokenTypes = tokenTypes;
      this.typeOfText = typeOfText;
    }
  }
}
