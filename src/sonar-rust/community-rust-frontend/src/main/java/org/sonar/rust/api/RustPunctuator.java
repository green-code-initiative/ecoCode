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
package org.sonar.rust.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

public enum RustPunctuator implements GrammarRuleKey {

  PLUS("+"),
  MINUS("-"),
  STAR("*"),
  SLASH("/"),
  PERCENT("%"),
  CARET("^"),
  NOT("!"),
  AND("&"),
  OR("|"),
  ANDAND("&&"),
  OROR("||"),
  SHL("<<"),
  SHR(">>"),
  PLUSEQ("+="),
  MINUSEQ("-="),
  STAREQ("*="),
  SLASHEQ("/="),
  PERCENTEQ("%="),
  CARETEQ("^="),
  ANDEQ("&="),
  OREQ("|="),
  SHLEQ("<<="),
  SHREQ(">>="),
  EQ("="),
  EQEQ("=="),
  NE("!="),
  GT(">"),
  LT("<"),
  GE(">="),
  LE("<="),
  AT("@"),
  UNDERSCORE("_"),
  DOT("."),
  DOTDOT(".."),
  DOTDOTDOT("..."),
  DOTDOTEQ("..="),
  COMMA(","),
  SEMI(";"),
  COLON(":"),
  PATHSEP("::"),
  RARROW("->"),
  FATARROW("=>"),
  POUND("#"),
  DOLLAR("$"),
  QUESTION("?");

  private final String value;

  RustPunctuator(String value) {
    this.value = value;
  }

  public static String[] punctuatorValues() {
    RustPunctuator[] punctuatorEnum = RustPunctuator.values();
    String[] punctuators = new String[punctuatorEnum.length];
    for (int i = 0; i < punctuators.length; i++) {
      punctuators[i] = punctuatorEnum[i].getValue();
    }
    return punctuators;
  }

  public String getName() {
    return name();
  }

  public String getValue() {
    return value;
  }
}
