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

/*
See https://doc.rust-lang.org/reference/keywords.html
 */
public enum RustKeyword implements GrammarRuleKey {
  KW_ABSTRACT("abstract"), // reserved keyword.
  KW_AS("as"),
  KW_ASYNC("async"), // added beginning in the 2018 edition.
  KW_AWAIT("await"), // added beginning in the 2018 edition.
  KW_BECOME("become"), // reserved keyword.
  KW_BOX("box"), // reserved keyword.
  KW_BREAK("break"),
  KW_CONST("const"),
  KW_CONTINUE("continue"),
  KW_CRATE("crate"),
  KW_DO("do"), // reserved keyword.
  KW_DYN("dyn"), // added beginning in the 2018 edition.
  KW_ELSE("else"),
  KW_ENUM("enum"),
  KW_EXTERN("extern"),
  KW_FALSE("false"),
  KW_FINAL("final"), // reserved keyword.
  KW_FN("fn"),
  KW_FOR("for"),
  KW_IF("if"),
  KW_IMPL("impl"),
  KW_IN("in"),
  KW_LET("let"),
  KW_LOOP("loop"),
  KW_MACRO("macro"), // reserved keyword.
  KW_MATCH("match"),
  KW_MOD("mod"),
  KW_MOVE("move"),
  KW_MUT("mut"),
  KW_OVERRIDE("override"), // reserved keyword.
  KW_PRIV("priv"), // reserved keyword.
  KW_PUB("pub"),
  KW_REF("ref"),
  KW_RETURN("return"),
  KW_SELF_VALUE("self"),
  KW_SELF_TYPE("Self"),
  KW_STATIC("static"), // weak
  KW_STRUCT("struct"),
  KW_SUPER("super"),
  KW_TRAIT("trait"),
  KW_TRUE("true"),
  KW_TRY("try"), // reserver keyword
  KW_TYPE("type"),
  KW_TYPEOF("typeof"), // reserved keyword.
  KW_UNION("union"), // weak
  KW_UNSAFE("unsafe"),
  KW_UNSIZED("unsized"), // reserved keyword
  KW_USE("use"),
  KW_VIRTUAL("virtual"), // reserved keyword
  KW_WHERE("where"),
  KW_WHILE("while"),
  KW_YIELD("yield"); // reserved keyword

  private final String value;

  RustKeyword(String value) {
    this.value = value;
  }

  /**
   * keywords as String.
   * @return an array containing all keywords as typed in Java
   */
  public static String[] keywordValues() {
    RustKeyword[] keywordsEnum = RustKeyword.values();
    String[] keywords = new String[keywordsEnum.length];
    for (int i = 0; i < keywords.length; i++) {
      keywords[i] = keywordsEnum[i].getValue();
    }
    return keywords;
  }

  public String getName() {
    return name();
  }

  public String getValue() {
    return value;
  }
}
