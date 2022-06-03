/**
 * Community Rust Plugin
 * Copyright (C) 2021 Eric Le Goff
 * mailto:community-rust AT pm DOT me
 * http://github.com/elegoff/sonar-rust
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
package org.elegoff.plugins.communityrust;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.api.config.Configuration;
import org.sonar.rust.RustVisitorContext;
import org.sonar.rust.api.RustKeyword;
import org.sonar.rust.api.RustTokenType;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;
import org.sonarsource.analyzer.commons.TokenLocation;

public class RustTokensVisitor {

  private final Set<String> keywords = new HashSet<>(Arrays.asList(RustKeyword.keywordValues()));
  private final SensorContext context;
  private final ParserAdapter<LexerlessGrammar> lexer;
  private final boolean ignoreCPDTests;

  public RustTokensVisitor(SensorContext context, ParserAdapter<LexerlessGrammar> lexer) {
    this.context = context;
    this.lexer = lexer;
    this.ignoreCPDTests = context.config().getBoolean(CommunityRustPlugin.IGNORE_DUPLICATION_FOR_TESTS).orElse(false);
  }

  private static String getTokenImage(Token token) {
    if (token.getType().equals(RustTokenType.CHARACTER_LITERAL)) {
      return RustTokenType.CHARACTER_LITERAL.getValue();
    }
    return token.getValue().toLowerCase(Locale.ENGLISH);
  }

  private static void highlight(NewHighlighting highlighting, TokenLocation tokenLocation, TypeOfText typeOfText) {
    highlighting.highlight(tokenLocation.startLine(), tokenLocation.startLineOffset(), tokenLocation.endLine(), tokenLocation.endLineOffset(), typeOfText);
  }

  private static TokenLocation tokenLocation(Token token) {
    return new TokenLocation(token.getLine(), token.getColumn(), token.getOriginalValue());
  }

  public void scanFile(InputFile inputFile, RustVisitorContext visitorContext) {
    var highlighting = context.newHighlighting();
    highlighting.onFile(inputFile);

    var cpdTokens = context.newCpdTokens();
    cpdTokens.onFile(inputFile);

    List<Token> parsedTokens = lexer.parse(visitorContext.file().content()).getTokens();
    Set<Token> unitTestTokens = identifyUnitTestTokens(parsedTokens);

    for (Token token : parsedTokens) {

      final var tokenLocation = tokenLocation(token);

      highlightToken(token, tokenLocation, highlighting);

      for (Trivia trivia : token.getTrivia()) {
        highlight(highlighting, tokenLocation(trivia.getToken()), TypeOfText.COMMENT);
      }

      if (unitTestTokens.contains(token)) {
        highlight(highlighting, tokenLocation, TypeOfText.ANNOTATION);
      }

      if (!GenericTokenType.EOF.equals(token.getType()) && !(unitTestTokens.contains(token) && this.ignoreCPDTests)) {
        cpdTokens.addToken(tokenLocation.startLine(), tokenLocation.startLineOffset(), tokenLocation.endLine(), tokenLocation.endLineOffset(), getTokenImage(token));
      }
    }

    highlighting.save();
    cpdTokens.save();
  }

  private void highlightToken(Token token, TokenLocation tokenLocation, NewHighlighting highlighting) {
    final String tokenImage = getTokenImage(token);
    if (token.getType().equals(RustTokenType.CHARACTER_LITERAL)
      || token.getType().equals(RustTokenType.STRING_LITERAL)
      || token.getType().equals(RustTokenType.RAW_STRING_LITERAL)
      || token.getType().equals(RustTokenType.RAW_BYTE_STRING_LITERAL)

    ) {
      highlight(highlighting, tokenLocation, TypeOfText.STRING);

    } else if (keywords.contains(tokenImage)) {
      highlight(highlighting, tokenLocation, TypeOfText.KEYWORD);
    }

    if (token.getType().equals(RustTokenType.FLOAT_LITERAL)
      || token.getType().equals(RustTokenType.BOOLEAN_LITERAL)
      || token.getType().equals(RustTokenType.INTEGER_LITERAL)) {
      highlight(highlighting, tokenLocation, TypeOfText.CONSTANT);
    }
  }

  private Set<Token> identifyUnitTestTokens(List<Token> parsedTokens) {
    Set<Token> testTokens = new HashSet<>();
    Set<String> unitTestsAttributes = getUnitTestAttributes();
    int i = 0;
    while (i < parsedTokens.size()) {
      if ("#".equals(getTokenImage(parsedTokens.get(i))) && ("[".equals(getTokenImage(parsedTokens.get(i + 1))))
        && (unitTestsAttributes.contains(getTokenImage(parsedTokens.get(i + 2)))) && ("]".equals(getTokenImage(parsedTokens.get(i + 3))))
        && ("fn".equals(getTokenImage(parsedTokens.get(i + 4))))) {
        int j = i + 5;
        // lookup for opening bracket
        while (!"{".equals(getTokenImage(parsedTokens.get(j)))) {
          j++;
        }

        int cptOpeningBracket = 1;
        // lookup for outer closing bracket (end of test function position)
        while (cptOpeningBracket > 0) {
          j++;
          String tokenImage = getTokenImage(parsedTokens.get(j));
          if ("{".equals(tokenImage)) {
            cptOpeningBracket++;
          } else if ("}".equals(tokenImage)) {
            cptOpeningBracket--;
          }

        }

        // all tokens constituting a test function are added to the set
        IntStream.rangeClosed(i, j).mapToObj(parsedTokens::get).forEach(testTokens::add);
      }
      i++;
    }
    return testTokens;
  }

  private Set<String> getUnitTestAttributes() {
    Configuration config = context.config();
    String[] attrs = filterEmptyStrings(config.getStringArray(CommunityRustPlugin.UNIT_TEST_ATTRIBUTES));
    if (attrs.length == 0) {
      attrs = StringUtils.split(CommunityRustPlugin.DEFAULT_UNIT_TEST_ATTRIBUTES, ",");
    }
    return Arrays.stream(attrs).collect(Collectors.toSet());
  }

  private String[] filterEmptyStrings(String[] stringArray) {
    List<String> nonEmptyStrings = new ArrayList<>();
    for (String string : stringArray) {
      if (StringUtils.isNotBlank(string.trim())) {
        nonEmptyStrings.add(string.trim());
      }
    }
    return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
  }

}
