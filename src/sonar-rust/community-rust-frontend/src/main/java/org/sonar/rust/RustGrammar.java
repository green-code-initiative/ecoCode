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
package org.sonar.rust;

import com.sonar.sslr.api.GenericTokenType;
import java.util.Arrays;
import org.apache.commons.lang.ArrayUtils;
import org.sonar.rust.api.RustKeyword;
import org.sonar.rust.api.RustPunctuator;
import org.sonar.rust.api.RustTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public enum RustGrammar implements GrammarRuleKey {
  ABI,
  ADDITION_EXPRESSION,
  ANDEQ_EXPRESSION,
  ANY_TOKEN,
  ARITHMETIC_OR_LOGICAL_EXPRESSION,
  ARRAY_ELEMENTS,
  ARRAY_EXPRESSION,
  ARRAY_TYPE,
  ASCII,
  ASCII_ESCAPE,
  ASCII_FOR_CHAR,
  ASCII_FOR_STRING,
  ASSIGNMENT_EXPRESSION,
  ASSOCIATED_ITEM,
  ASYNC_BLOCK_EXPRESSION,
  AS_CLAUSE,
  ATTR,
  ATTR_INPUT,
  AWAIT_EXPRESSION,
  BARE_FUNCTION_RETURN_TYPE,
  BARE_FUNCTION_TYPE,
  BIN_DIGIT,
  BIN_LITERAL,
  BITAND_EXPRESSION,
  BITOR_EXPRESSION,
  BITXOR_EXPRESSION,
  BLOCK_COMMENT,
  BLOCK_COMMENT_OR_DOC,
  BLOCK_EXPRESSION,
  BOOLEAN_LITERAL,
  BORROW_EXPRESSION,
  BOX_EXPRESSION,
  BREAK_EXPRESSION,
  BYTE_ESCAPE,
  BYTE_LITERAL,
  BYTE_STRING_LITERAL,
  CALL_EXPRESSION,
  CALL_PARAMS,
  CARETEQ_EXPRESSION,
  CHAR_LITERAL,
  CLOSURE_EXPRESSION,
  CLOSURE_PARAM,
  CLOSURE_PARAMETERS,
  COMPARISON_EXPRESSION,
  COMPILATION_UNIT,
  COMPOUND_ASSIGNMENT_EXPRESSION,
  CONST_PARAM,
  CONSTANT_ITEM,
  CONTINUE_EXPRESSION,
  CRATE_REF,
  DEC_DIGIT,
  DEC_LITERAL,
  DELIMITERS,
  DELIM_TOKEN_TREE,
  DEREFERENCE_EXPRESSION,
  DIVISION_EXPRESSION,
  ENUMERATION,
  ENUM_ITEMS,
  ENUM_ITEM,
  ENUM_ITEM_DISCRIMINANT,
  ENUM_ITEM_STRUCT,
  ENUM_ITEM_TUPLE,
  EOF,
  EQ_EXPRESSION,
  ERROR_PROPAGATION_EXPRESSION,
  EXPRESSION,
  SCRUTINEE,
  EXPRESSION_STATEMENT,
  EXPRESSION_TERM,
  EXPRESSION_TERM_EXCEPT_STRUCT,
  EXPRESSION_WITHOUT_BLOCK,
  EXPRESSION_WITH_BLOCK,
  EXTERNAL_ITEM,
  EXTERN_BLOCK,
  EXTERN_CRATE,
  FIELD_EXPRESSION,
  FLOAT_EXPONENT,
  FLOAT_LITERAL,
  FLOAT_SUFFIX,
  FOR_LIFETIMES,
  FUNCTION,
  FUNCTION_PARAM,
  FUNCTION_PARAM_PATTERN,
  FUNCTION_PARAMETERS,
  FUNCTION_PARAMETERS_MAYBE_NAMED_VARIADIC,
  FUNCTION_QUALIFIERS,
  FUNCTION_RETURN_TYPE,
  FUNCTION_TYPE_QUALIFIERS,
  GE_EXPRESSION,
  GENERIC_ARG,
  GENERIC_ARGS,
  GENERIC_ARGS_BINDING,
  GENERIC_ARGS_CONST,
  GENERIC_PARAMS,
  GENERIC_PARAM,
  GROUPED_EXPRESSION,
  GROUPED_PATTERN,
  GT_EXPRESSION,
  HALF_OPEN_RANGE_PATTERN,
  HEX_DIGIT,
  HEX_LITERAL,
  IDENTIFIER,
  IDENTIFIER_OR_KEYWORD,
  IDENTIFIER_PATTERN,
  IF_EXPRESSION,
  IF_LET_EXPRESSION,
  IMPLEMENTATION,
  IMPL_TRAIT_TYPE,
  IMPL_TRAIT_TYPE_ONE_BOUND,
  INCLUSIVE_RANGE_PATTERN,
  INDEX_EXPRESSION,
  INFERRED_TYPE,
  INFINITE_LOOP_EXPRESSION,
  INHERENT_IMPL,
  INNER_ATTRIBUTE,
  INNER_BLOCK_DOC,
  INNER_LINE_DOC,
  INTEGER_LITERAL,
  INTEGER_SUFFIX,
  ITEM,
  ITERATOR_LOOP_EXPRESSION,
  KEYWORD,
  LAZY_AND,
  LAZY_BOOLEAN_EXPRESSION,
  LAZY_OR,
  LET_STATEMENT,
  LE_EXPRESSION,
  LIFETIME,
  LIFETIMES,
  LIFETIME_BOUNDS,
  LIFETIME_OR_LABEL,
  LIFETIME_PARAM,
  LIFETIME_TOKEN,
  LIFETIME_WHERE_CLAUSE_ITEM,
  LINE_COMMENT,
  LITERALS,
  LITERAL_PATTERN,
  LITERAL_EXPRESSION,
  LOOP_EXPRESSION,
  LOOP_LABEL,
  LT_EXPRESSION,
  MACRO_FRAG_SPEC,
  MACRO_INVOCATION,
  MACRO_INVOCATION_SEMI,
  MACRO_ITEM,
  MACRO_MATCH,
  MACRO_MATCHER,
  MACRO_REP_OP,
  MACRO_REP_SEP,
  MACRO_RULE,
  MACRO_RULES,
  MACRO_RULES_DEF,
  MACRO_RULES_DEFINITION,
  MACRO_TRANSCRIBER,
  MATCH_ARM,
  MATCH_ARMS,
  MATCH_ARM_GUARD,
  MATCH_EXPRESSION,
  MAYBE_NAMED_FUNCTION_PARAMETERS,
  MAYBE_NAMED_FUNCTION_PARAMETERS_VARIADIC,
  MAYBE_NAMED_PARAM, STRUCT_FIELDS,
  META_ITEM,
  META_ITEM_INNER,
  META_LIST_IDENTS,
  META_LIST_NAME_VALUE_STR,
  META_LIST_PATHS,
  META_NAME_VALUE_STR,
  META_SEQ,
  META_WORD,
  METHOD_CALL_EXPRESSION,
  MINUSEQ_EXPRESSION,
  MODULE,
  MULTIPLICATION_EXPRESSION,
  NEGATION_EXPRESSION,
  NEGATION_EXPRESSION_EXCEPT_STRUCT,
  NEQ_EXPRESSION,
  NEVER_TYPE,
  NON_KEYWORD_IDENTIFIER,
  NON_ZERO_DEC_DIGIT,
  OBSOLETE_RANGE_PATTERN,
  OCT_DIGIT,
  OCT_LITERAL,
  OPERATOR_EXPRESSION,
  OREQ_EXPRESSION,
  OUTER_ATTRIBUTE,
  OUTER_BLOCK_DOC,
  OUTER_LINE_DOC,
  PARENTHESIZED_TYPE,
  PATH_EXPRESSION,
  PATH_EXPR_SEGMENT,
  PATH_IDENT_SEGMENT,
  PATH_IN_EXPRESSION,
  PATH_PATTERN,
  PATTERN,
  PATTERN_NO_TOP_ALT,
  PATTERN_WITHOUT_RANGE,
  PERCENTEQ_EXPRESSION,
  PLUSEQ_EXPRESSION,
  PREDICATE_LOOP_EXPRESSION,
  PREDICATE_PATTERN_LOOP_EXPRESSION,
  PUNCTUATION,
  PUNCTUATION_EXCEPT_DOLLAR,
  PUNCTUATION_EXCEPT_SEMI,
  QUALIFIED_PATH_IN_EXPRESSION,
  QUALIFIED_PATH_IN_TYPE,
  QUALIFIED_PATH_TYPE,
  QUOTE_ESCAPE,
  RANGE_EXPR,
  RANGE_EXPRESSION,
  RANGE_FROM_EXPR,
  RANGE_FULL_EXPR,
  RANGE_INCLUSIVE_EXPR,
  RANGE_PATTERN,
  RANGE_PATTERN_BOUND,
  RANGE_TO_EXPR,
  RANGE_TO_INCLUSIVE_EXPR,
  RAW_BYTE_STRING_CONTENT,
  RAW_BYTE_STRING_LITERAL,
  RAW_IDENTIFIER,
  RAW_POINTER_TYPE,
  RAW_STRING_CONTENT,
  RAW_STRING_LITERAL,
  REFERENCE_PATTERN,
  REFERENCE_TYPE,
  REMAINDER_EXPRESSION,
  REST_PATTERN,
  RETURN_EXPRESSION,
  SELF_PARAM,
  SHLEQ_EXPRESSION,
  SHL_EXPRESSION,
  SHORTHAND_SELF,
  SHREQ_EXPRESSION,
  SHR_EXPRESSION,
  SIMPLE_PATH,
  SIMPLE_PATH_SEGMENT,
  SLASHEQ_EXPRESSION,
  SLICE_PATTERN,
  SLICE_TYPE,
  SPC,
  STAREQ_EXPRESSION,
  STATEMENT,
  STATEMENTS,
  STATIC_ITEM,
  STRING_CONTENT,
  STRING_CONTINUE,
  STRING_LITERAL,
  STRUCT,
  STRUCT_BASE,
  STRUCT_EXPRESSION,
  STRUCT_EXPR_FIELD,
  STRUCT_EXPR_FIELDS,
  STRUCT_EXPR_STRUCT,
  STRUCT_EXPR_TUPLE,
  STRUCT_EXPR_UNIT,
  STRUCT_FIELD,
  STRUCT_PATTERN,
  STRUCT_PATTERN_ELEMENTS,
  STRUCT_PATTERN_ETCETERA,
  STRUCT_PATTERN_FIELD,
  STRUCT_PATTERN_FIELDS,
  STRUCT_STRUCT,
  SUBTRACTION_EXPRESSION,
  TOKEN,
  TOKEN_EXCEPT_DELIMITERS,
  TOKEN_MACRO,
  TOKEN_TREE,
  TRAIT,
  TRAIT_BOUND,
  TRAIT_IMPL,
  TRAIT_OBJECT_TYPE,
  TRAIT_OBJECT_TYPE_ONE_BOUND,
  TUPLE_ELEMENT,
  TUPLE_EXPRESSION,
  TUPLE_FIELD,
  TUPLE_FIELDS,
  TUPLE_INDEX,
  TUPLE_INDEXING_EXPRESSION,
  TUPLE_PATTERN,
  TUPLE_PATTERN_ITEMS,
  TUPLE_STRUCT,
  TUPLE_STRUCT_ITEMS,
  TUPLE_STRUCT_PATTERN,
  TUPLE_TYPE,
  TYPE,
  TYPED_SELF,
  TYPE_ALIAS,
  TYPE_BOUND_CLAUSE_ITEM,
  TYPE_CAST_EXPRESSION,
  TYPE_NO_BOUNDS,
  TYPE_PARAM,
  TYPE_PARAM_BOUND,
  TYPE_PARAM_BOUNDS,
  TYPE_PATH,
  TYPE_PATH_FN,
  TYPE_PATH_FN_INPUTS,
  TYPE_PATH_SEGMENT,
  UNICODE_ESCAPE,
  UNION,
  UNKNOWN_CHAR,
  UNSAFE_BLOCK_EXPRESSION,
  USE_DECLARATION,
  USE_TREE,
  VISIBILITY,
  VIS_ITEM,
  WHERE_CLAUSE,
  WHERE_CLAUSE_ITEM,
  WILDCARD_PATTERN;

  private static final String DOLLAR_CRATE_REGEX = "^\\$crate$";

  public static LexerlessGrammarBuilder create() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

    b.rule(COMPILATION_UNIT).is(SPC, b.zeroOrMore(INNER_ATTRIBUTE, SPC), SPC, b.zeroOrMore(STATEMENT, SPC), EOF);

    punctuators(b);
    keywords(b);
    literals(b);
    lexical(b);
    types(b);
    attributes(b);
    expressions(b);
    items(b);
    macros(b);
    patterns(b);
    statement(b);

    b.setRootRule(COMPILATION_UNIT);

    return b;
  }

  private static Object inlineComment(LexerlessGrammarBuilder b) {
    return b.regexp("//[^\\n\\r]*+");
  }

  private static Object multilineComment(LexerlessGrammarBuilder b) {
    return b.regexp("/\\*[\\s\\S]*?\\*\\/");
  }

  private static void literals(LexerlessGrammarBuilder b) {
    b.rule(SPC).is(
      b.skippedTrivia(whitespace(b)),
      b.zeroOrMore(
        b.commentTrivia(b.firstOf(inlineComment(b), multilineComment(b))),
        b.skippedTrivia(whitespace(b))));

    b.rule(EOF).is(b.token(GenericTokenType.EOF, b.endOfInput())).skip();

    b.rule(UNKNOWN_CHAR).is(
      b.token(GenericTokenType.UNKNOWN_CHAR, b.regexp("(?s).")),
      SPC).skip();

    b.rule(CHAR_LITERAL).is(b.token(RustTokenType.CHARACTER_LITERAL,
      b.sequence("'",
        b.firstOf(
          UNICODE_ESCAPE,
          QUOTE_ESCAPE,
          ASCII_ESCAPE,
          b.regexp("[\\x00-\\x08\\x11-\\x12\\x14-\\x26\\x28-x5b\\x5d-\\x{1ffff}]")),
        "'"

      ))).skip();

    b.rule(STRING_CONTENT).is(b.oneOrMore(b.regexp("[\\x{0000}-\\x{0021}\\x{0023}-\\x{005b}\\x{005d}-\\x{1ffff}]")));

    b.rule(STRING_LITERAL).is(b.token(RustTokenType.STRING_LITERAL,
      b.sequence(
        "\"", b.zeroOrMore(b.firstOf(
          UNICODE_ESCAPE,
          "\\n//",
          "\\\\",
          QUOTE_ESCAPE

          , ASCII_ESCAPE

          , STRING_CONTINUE, STRING_CONTENT), SPC),
        "\"")));

    comments(b);
  }

  private static void comments(LexerlessGrammarBuilder b) {
    b.rule(LINE_COMMENT).is(b.commentTrivia(
      b.regexp("////[^!/\\n]*|//[^!/\\n]*")));

    b.rule(BLOCK_COMMENT).is(b.commentTrivia(
      b.firstOf(
        "/***/",
        "/**/",
        b.regexp("^\\/\\*.*\\*\\/"))));

    b.rule(INNER_LINE_DOC).is(b.commentTrivia(b.regexp("(?!\\n\\r)//!.*")));
    b.rule(INNER_BLOCK_DOC).is(
      b.regexp("^\\/\\*!.*\\*\\/"));
    b.rule(OUTER_LINE_DOC).is(b.commentTrivia(b.regexp("///[^\\r\\n\\/]*")));
    b.rule(OUTER_BLOCK_DOC).is(b.regexp("^\\/\\*\\*[^\\r\\n\\*].*\\*\\/"));
    b.rule(BLOCK_COMMENT_OR_DOC).is(b.commentTrivia(
      b.firstOf(BLOCK_COMMENT, OUTER_BLOCK_DOC, INNER_BLOCK_DOC)));
  }

  private static String[] getPunctuatorsExcept(String[] arr, String toRemove) {
    int newLength = arr.length;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].contains(toRemove)) {
        newLength--;
      }
    }
    String[] result = new String[newLength];
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
      if (!arr[i].contains(toRemove)) {
        result[count] = arr[i];
        count++;
      }
    }
    return result;
  }

  private static void punctuators(LexerlessGrammarBuilder b) {
    for (RustPunctuator tokenType : RustPunctuator.values()) {
      b.rule(tokenType).is(tokenType.getValue());
    }
    String[] punctuators = RustPunctuator.punctuatorValues();

    String[] punctuatorsExceptDollar = RustGrammar.getPunctuatorsExcept(punctuators, "$");
    String[] punctuatorsExceptSemi = RustGrammar.getPunctuatorsExcept(punctuators, ";");

    Arrays.sort(punctuators);
    ArrayUtils.reverse(punctuators);
    b.rule(PUNCTUATION).is(
      b.firstOf(
        punctuators[0],
        punctuators[1],
        ArrayUtils.subarray(punctuators, 2, punctuators.length)));
    Arrays.sort(punctuatorsExceptDollar);
    ArrayUtils.reverse(punctuatorsExceptDollar);
    Arrays.sort(punctuatorsExceptSemi);
    ArrayUtils.reverse(punctuatorsExceptSemi);
    b.rule(PUNCTUATION_EXCEPT_DOLLAR).is(
      b.firstOf(
        punctuatorsExceptDollar[0],
        punctuatorsExceptDollar[1],
        ArrayUtils.subarray(punctuatorsExceptDollar, 2, punctuatorsExceptDollar.length)));
    b.rule(PUNCTUATION_EXCEPT_SEMI).is(
      b.firstOf(
        punctuatorsExceptSemi[0],
        punctuatorsExceptSemi[1],
        ArrayUtils.subarray(punctuatorsExceptSemi, 2, punctuatorsExceptSemi.length)));
  }

  private static void keywords(LexerlessGrammarBuilder b) {
    for (RustKeyword tokenType : RustKeyword.values()) {
      b.rule(tokenType).is(tokenType.getValue(), SPC);
    }
    String[] keywords = RustKeyword.keywordValues();
    Arrays.sort(keywords);
    ArrayUtils.reverse(keywords);
    b.rule(KEYWORD).is(
      b.firstOf(
        keywords[0],
        keywords[1],
        ArrayUtils.subarray(keywords, 2, keywords.length)));
  }

  private static Object whitespace(LexerlessGrammarBuilder b) {
    return b.skippedTrivia(b.regexp("[ \t\n\r]*+"));
  }

  /* recurring grammar pattern */
  private static Object seq(LexerlessGrammarBuilder b, GrammarRuleKey g, RustPunctuator sep) {
    return b.sequence(g, b.sequence(b.zeroOrMore(SPC, sep, SPC, g),
      b.optional(SPC, sep, SPC)));
  }

  private static void items(LexerlessGrammarBuilder b) {
    b.rule(ITEM).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(VIS_ITEM, MACRO_ITEM));
    b.rule(VIS_ITEM).is(b.optional(VISIBILITY, SPC), b.firstOf(
      MODULE,
      EXTERN_CRATE,
      USE_DECLARATION,
      FUNCTION,
      TYPE_ALIAS,
      STRUCT,
      ENUMERATION,
      UNION,
      CONSTANT_ITEM,
      STATIC_ITEM,
      TRAIT,
      IMPLEMENTATION,
      EXTERN_BLOCK));
    b.rule(MACRO_ITEM).is(b.firstOf(MACRO_INVOCATION_SEMI, MACRO_RULES_DEFINITION));
    modules(b);
    externcrates(b);
    useItem(b);
    aliasItem(b);
    functionsItem(b);
    structsItem(b);
    enumerationsItem(b);
    unionsItem(b);
    constantsItem(b);
    staticItem(b);
    traitsItem(b);
    implItem(b);
    extblocksItem(b);
    genericItem(b);
    assocItem(b);
    visibilityItem(b);
  }

  /* https://doc.rust-lang.org/reference/items/traits.html */
  private static void traitsItem(LexerlessGrammarBuilder b) {
    b.rule(TRAIT).is(
      b.optional(RustKeyword.KW_UNSAFE, SPC),
      RustKeyword.KW_TRAIT, SPC, IDENTIFIER, SPC,
      b.optional(GENERIC_PARAMS, SPC),
      b.optional(RustPunctuator.COLON, b.optional(TYPE_PARAM_BOUNDS)),
      b.optional(WHERE_CLAUSE), "{", SPC, b.zeroOrMore(INNER_ATTRIBUTE, SPC),
      b.zeroOrMore(ASSOCIATED_ITEM, SPC), SPC, "}");

  }

  /* https://doc.rust-lang.org/reference/items/enumerations.html */
  private static void enumerationsItem(LexerlessGrammarBuilder b) {
    b.rule(ENUMERATION).is(RustKeyword.KW_ENUM, SPC, IDENTIFIER, SPC,
      b.optional(GENERIC_PARAMS, SPC), b.optional(WHERE_CLAUSE, SPC), "{",
      SPC, b.optional(ENUM_ITEMS), SPC, "}");
    b.rule(ENUM_ITEMS).is(seq(b, ENUM_ITEM, RustPunctuator.COMMA));
    b.rule(ENUM_ITEM).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC), b.optional(VISIBILITY, SPC),
      IDENTIFIER, SPC, b.optional(b.firstOf(ENUM_ITEM_TUPLE, ENUM_ITEM_STRUCT, ENUM_ITEM_DISCRIMINANT)));
    b.rule(ENUM_ITEM_TUPLE).is("(", SPC, b.optional(TUPLE_FIELDS), SPC, ")");
    b.rule(ENUM_ITEM_STRUCT).is("{", SPC, b.optional(STRUCT_FIELDS), SPC, "}");
    b.rule(ENUM_ITEM_DISCRIMINANT).is(RustPunctuator.EQ, SPC, EXPRESSION);

  }

  /* https://doc.rust-lang.org/reference/items/type-aliases.html */
  private static void aliasItem(LexerlessGrammarBuilder b) {

    b.rule(TYPE_ALIAS).is(
      RustKeyword.KW_TYPE, SPC, IDENTIFIER, SPC, b.optional(GENERIC_PARAMS, SPC),
      b.optional(RustPunctuator.COLON, SPC, TYPE_PARAM_BOUNDS, SPC),
      b.optional(WHERE_CLAUSE, SPC),
      b.optional(RustPunctuator.EQ, SPC, TYPE, SPC),
      RustPunctuator.SEMI);
  }

  private static void useItem(LexerlessGrammarBuilder b) {
    b.rule(USE_DECLARATION).is(RustKeyword.KW_USE, SPC, USE_TREE, SPC, RustPunctuator.SEMI);

    b.rule(USE_TREE).is(b.firstOf(
      b.sequence(b.optional(b.optional(SIMPLE_PATH), RustPunctuator.PATHSEP), RustPunctuator.STAR),
      b.sequence(b.optional(b.optional(SIMPLE_PATH), RustPunctuator.PATHSEP),
        "{", SPC, b.optional(seq(b, USE_TREE, RustPunctuator.COMMA)), SPC, "}"),
      b.sequence(SIMPLE_PATH, SPC, b.optional(
        RustKeyword.KW_AS, SPC, b.firstOf(IDENTIFIER, RustPunctuator.UNDERSCORE)))));

  }

  private static void functionsItem(LexerlessGrammarBuilder b) {
    b.rule(FUNCTION).is(
      FUNCTION_QUALIFIERS, SPC, RustKeyword.KW_FN, SPC, IDENTIFIER,
      b.optional(GENERIC_PARAMS, SPC), SPC,
      "(", SPC, b.optional(FUNCTION_PARAMETERS, SPC), SPC, ")", SPC,
      b.optional(SPC, FUNCTION_RETURN_TYPE, SPC), b.optional(WHERE_CLAUSE, SPC), SPC,
      b.firstOf(BLOCK_EXPRESSION, RustPunctuator.SEMI));
    b.rule(FUNCTION_QUALIFIERS).is(
      b.optional(RustKeyword.KW_CONST, SPC),
      b.optional(RustKeyword.KW_ASYNC, SPC),
      b.optional(RustKeyword.KW_UNSAFE, SPC),
      b.optional(RustKeyword.KW_EXTERN, SPC, b.optional(ABI)));

    b.rule(ABI).is(b.firstOf(RAW_STRING_LITERAL, STRING_LITERAL));
    b.rule(FUNCTION_PARAMETERS).is(
      b.firstOf(b.sequence(SELF_PARAM, b.optional(RustPunctuator.COMMA), SPC,
        b.optional(seq(b, FUNCTION_PARAM, RustPunctuator.COMMA))),
        seq(b, FUNCTION_PARAM, RustPunctuator.COMMA)));

    b.rule(FUNCTION_PARAM).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC), SPC,
      b.firstOf(FUNCTION_PARAM_PATTERN, RustPunctuator.DOTDOTDOT, TYPE));

    b.rule(FUNCTION_PARAM_PATTERN).is(PATTERN_NO_TOP_ALT, SPC, RustPunctuator.COLON,
      SPC, b.firstOf(TYPE, RustPunctuator.DOTDOTDOT));

    b.rule(FUNCTION_RETURN_TYPE).is(RustPunctuator.RARROW, SPC, TYPE);

    b.rule(SELF_PARAM).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC), b.firstOf(
      TYPED_SELF, SHORTHAND_SELF));

    b.rule(SHORTHAND_SELF).is(
      b.optional(b.firstOf(b.sequence(RustPunctuator.AND, LIFETIME, SPC), RustPunctuator.AND)),
      b.optional(RustKeyword.KW_MUT, SPC), RustKeyword.KW_SELF_VALUE);

    b.rule(TYPED_SELF).is(b.optional(RustKeyword.KW_MUT, SPC), RustKeyword.KW_SELF_VALUE, SPC,
      RustPunctuator.COLON, SPC, TYPE);

  }

  /* https://doc.rust-lang.org/reference/items/structs.html */
  private static void structsItem(LexerlessGrammarBuilder b) {
    b.rule(STRUCT).is(b.firstOf(STRUCT_STRUCT, TUPLE_STRUCT));
    b.rule(STRUCT_STRUCT).is(
      RustKeyword.KW_STRUCT, SPC, IDENTIFIER, SPC, b.optional(GENERIC_PARAMS, SPC),
      b.optional(WHERE_CLAUSE, SPC),
      b.firstOf(b.sequence("{", SPC, b.optional(STRUCT_FIELDS, SPC), "}"),
        RustPunctuator.SEMI));
    b.rule(TUPLE_STRUCT).is(
      RustKeyword.KW_STRUCT, SPC, IDENTIFIER, SPC, b.optional(GENERIC_PARAMS, SPC), "(", SPC,
      b.optional(TUPLE_FIELDS, SPC), ")", SPC,
      b.optional(WHERE_CLAUSE, SPC), RustPunctuator.SEMI);
    b.rule(STRUCT_FIELDS).is(seq(b, STRUCT_FIELD, RustPunctuator.COMMA));
    b.rule(STRUCT_FIELD).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC), SPC,
      b.optional(VISIBILITY), SPC,
      IDENTIFIER, SPC, RustPunctuator.COLON, SPC, TYPE);
    b.rule(TUPLE_FIELDS).is(seq(b, TUPLE_FIELD, RustPunctuator.COMMA));
    b.rule(TUPLE_FIELD).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.optional(VISIBILITY, SPC), TYPE);

  }

  /* https://doc.rust-lang.org/reference/items/unions.html */
  private static void unionsItem(LexerlessGrammarBuilder b) {
    b.rule(UNION).is(
      RustKeyword.KW_UNION, SPC, IDENTIFIER, SPC,
      b.optional(GENERIC_PARAMS, SPC),
      b.optional(WHERE_CLAUSE, SPC),
      "{", SPC, STRUCT_FIELDS, SPC, "}");
  }

  /* https://doc.rust-lang.org/reference/items/constant-items.html */
  private static void constantsItem(LexerlessGrammarBuilder b) {
    b.rule(CONSTANT_ITEM).is(
      RustKeyword.KW_CONST, SPC, b.firstOf(IDENTIFIER, RustPunctuator.UNDERSCORE), SPC,
      RustPunctuator.COLON, SPC, TYPE,
      SPC, b.optional(RustPunctuator.EQ, SPC, EXPRESSION, SPC), RustPunctuator.SEMI);

  }

  /* https://doc.rust-lang.org/reference/items/static-items.html */
  private static void staticItem(LexerlessGrammarBuilder b) {
    b.rule(STATIC_ITEM).is(
      RustKeyword.KW_STATIC, SPC, b.optional(RustKeyword.KW_MUT, SPC), IDENTIFIER,
      SPC, RustPunctuator.COLON, SPC, TYPE, SPC, b.optional(RustPunctuator.EQ, SPC, EXPRESSION, SPC), RustPunctuator.SEMI);
  }

  /* https://doc.rust-lang.org/reference/items/implementations.html */
  private static void implItem(LexerlessGrammarBuilder b) {
    b.rule(IMPLEMENTATION).is(b.firstOf(INHERENT_IMPL, TRAIT_IMPL));
    b.rule(INHERENT_IMPL).is(
      RustKeyword.KW_IMPL, SPC, b.optional(GENERIC_PARAMS, SPC), TYPE, SPC,
      b.optional(WHERE_CLAUSE, SPC), "{", SPC,
      b.zeroOrMore(INNER_ATTRIBUTE, SPC),
      b.zeroOrMore(ASSOCIATED_ITEM, SPC), "}");

    b.rule(TRAIT_IMPL).is(
      b.optional(RustKeyword.KW_UNSAFE, SPC), RustKeyword.KW_IMPL, SPC,
      b.optional(GENERIC_PARAMS, SPC),
      b.optional(RustPunctuator.NOT, SPC), TYPE_PATH, SPC, RustKeyword.KW_FOR, SPC, TYPE, SPC,
      b.optional(WHERE_CLAUSE, SPC), "{", SPC,
      b.zeroOrMore(INNER_ATTRIBUTE, SPC),
      b.zeroOrMore(ASSOCIATED_ITEM, SPC), "}");

  }

  /* https://doc.rust-lang.org/reference/items/external-blocks.html */
  private static void extblocksItem(LexerlessGrammarBuilder b) {
    b.rule(EXTERN_BLOCK).is(b.optional(RustKeyword.KW_UNSAFE),
      RustKeyword.KW_EXTERN, SPC, b.optional(ABI), SPC, "{", SPC,
      b.zeroOrMore(INNER_ATTRIBUTE, SPC),
      b.zeroOrMore(EXTERNAL_ITEM, SPC), "}");
    b.rule(EXTERNAL_ITEM).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC), SPC,
      b.firstOf(MACRO_INVOCATION_SEMI,
        b.sequence(
          b.optional(VISIBILITY, SPC),
          b.firstOf(STATIC_ITEM, FUNCTION))));
  }

  /* https://doc.rust-lang.org/reference/items/generics.html */
  private static void genericItem(LexerlessGrammarBuilder b) {

    b.rule(GENERIC_PARAMS).is(b.firstOf(
      b.sequence(RustPunctuator.LT, SPC, seq(b, GENERIC_PARAM, RustPunctuator.COMMA), SPC, RustPunctuator.GT),
      b.sequence(RustPunctuator.LT, SPC, RustPunctuator.GT)

    ));

    b.rule(GENERIC_PARAM).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(LIFETIME_PARAM, CONST_PARAM, TYPE_PARAM));

    b.rule(LIFETIME_PARAM).is(LIFETIME_OR_LABEL, SPC,
      b.optional(RustPunctuator.COLON, SPC, LIFETIME_BOUNDS, SPC));
    b.rule(TYPE_PARAM).is(
      b.optional(OUTER_ATTRIBUTE, SPC), NON_KEYWORD_IDENTIFIER, SPC,
      b.optional(RustPunctuator.COLON, b.optional(TYPE_PARAM_BOUNDS), SPC),
      b.optional(RustPunctuator.EQ, SPC, TYPE));

    b.rule(CONST_PARAM).is(RustKeyword.KW_CONST, SPC, IDENTIFIER, SPC, RustPunctuator.COLON, SPC, TYPE);
    b.rule(WHERE_CLAUSE).is(
      RustKeyword.KW_WHERE, b.zeroOrMore(b.sequence(WHERE_CLAUSE_ITEM, SPC, RustPunctuator.COMMA, SPC)), b.optional(WHERE_CLAUSE_ITEM));
    b.rule(WHERE_CLAUSE_ITEM).is(b.firstOf(
      LIFETIME_WHERE_CLAUSE_ITEM, TYPE_BOUND_CLAUSE_ITEM));
    b.rule(LIFETIME_WHERE_CLAUSE_ITEM).is(LIFETIME, SPC, RustPunctuator.COLON, SPC, LIFETIME_BOUNDS);
    b.rule(TYPE_BOUND_CLAUSE_ITEM).is(
      b.optional(FOR_LIFETIMES), TYPE, RustPunctuator.COLON, b.optional(TYPE_PARAM_BOUNDS));

    b.rule(FOR_LIFETIMES).is(RustKeyword.KW_FOR, SPC, GENERIC_PARAMS);

  }

  private static void assocItem(LexerlessGrammarBuilder b) {

    b.rule(ASSOCIATED_ITEM).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(MACRO_INVOCATION_SEMI,
        b.sequence(b.optional(VISIBILITY, SPC), b.firstOf(
          TYPE_ALIAS, CONSTANT_ITEM, FUNCTION))));

  }

  private static void visibilityItem(LexerlessGrammarBuilder b) {
    b.rule(VISIBILITY).is(b.firstOf(
      b.sequence(RustKeyword.KW_PUB, SPC, "(", SPC, RustKeyword.KW_CRATE, SPC, ")"),
      b.sequence(RustKeyword.KW_PUB, SPC, "(", SPC, RustKeyword.KW_SELF_VALUE, SPC, ")"),
      b.sequence(RustKeyword.KW_PUB, SPC, "(", SPC, RustKeyword.KW_SUPER, SPC, ")"),
      b.sequence(RustKeyword.KW_PUB, SPC, "(", SPC, RustKeyword.KW_IN, SIMPLE_PATH, SPC, ")"),
      RustKeyword.KW_PUB

    ));
  }

  private static void externcrates(LexerlessGrammarBuilder b) {
    b.rule(EXTERN_CRATE).is(
      RustKeyword.KW_EXTERN, SPC, RustKeyword.KW_CRATE, SPC, CRATE_REF, SPC, b.optional(SPC, AS_CLAUSE, SPC), RustPunctuator.SEMI);
    b.rule(CRATE_REF).is(b.firstOf(RustKeyword.KW_SELF_VALUE, IDENTIFIER));
    b.rule(AS_CLAUSE).is(RustKeyword.KW_AS, SPC, b.firstOf(IDENTIFIER, RustPunctuator.UNDERSCORE));

  }

  private static void modules(LexerlessGrammarBuilder b) {
    b.rule(MODULE).is(b.firstOf(
      b.sequence(b.optional(RustKeyword.KW_UNSAFE, SPC), RustKeyword.KW_MOD, SPC, IDENTIFIER, SPC, RustPunctuator.SEMI),
      b.sequence(b.optional(RustKeyword.KW_UNSAFE, SPC), RustKeyword.KW_MOD, SPC, IDENTIFIER, SPC, "{", SPC,
        b.zeroOrMore(INNER_ATTRIBUTE, SPC),
        b.zeroOrMore(ITEM, SPC), "}")));

  }

  private static void lexical(LexerlessGrammarBuilder b) {
    // not explicit in reference

    lexicalpath(b);
    lexicaltoken(b);
  }

  /* https://doc.rust-lang.org/reference/macros.html */
  private static void macros(LexerlessGrammarBuilder b) {
    b.rule(MACRO_INVOCATION).is(
      SIMPLE_PATH, RustPunctuator.NOT, SPC, DELIM_TOKEN_TREE);

    b.rule(DELIM_TOKEN_TREE).is(b.firstOf(
      b.sequence("(", SPC, b.zeroOrMore(TOKEN_TREE, SPC), SPC, ")"),
      b.sequence("[", SPC, b.zeroOrMore(TOKEN_TREE, SPC), SPC, "]"),
      b.sequence("{", SPC, b.zeroOrMore(TOKEN_TREE, SPC), SPC, "}")));

    b.rule(TOKEN_EXCEPT_DELIMITERS).is(b.firstOf(
      LITERALS, IDENTIFIER_OR_KEYWORD, LIFETIMES, PUNCTUATION));
    b.rule(TOKEN_TREE).is(
      b.firstOf(
        TOKEN_EXCEPT_DELIMITERS,
        DELIM_TOKEN_TREE));
    b.rule(MACRO_INVOCATION_SEMI).is(b.firstOf(
      b.sequence(SIMPLE_PATH, RustPunctuator.NOT, SPC, "(", b.zeroOrMore(SPC, TOKEN_TREE, SPC), ");"),
      b.sequence(SIMPLE_PATH, RustPunctuator.NOT, SPC, "[", b.zeroOrMore(SPC, TOKEN_TREE, SPC), "];"),
      b.sequence(SIMPLE_PATH, RustPunctuator.NOT, SPC, "{", b.zeroOrMore(SPC, TOKEN_TREE, SPC), "}")));
    macrosByExample(b);
  }

  /* https://doc.rust-lang.org/reference/macros-by-example.html */
  private static void macrosByExample(LexerlessGrammarBuilder b) {
    b.rule(MACRO_RULES_DEFINITION).is(
      "macro_rules!", SPC, IDENTIFIER, SPC, MACRO_RULES_DEF);
    b.rule(MACRO_RULES_DEF).is(b.firstOf(
      b.sequence("(", SPC, MACRO_RULES, SPC, ")", SPC, RustPunctuator.SEMI),
      b.sequence("[", SPC, MACRO_RULES, SPC, "]", SPC, RustPunctuator.SEMI),
      b.sequence("{", SPC, MACRO_RULES, SPC, "}")));
    b.rule(MACRO_RULES).is(
      seq(b, MACRO_RULE, RustPunctuator.SEMI));
    b.rule(MACRO_RULE).is(MACRO_MATCHER, SPC, RustPunctuator.FATARROW, SPC, MACRO_TRANSCRIBER);
    b.rule(MACRO_MATCHER).is(b.firstOf(
      b.sequence("(", SPC, b.zeroOrMore(MACRO_MATCH, SPC), SPC, ")"),
      b.sequence("[", SPC, b.zeroOrMore(MACRO_MATCH, SPC), SPC, "]"),
      b.sequence("{", SPC, b.zeroOrMore(MACRO_MATCH, SPC), SPC, "}")));

    b.rule(MACRO_MATCH).is(b.firstOf(

      b.sequence("$", IDENTIFIER, SPC, RustPunctuator.COLON, SPC, MACRO_FRAG_SPEC),

      b.sequence("$", SPC, "(", SPC, b.oneOrMore(MACRO_MATCH, SPC), ")", b.optional(MACRO_REP_SEP) // MacroRepSep
        , b.firstOf("+", "*", "?")),
      TOKEN_MACRO,
      MACRO_MATCHER));

    b.rule(TOKEN_MACRO).is(b.firstOf(LITERALS, IDENTIFIER_OR_KEYWORD,
      LIFETIMES, PUNCTUATION_EXCEPT_DOLLAR));
    b.rule(MACRO_FRAG_SPEC).is(b.firstOf(
      "block", "expr", "ident", "item", "lifetime", "literal", "meta", "path", "pat_param", "pat", "stmt", "tt", "ty", "vis"));
    b.rule(MACRO_REP_SEP).is(b.firstOf(LITERALS, IDENTIFIER_OR_KEYWORD,
      LIFETIMES,
      RustPunctuator.MINUS,
      RustPunctuator.SLASH,
      RustPunctuator.PERCENT,
      RustPunctuator.CARET,
      RustPunctuator.NOT,
      RustPunctuator.AND,
      RustPunctuator.OR,
      RustPunctuator.ANDAND,
      RustPunctuator.OROR,
      RustPunctuator.SHL,
      RustPunctuator.SHR,
      RustPunctuator.PLUSEQ,
      RustPunctuator.MINUSEQ,
      RustPunctuator.STAREQ,
      RustPunctuator.SLASHEQ,
      RustPunctuator.PERCENTEQ,
      RustPunctuator.CARETEQ,
      RustPunctuator.ANDEQ,
      RustPunctuator.OREQ,
      RustPunctuator.SHLEQ,
      RustPunctuator.SHREQ,
      RustPunctuator.EQ,
      RustPunctuator.EQEQ,
      RustPunctuator.NE,
      RustPunctuator.GT,
      RustPunctuator.LT,
      RustPunctuator.GE,
      RustPunctuator.LE,
      RustPunctuator.AT,
      RustPunctuator.UNDERSCORE,
      RustPunctuator.DOT,
      RustPunctuator.DOTDOT,
      RustPunctuator.DOTDOTDOT,
      RustPunctuator.DOTDOTEQ,
      RustPunctuator.COMMA,
      RustPunctuator.SEMI,
      RustPunctuator.PATHSEP,
      RustPunctuator.COLON,
      RustPunctuator.RARROW,
      RustPunctuator.FATARROW,
      RustPunctuator.POUND,
      RustPunctuator.DOLLAR)); // Token except delimiters and repetition operators
    b.rule(MACRO_REP_OP).is(b.firstOf(RustPunctuator.STAR, RustPunctuator.PLUS, RustPunctuator.QUESTION));
    b.rule(MACRO_TRANSCRIBER).is(DELIM_TOKEN_TREE);

  }

  private static void patterns(LexerlessGrammarBuilder b) {

    b.rule(PATTERN).is(
      b.optional(RustPunctuator.OR, SPC),
      PATTERN_NO_TOP_ALT, SPC,
      b.zeroOrMore(b.sequence(RustPunctuator.OR, SPC, PATTERN_NO_TOP_ALT, SPC)));
    b.rule(PATTERN_NO_TOP_ALT).is(b.firstOf(
      RANGE_PATTERN,
      PATTERN_WITHOUT_RANGE));

    b.rule(PATTERN_WITHOUT_RANGE).is(b.firstOf(
      TUPLE_STRUCT_PATTERN,
      STRUCT_PATTERN,
      MACRO_INVOCATION,
      // unambigous PATH_PATTERN,
      b.firstOf(
        b.sequence(b.optional(RustPunctuator.PATHSEP),
          // PATH_EXPR_SEGMENT,
          b.sequence(b.firstOf(
            b.sequence(RustKeyword.KW_SUPER, b.nextNot(IDENTIFIER)),
            b.regexp("^[sS]elf$"), RustKeyword.KW_CRATE, b.regexp(DOLLAR_CRATE_REGEX), IDENTIFIER), b.optional(b.sequence(RustPunctuator.PATHSEP, GENERIC_ARGS))),
          b.oneOrMore(b.sequence(RustPunctuator.PATHSEP, PATH_EXPR_SEGMENT))),
        QUALIFIED_PATH_IN_EXPRESSION),
      BYTE_LITERAL,
      RAW_STRING_LITERAL,
      BYTE_STRING_LITERAL,
      RAW_BYTE_STRING_LITERAL,
      IDENTIFIER_PATTERN,
      BOOLEAN_LITERAL,
      CHAR_LITERAL,
      STRING_LITERAL,
      b.sequence(b.optional("-"), INTEGER_LITERAL, b.nextNot(RustPunctuator.DOTDOT)),
      b.sequence(b.optional("-"), FLOAT_LITERAL),
      WILDCARD_PATTERN,
      REST_PATTERN,
      REFERENCE_PATTERN,
      TUPLE_PATTERN,
      GROUPED_PATTERN,
      SLICE_PATTERN

    ));

    b.rule(LITERAL_PATTERN).is(b.firstOf(
      BOOLEAN_LITERAL,
      CHAR_LITERAL,
      BYTE_LITERAL,
      STRING_LITERAL,
      RAW_STRING_LITERAL,
      BYTE_STRING_LITERAL,
      RAW_BYTE_STRING_LITERAL,
      b.sequence(b.optional("-"), INTEGER_LITERAL),
      b.sequence(b.optional("-"), FLOAT_LITERAL)));
    b.rule(IDENTIFIER_PATTERN).is(
      b.optional(RustKeyword.KW_REF, b.nextNot(DEC_LITERAL)),
      b.optional(RustKeyword.KW_MUT, b.nextNot(DEC_LITERAL)),
      IDENTIFIER, SPC,
      b.optional(b.sequence("@", SPC, PATTERN)));
    b.rule(WILDCARD_PATTERN).is(RustPunctuator.UNDERSCORE);
    b.rule(REST_PATTERN).is(RustPunctuator.DOTDOT);

    b.rule(RANGE_PATTERN).is(b.firstOf(OBSOLETE_RANGE_PATTERN, INCLUSIVE_RANGE_PATTERN, HALF_OPEN_RANGE_PATTERN));
    b.rule(INCLUSIVE_RANGE_PATTERN).is(b.sequence(RANGE_PATTERN_BOUND, RustPunctuator.DOTDOTEQ, RANGE_PATTERN_BOUND));
    b.rule(HALF_OPEN_RANGE_PATTERN).is(b.sequence(RANGE_PATTERN_BOUND, RustPunctuator.DOTDOT));
    b.rule(OBSOLETE_RANGE_PATTERN).is(b.sequence(RANGE_PATTERN_BOUND, RustPunctuator.DOTDOTDOT, RANGE_PATTERN_BOUND));
    b.rule(RANGE_PATTERN_BOUND).is(b.firstOf(
      CHAR_LITERAL, BYTE_LITERAL, b.sequence(b.optional("-"), INTEGER_LITERAL),
      b.sequence(b.optional("-"), FLOAT_LITERAL),
      PATH_IN_EXPRESSION, QUALIFIED_PATH_IN_EXPRESSION));

    b.rule(REFERENCE_PATTERN).is(
      b.firstOf(RustPunctuator.ANDAND, RustPunctuator.AND),
      b.optional(RustKeyword.KW_MUT),
      PATTERN_WITHOUT_RANGE);
    b.rule(STRUCT_PATTERN).is(
      PATH_IN_EXPRESSION, SPC, "{", SPC, b.optional(STRUCT_PATTERN_ELEMENTS), SPC, "}");
    b.rule(STRUCT_PATTERN_ELEMENTS).is(b.firstOf(
      STRUCT_PATTERN_ETCETERA,
      b.sequence(STRUCT_PATTERN_FIELDS, SPC,
        b.optional(b.firstOf(
          b.sequence(RustPunctuator.COMMA, SPC, STRUCT_PATTERN_ETCETERA),
          RustPunctuator.COMMA

        )))

    ));
    b.rule(STRUCT_PATTERN_FIELDS).is(
      STRUCT_PATTERN_FIELD, b.zeroOrMore(b.sequence(RustPunctuator.COMMA, SPC, STRUCT_PATTERN_FIELD)));
    b.rule(STRUCT_PATTERN_FIELD).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(
        b.sequence(TUPLE_INDEX, SPC, RustPunctuator.COLON, SPC, PATTERN),
        b.sequence(IDENTIFIER, SPC, RustPunctuator.COLON, SPC, PATTERN),
        b.sequence(b.optional(RustKeyword.KW_REF, SPC), b.optional(RustKeyword.KW_MUT, SPC), IDENTIFIER)));
    b.rule(STRUCT_PATTERN_ETCETERA).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC), RustPunctuator.DOTDOT);

    b.rule(TUPLE_STRUCT_PATTERN).is(
      PATH_IN_EXPRESSION, "(", SPC, b.optional(RustKeyword.KW_BOX, SPC), b.optional(TUPLE_STRUCT_ITEMS), ")");
    b.rule(TUPLE_STRUCT_ITEMS).is(seq(b, PATTERN, RustPunctuator.COMMA));

    b.rule(TUPLE_PATTERN).is("(", SPC, b.optional(TUPLE_PATTERN_ITEMS), SPC, ")");

    b.rule(TUPLE_PATTERN_ITEMS).is(b.firstOf(
      seq(b, PATTERN, RustPunctuator.COMMA),
      b.sequence(PATTERN, SPC, RustPunctuator.COMMA, SPC),
      REST_PATTERN

    ));

    b.rule(GROUPED_PATTERN).is("(", SPC, PATTERN, SPC, ")");

    b.rule(SLICE_PATTERN).is("[", b.optional(seq(b, PATTERN, RustPunctuator.COMMA)), "]");
    b.rule(PATH_PATTERN).is(b.firstOf(PATH_IN_EXPRESSION, QUALIFIED_PATH_IN_EXPRESSION));
  }

  private static void types(LexerlessGrammarBuilder b) {
    // 1
    type(b);
    // 5
    tupletype(b);
    // 13
    pointer(b);
    // 14
    functionpointer(b);
    // 15
    trait(b);

  }

  /* https://doc.rust-lang.org/reference/types/function-pointer.html */
  private static void functionpointer(LexerlessGrammarBuilder b) {
    b.rule(BARE_FUNCTION_TYPE).is(
      b.optional(FOR_LIFETIMES), FUNCTION_TYPE_QUALIFIERS, SPC, RustKeyword.KW_FN,
      "(", SPC, b.optional(FUNCTION_PARAMETERS_MAYBE_NAMED_VARIADIC), SPC, ")", SPC,
      b.optional(BARE_FUNCTION_RETURN_TYPE));

    b.rule(FUNCTION_TYPE_QUALIFIERS).is(b.optional(RustKeyword.KW_UNSAFE), b.optional(SPC, RustKeyword.KW_EXTERN, SPC, b.optional(ABI)));

    b.rule(BARE_FUNCTION_RETURN_TYPE).is(RustPunctuator.RARROW, SPC, TYPE_NO_BOUNDS);
    b.rule(FUNCTION_PARAMETERS_MAYBE_NAMED_VARIADIC).is(b.firstOf(
      MAYBE_NAMED_FUNCTION_PARAMETERS, MAYBE_NAMED_FUNCTION_PARAMETERS_VARIADIC));
    b.rule(MAYBE_NAMED_FUNCTION_PARAMETERS).is(seq(b, MAYBE_NAMED_PARAM, RustPunctuator.COMMA));
    b.rule(MAYBE_NAMED_PARAM).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.optional(b.sequence(
        b.firstOf(IDENTIFIER, RustPunctuator.UNDERSCORE), SPC, RustPunctuator.COLON, b.nextNot(RustPunctuator.COLON))),
      SPC, TYPE);
    b.rule(MAYBE_NAMED_FUNCTION_PARAMETERS_VARIADIC).is(
      b.zeroOrMore(b.sequence(MAYBE_NAMED_PARAM, SPC, RustPunctuator.COMMA, SPC)),
      MAYBE_NAMED_PARAM, SPC, RustPunctuator.COMMA, SPC, b.zeroOrMore(OUTER_ATTRIBUTE, SPC), RustPunctuator.DOTDOTDOT);

  }

  public static void statement(LexerlessGrammarBuilder b) {
    b.rule(STATEMENT).is(b.firstOf(
      RustPunctuator.SEMI,
      b.sequence(EXPRESSION_WITHOUT_BLOCK, SPC, RustPunctuator.SEMI),
      b.sequence(EXPRESSION_WITH_BLOCK, SPC, b.optional(SPC, RustPunctuator.SEMI)),
      ITEM,
      LET_STATEMENT,
      MACRO_INVOCATION_SEMI));
    b.rule(LET_STATEMENT).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      RustKeyword.KW_LET, SPC, PATTERN_NO_TOP_ALT, SPC,
      b.optional(RustPunctuator.COLON, SPC, TYPE, SPC),
      b.optional(RustPunctuator.EQ, SPC, EXPRESSION, SPC),
      RustPunctuator.SEMI);

    b.rule(EXPRESSION_STATEMENT).is(b.firstOf(
      b.sequence(EXPRESSION_WITH_BLOCK, b.optional(SPC, RustPunctuator.SEMI)),
      b.sequence(EXPRESSION_WITHOUT_BLOCK, SPC, RustPunctuator.SEMI)));
    b.rule(ANY_TOKEN).is(
      b.firstOf(
        DELIMITERS,
        LITERAL_EXPRESSION,
        IDENTIFIER,
        PUNCTUATION_EXCEPT_SEMI,
        LIFETIME_TOKEN));
  }

  /* https://doc.rust-lang.org/reference/expressions.html */
  public static void expressions(LexerlessGrammarBuilder b) {
    literal(b);
    path(b);
    block(b);
    operator(b);
    grouped(b);
    array(b);
    tuple(b);
    struct(b);
    call(b);
    closure(b);
    loops(b);
    range(b);
    ifExpr(b);
    match(b);
    returnExpr(b);
    await(b);

    b.rule(EXPRESSION).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(
        b.sequence(LITERAL_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(BREAK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),

        b.sequence(BOX_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(RustPunctuator.DOTDOT, b.next(b.firstOf(")", "]", "}"))),
        b.sequence(RANGE_TO_INCLUSIVE_EXPR, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), b.endOfInput()),
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), EXPRESSION),

        b.sequence(BLOCK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(MATCH_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(ASYNC_BLOCK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(UNSAFE_BLOCK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(LOOP_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(IF_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(IF_LET_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(CLOSURE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(BORROW_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(DEREFERENCE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(NEGATION_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(MACRO_INVOCATION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(RETURN_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(STRUCT_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(PATH_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(GROUPED_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(ARRAY_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(TUPLE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(CONTINUE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM))

      ));

    b.rule(SCRUTINEE).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(
        b.sequence(RustKeyword.KW_BREAK, SPC, b.optional(LIFETIME_OR_LABEL, SPC), b.optional(SCRUTINEE, SPC), b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(BOX_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(CONTINUE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(RustPunctuator.DOTDOT, b.next(")")),
        b.sequence(RANGE_TO_INCLUSIVE_EXPR, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), b.endOfInput()),
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), SCRUTINEE),
        b.sequence(LITERAL_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(BLOCK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(MATCH_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(ASYNC_BLOCK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(UNSAFE_BLOCK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(LOOP_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(IF_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(IF_LET_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(CLOSURE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(BORROW_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(RustPunctuator.STAR, SCRUTINEE, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(NEGATION_EXPRESSION_EXCEPT_STRUCT, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(MACRO_INVOCATION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(RETURN_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(PATH_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(STRUCT_EXPRESSION, b.oneOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)), // NB : one or more
        b.sequence(PATH_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(GROUPED_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(ARRAY_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT)),
        b.sequence(TUPLE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM_EXCEPT_STRUCT))));

    b.rule(EXPRESSION_TERM).is(
      b.firstOf(
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), b.endOfInput()),
        b.sequence(RustPunctuator.DOTDOTEQ, EXPRESSION),
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), b.optional(EXPRESSION)),
        b.sequence(RustPunctuator.DOT, RustKeyword.KW_AWAIT, b.nextNot(IDENTIFIER), SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.DOT, PATH_EXPR_SEGMENT, SPC, "(", SPC, b.optional(CALL_PARAMS, SPC), ")", SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.DOT, TUPLE_INDEX, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.DOT, IDENTIFIER, SPC, EXPRESSION_TERM),
        b.sequence("(", SPC, b.optional(CALL_PARAMS), SPC, ")", SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.QUESTION, SPC, EXPRESSION_TERM),
        b.sequence(RustKeyword.KW_AS, SPC, TYPE_NO_BOUNDS, SPC, EXPRESSION_TERM),
        b.sequence("[", SPC, EXPRESSION, SPC, "]", SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.OROR, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.ANDAND, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.NE, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.GT, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.LT, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.GE, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.LE, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.PLUS, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.MINUS, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.STAR, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.SLASH, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.PERCENT, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.AND, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.OR, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.CARET, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.SHL, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.SHR, SPC, EXPRESSION, SPC, EXPRESSION_TERM),
        b.sequence(RustPunctuator.EQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.PLUSEQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.MINUSEQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.STAREQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.SLASHEQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.PERCENTEQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.ANDEQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.OREQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.CARETEQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.SHLEQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.SHREQ, SPC, EXPRESSION, EXPRESSION_TERM),
        b.sequence(RustPunctuator.DOT, RustKeyword.KW_AWAIT, b.nextNot(IDENTIFIER)),
        b.sequence(RustPunctuator.DOT, PATH_EXPR_SEGMENT, SPC, "(", SPC, b.optional(CALL_PARAMS, SPC), ")"),
        b.sequence(RustPunctuator.DOT, TUPLE_INDEX),
        b.sequence(RustPunctuator.DOT, IDENTIFIER),
        b.sequence("(", SPC, b.optional(CALL_PARAMS), SPC, ")"),
        RustPunctuator.QUESTION,
        b.sequence(RustKeyword.KW_AS, SPC, TYPE_NO_BOUNDS),
        b.sequence("[", SPC, EXPRESSION, SPC, "]"),
        b.sequence(RustPunctuator.OROR, SPC, EXPRESSION),
        b.sequence(RustPunctuator.ANDAND, SPC, EXPRESSION),
        b.sequence(RustPunctuator.EQEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.NE, SPC, EXPRESSION),
        b.sequence(RustPunctuator.GT, SPC, EXPRESSION),
        b.sequence(RustPunctuator.LT, SPC, EXPRESSION),
        b.sequence(RustPunctuator.GE, SPC, EXPRESSION),
        b.sequence(RustPunctuator.LE, SPC, EXPRESSION),
        b.sequence(RustPunctuator.PLUS, SPC, EXPRESSION),
        b.sequence(RustPunctuator.MINUS, SPC, EXPRESSION),
        b.sequence(RustPunctuator.STAR, SPC, EXPRESSION),
        b.sequence(RustPunctuator.SLASH, SPC, EXPRESSION),
        b.sequence(RustPunctuator.PERCENT, SPC, EXPRESSION),
        b.sequence(RustPunctuator.AND, SPC, EXPRESSION),
        b.sequence(RustPunctuator.OR, SPC, EXPRESSION),
        b.sequence(RustPunctuator.CARET, SPC, EXPRESSION),
        b.sequence(RustPunctuator.SHL, SPC, EXPRESSION),
        b.sequence(RustPunctuator.SHR, SPC, EXPRESSION),
        b.sequence(RustPunctuator.EQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.PLUSEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.MINUSEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.STAREQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.SLASHEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.PERCENTEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.ANDEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.OREQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.CARETEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.SHLEQ, SPC, EXPRESSION),
        b.sequence(RustPunctuator.SHREQ, SPC, EXPRESSION)));

    b.rule(EXPRESSION_TERM_EXCEPT_STRUCT).is(
      b.firstOf(
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), b.endOfInput()),
        b.sequence(RustPunctuator.DOTDOTEQ, SCRUTINEE),
        b.sequence(RustPunctuator.DOTDOT, b.nextNot(RustPunctuator.EQ), b.optional(SCRUTINEE)),
        b.sequence(RustPunctuator.DOT, RustKeyword.KW_AWAIT, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.DOT, PATH_EXPR_SEGMENT, SPC, "(", SPC, b.optional(CALL_PARAMS, SPC), ")", SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.DOT, TUPLE_INDEX, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.DOT, IDENTIFIER, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence("(", SPC, b.optional(CALL_PARAMS), SPC, ")", SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.QUESTION, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustKeyword.KW_AS, SPC, TYPE_NO_BOUNDS, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence("[", SPC, SCRUTINEE, SPC, "]", SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.OROR, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.ANDAND, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.NE, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.GT, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.LT, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.GE, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.LE, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.PLUS, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.MINUS, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.STAR, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.SLASH, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.PERCENT, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.AND, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.OR, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.CARET, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.SHL, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.SHR, SPC, SCRUTINEE, SPC, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.EQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.PLUSEQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.MINUSEQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.STAREQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.SLASHEQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.PERCENTEQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.ANDEQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.OREQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.CARETEQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.SHLEQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.SHREQ, SPC, SCRUTINEE, EXPRESSION_TERM_EXCEPT_STRUCT),
        b.sequence(RustPunctuator.DOT, RustKeyword.KW_AWAIT),
        b.sequence(RustPunctuator.DOT, PATH_EXPR_SEGMENT, SPC, "(", SPC, b.optional(CALL_PARAMS, SPC), ")"),
        b.sequence(RustPunctuator.DOT, TUPLE_INDEX),
        b.sequence(RustPunctuator.DOT, IDENTIFIER),
        b.sequence("(", SPC, b.optional(CALL_PARAMS), SPC, ")"),
        RustPunctuator.QUESTION,
        b.sequence(RustKeyword.KW_AS, SPC, TYPE_NO_BOUNDS),
        b.sequence("[", SPC, EXPRESSION, SPC, "]"),
        b.sequence(RustPunctuator.OROR, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.ANDAND, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.EQEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.NE, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.GT, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.LT, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.GE, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.LE, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.PLUS, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.MINUS, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.STAR, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.SLASH, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.PERCENT, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.AND, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.OR, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.CARET, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.SHL, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.SHR, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.EQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.PLUSEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.MINUSEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.STAREQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.SLASHEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.PERCENTEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.ANDEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.OREQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.CARETEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.SHLEQ, SPC, SCRUTINEE),
        b.sequence(RustPunctuator.SHREQ, SPC, SCRUTINEE)));

    b.rule(EXPRESSION_WITHOUT_BLOCK).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(
        b.sequence(BREAK_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(BOX_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(CONTINUE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(EXPRESSION_WITH_BLOCK, b.oneOrMore(SPC, EXPRESSION_TERM)),

        b.sequence(LITERAL_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(CLOSURE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(RANGE_TO_INCLUSIVE_EXPR, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(RANGE_TO_EXPR, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(RANGE_FULL_EXPR, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(BORROW_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(DEREFERENCE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(NEGATION_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(MACRO_INVOCATION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(RETURN_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(STRUCT_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(PATH_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),

        b.sequence(GROUPED_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(ARRAY_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM)),
        b.sequence(TUPLE_EXPRESSION, b.zeroOrMore(SPC, EXPRESSION_TERM))));

    b.rule(EXPRESSION_WITH_BLOCK).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      b.firstOf(
        BLOCK_EXPRESSION,
        MATCH_EXPRESSION,
        ASYNC_BLOCK_EXPRESSION,
        UNSAFE_BLOCK_EXPRESSION,
        LOOP_EXPRESSION,
        IF_EXPRESSION,
        IF_LET_EXPRESSION));

  }

  private static void await(LexerlessGrammarBuilder b) {

    b.rule(AWAIT_EXPRESSION).is(EXPRESSION, RustPunctuator.DOT, "await");
  }

  private static void returnExpr(LexerlessGrammarBuilder b) {
    b.rule(RETURN_EXPRESSION).is("return", SPC, b.optional(EXPRESSION));
  }

  // https://doc.rust-lang.org/reference/expressions/match-expr.html
  private static void match(LexerlessGrammarBuilder b) {
    b.rule(MATCH_EXPRESSION).is(
      RustKeyword.KW_MATCH, SPC,
      b.optional(RustKeyword.KW_MATCH, b.next(IDENTIFIER)),
      SCRUTINEE,
      SPC, "{", SPC,
      b.zeroOrMore(INNER_ATTRIBUTE, SPC),
      b.optional(MATCH_ARMS, SPC),
      "}");

    b.rule(MATCH_ARMS).is(
      b.oneOrMore(MATCH_ARM, SPC, RustPunctuator.FATARROW, SPC,
        b.firstOf(b.sequence(EXPRESSION_WITH_BLOCK, SPC, b.optional(RustPunctuator.COMMA, SPC)),
          b.sequence(EXPRESSION_WITHOUT_BLOCK, SPC, b.optional(RustPunctuator.COMMA, SPC)))));

    b.rule(MATCH_ARM).is(
      b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      PATTERN,
      b.optional(MATCH_ARM_GUARD));

    b.rule(MATCH_ARM_GUARD).is(RustKeyword.KW_IF, SPC, EXPRESSION);

  }

  private static void ifExpr(LexerlessGrammarBuilder b) {
    b.rule(IF_EXPRESSION).is(
      RustKeyword.KW_IF, SPC,
      b.optional(RustKeyword.KW_IF, b.next(IDENTIFIER)),
      SCRUTINEE, b.next(SPC, "{"), SPC, BLOCK_EXPRESSION, SPC,
      b.optional(

        RustKeyword.KW_ELSE, SPC, b.firstOf(BLOCK_EXPRESSION, IF_EXPRESSION, IF_LET_EXPRESSION)));
    b.rule(IF_LET_EXPRESSION).is(
      RustKeyword.KW_IF, SPC, RustKeyword.KW_LET, SPC, PATTERN, SPC, RustPunctuator.EQ, SPC, SCRUTINEE, // except struct or lazy boolean operator expression
      SPC, BLOCK_EXPRESSION, SPC,
      b.optional(RustKeyword.KW_ELSE, SPC, b.firstOf(BLOCK_EXPRESSION, IF_EXPRESSION, IF_LET_EXPRESSION)));
  }

  private static void range(LexerlessGrammarBuilder b) {

    b.rule(RANGE_EXPRESSION).is(b.firstOf(
      b.sequence(RustPunctuator.DOTDOTEQ, b.endOfInput()),
      b.sequence(RustPunctuator.DOTDOT, b.endOfInput()),
      b.sequence(RustPunctuator.DOTDOT, EXPRESSION),
      b.sequence(EXPRESSION, RustPunctuator.DOTDOT, b.endOfInput()),
      b.sequence(EXPRESSION, RustPunctuator.DOTDOTEQ, EXPRESSION),
      b.sequence(EXPRESSION, RustPunctuator.DOTDOT, EXPRESSION)));

    b.rule(RANGE_EXPR).is(EXPRESSION, RustPunctuator.DOTDOT, EXPRESSION);
    b.rule(RANGE_FROM_EXPR).is(EXPRESSION, RustPunctuator.DOTDOT);
    b.rule(RANGE_INCLUSIVE_EXPR).is(EXPRESSION, RustPunctuator.DOTDOTEQ, EXPRESSION);
    b.rule(RANGE_TO_EXPR).is(RustPunctuator.DOTDOT, EXPRESSION);
    b.rule(RANGE_FULL_EXPR).is(RustPunctuator.DOTDOT);
    b.rule(RANGE_TO_INCLUSIVE_EXPR).is(RustPunctuator.DOTDOTEQ, EXPRESSION);
  }

  private static void loops(LexerlessGrammarBuilder b) {
    b.rule(LOOP_EXPRESSION).is(b.optional(LOOP_LABEL, SPC),
      b.firstOf(
        INFINITE_LOOP_EXPRESSION,
        PREDICATE_LOOP_EXPRESSION,
        PREDICATE_PATTERN_LOOP_EXPRESSION,
        ITERATOR_LOOP_EXPRESSION));
    b.rule(INFINITE_LOOP_EXPRESSION).is(
      RustKeyword.KW_LOOP, SPC, BLOCK_EXPRESSION);
    b.rule(PREDICATE_LOOP_EXPRESSION).is(
      RustKeyword.KW_WHILE, SPC, SCRUTINEE, SPC,
      BLOCK_EXPRESSION);
    b.rule(PREDICATE_PATTERN_LOOP_EXPRESSION).is(
      RustKeyword.KW_WHILE, SPC, RustKeyword.KW_LET, SPC, PATTERN, SPC, RustPunctuator.EQ,
      SPC, SCRUTINEE,
      SPC, BLOCK_EXPRESSION);
    b.rule(ITERATOR_LOOP_EXPRESSION).is(
      RustKeyword.KW_FOR, SPC, PATTERN, SPC, RustKeyword.KW_IN, SPC, SCRUTINEE,
      SPC, BLOCK_EXPRESSION);
    b.rule(LOOP_LABEL).is(LIFETIME_OR_LABEL, SPC, RustPunctuator.COLON);
    b.rule(BREAK_EXPRESSION).is(RustKeyword.KW_BREAK, SPC, b.optional(LIFETIME_OR_LABEL, SPC), b.optional(EXPRESSION, SPC));
    b.rule(BOX_EXPRESSION).is(RustKeyword.KW_BOX, SPC, EXPRESSION);
    b.rule(CONTINUE_EXPRESSION).is(
      RustKeyword.KW_CONTINUE, b.optional(SPC, LIFETIME_OR_LABEL));

  }

  private static void call(LexerlessGrammarBuilder b) {

    b.rule(CALL_EXPRESSION).is(EXPRESSION, SPC,
      "(", SPC, b.optional(CALL_PARAMS), SPC, ")");

    b.rule(METHOD_CALL_EXPRESSION).is(EXPRESSION, RustPunctuator.DOT, PATH_EXPR_SEGMENT, SPC, "(", SPC, b.optional(CALL_PARAMS, SPC), ")");

    b.rule(CALL_PARAMS).is(seq(b, EXPRESSION, RustPunctuator.COMMA));
  }

  private static void tuple(LexerlessGrammarBuilder b) {
    b.rule(TUPLE_EXPRESSION).is("(", SPC, b.optional(TUPLE_ELEMENT), SPC, ")");

    b.rule(TUPLE_ELEMENT).is(b.oneOrMore(b.sequence(EXPRESSION, SPC, RustPunctuator.COMMA, SPC)), b.optional(EXPRESSION, SPC));

    b.rule(TUPLE_INDEXING_EXPRESSION).is(EXPRESSION, RustPunctuator.DOT, TUPLE_INDEX);

  }

  private static void array(LexerlessGrammarBuilder b) {
    b.rule(ARRAY_EXPRESSION).is("[", SPC, b.optional(ARRAY_ELEMENTS, SPC), SPC, "]");

    b.rule(ARRAY_ELEMENTS).is(b.firstOf(
      b.sequence(SPC, EXPRESSION, SPC, RustPunctuator.SEMI, SPC, EXPRESSION),
      b.sequence(SPC, EXPRESSION, SPC, b.zeroOrMore(RustPunctuator.COMMA, SPC, EXPRESSION), b.optional(RustPunctuator.COMMA, SPC))));

    b.rule(INDEX_EXPRESSION).is(EXPRESSION, SPC, "[", SPC, EXPRESSION, SPC, "]");

    b.rule(FIELD_EXPRESSION).is(EXPRESSION, RustPunctuator.DOT, IDENTIFIER);
  }

  private static void grouped(LexerlessGrammarBuilder b) {
    b.rule(GROUPED_EXPRESSION).is("(", SPC, EXPRESSION, SPC, ")");
  }

  private static void operator(LexerlessGrammarBuilder b) {
    // https://doc.rust-lang.org/reference/expressions/operator-expr.html
    b.rule(OPERATOR_EXPRESSION).is(b.firstOf(
      BORROW_EXPRESSION,
      LAZY_BOOLEAN_EXPRESSION,
      COMPARISON_EXPRESSION,
      DEREFERENCE_EXPRESSION,
      ERROR_PROPAGATION_EXPRESSION,
      NEGATION_EXPRESSION,
      ARITHMETIC_OR_LOGICAL_EXPRESSION,
      TYPE_CAST_EXPRESSION,
      ASSIGNMENT_EXPRESSION,
      COMPOUND_ASSIGNMENT_EXPRESSION));

    b.rule(BORROW_EXPRESSION).is(b.firstOf(
      b.sequence(b.firstOf(RustPunctuator.ANDAND, RustPunctuator.AND), SPC, RustKeyword.KW_MUT, SPC, EXPRESSION),
      b.sequence(b.firstOf(RustPunctuator.ANDAND, RustPunctuator.AND), SPC, EXPRESSION)));
    b.rule(DEREFERENCE_EXPRESSION).is(RustPunctuator.STAR, EXPRESSION);

    b.rule(ERROR_PROPAGATION_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.QUESTION);

    b.rule(NEGATION_EXPRESSION).is(b.firstOf(
      b.sequence(RustPunctuator.NOT, SPC, EXPRESSION), b.sequence("-", SPC, EXPRESSION)));

    b.rule(NEGATION_EXPRESSION_EXCEPT_STRUCT).is(b.firstOf(
      b.sequence(RustPunctuator.NOT, SPC, SCRUTINEE), b.sequence("-", SPC, SCRUTINEE)));

    b.rule(ARITHMETIC_OR_LOGICAL_EXPRESSION).is(b.firstOf(
      SHL_EXPRESSION,
      ADDITION_EXPRESSION,
      SUBTRACTION_EXPRESSION,
      MULTIPLICATION_EXPRESSION,
      DIVISION_EXPRESSION,
      REMAINDER_EXPRESSION,
      BITAND_EXPRESSION,
      BITOR_EXPRESSION,
      BITXOR_EXPRESSION,
      SHR_EXPRESSION));

    b.rule(ADDITION_EXPRESSION).is(b.nextNot(RustPunctuator.PLUS, SPC, EXPRESSION), EXPRESSION, SPC, RustPunctuator.PLUS, SPC, EXPRESSION);
    b.rule(SUBTRACTION_EXPRESSION).is(EXPRESSION, RustPunctuator.MINUS, SPC, EXPRESSION);
    b.rule(MULTIPLICATION_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.STAR, SPC, EXPRESSION);
    b.rule(DIVISION_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.SLASH, SPC, EXPRESSION);
    b.rule(REMAINDER_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.PERCENT, SPC, EXPRESSION);
    b.rule(BITAND_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.AND, SPC, EXPRESSION);
    b.rule(BITOR_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.OR, SPC, EXPRESSION);
    b.rule(BITXOR_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.CARET, SPC, EXPRESSION);
    b.rule(SHL_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.SHL, SPC, EXPRESSION);
    b.rule(SHR_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.SHR, SPC, EXPRESSION);

    b.rule(COMPARISON_EXPRESSION).is(b.firstOf(
      EQ_EXPRESSION,
      NEQ_EXPRESSION,
      GT_EXPRESSION,
      LT_EXPRESSION,
      GE_EXPRESSION,
      LE_EXPRESSION));

    b.rule(EQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.EQEQ, SPC, EXPRESSION);

    b.rule(NEQ_EXPRESSION).is(
      EXPRESSION, SPC, RustPunctuator.NE, SPC, EXPRESSION);

    b.rule(GT_EXPRESSION).is(
      EXPRESSION, SPC, RustPunctuator.GT, SPC, EXPRESSION);

    b.rule(LT_EXPRESSION).is(
      EXPRESSION, SPC, RustPunctuator.LT, SPC, EXPRESSION);

    b.rule(GE_EXPRESSION).is(
      EXPRESSION, SPC, RustPunctuator.GE, SPC, EXPRESSION);

    b.rule(LE_EXPRESSION).is(
      EXPRESSION, SPC, RustPunctuator.LE, SPC, EXPRESSION);

    b.rule(LAZY_BOOLEAN_EXPRESSION).is(b.firstOf(
      LAZY_OR,
      LAZY_AND));

    b.rule(LAZY_AND).is(EXPRESSION, SPC, RustPunctuator.ANDAND, SPC, EXPRESSION);
    b.rule(LAZY_OR).is(EXPRESSION, SPC, RustPunctuator.OROR, SPC, EXPRESSION);

    b.rule(TYPE_CAST_EXPRESSION).is(EXPRESSION, SPC, RustKeyword.KW_AS, SPC, TYPE_NO_BOUNDS);

    b.rule(ASSIGNMENT_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.EQ, SPC, EXPRESSION);

    b.rule(COMPOUND_ASSIGNMENT_EXPRESSION).is(b.firstOf(
      PLUSEQ_EXPRESSION,
      MINUSEQ_EXPRESSION,
      STAREQ_EXPRESSION,
      SLASHEQ_EXPRESSION,
      PERCENTEQ_EXPRESSION,
      ANDEQ_EXPRESSION,
      OREQ_EXPRESSION),
      CARETEQ_EXPRESSION,
      SHLEQ_EXPRESSION,
      SHREQ_EXPRESSION);

    b.rule(PLUSEQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.PLUSEQ, SPC, EXPRESSION);
    b.rule(MINUSEQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.MINUSEQ, SPC, EXPRESSION);
    b.rule(STAREQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.STAREQ, SPC, EXPRESSION);
    b.rule(SLASHEQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.SLASHEQ, SPC, EXPRESSION);
    b.rule(PERCENTEQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.PERCENTEQ, SPC, EXPRESSION);
    b.rule(ANDEQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.ANDEQ, SPC, EXPRESSION);
    b.rule(OREQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.OREQ, SPC, EXPRESSION);
    b.rule(CARETEQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.CARETEQ, SPC, EXPRESSION);
    b.rule(SHLEQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.SHLEQ, SPC, EXPRESSION);
    b.rule(SHREQ_EXPRESSION).is(EXPRESSION, SPC, RustPunctuator.SHREQ, SPC, EXPRESSION);

  }

  private static void block(LexerlessGrammarBuilder b) {
    b.rule(BLOCK_EXPRESSION).is("{", SPC, b.zeroOrMore(INNER_ATTRIBUTE, SPC),
      SPC, b.optional(STATEMENTS), SPC, "}");

    b.rule(STATEMENTS).is(

      b.firstOf(
        b.sequence(b.oneOrMore(
          b.firstOf(
            b.sequence(RustPunctuator.SEMI, SPC),
            b.sequence(EXPRESSION_WITHOUT_BLOCK, SPC, RustPunctuator.SEMI, SPC),
            b.sequence(EXPRESSION_WITH_BLOCK, b.nextNot(SPC, RustPunctuator.DOT), b.optional(SPC, RustPunctuator.SEMI), SPC),
            b.sequence(ITEM, SPC),
            b.sequence(LET_STATEMENT, SPC),
            b.sequence(MACRO_INVOCATION_SEMI, SPC))),
          SPC, b.firstOf(
            b.sequence(EXPRESSION_WITHOUT_BLOCK, b.nextNot(SPC, RustPunctuator.SEMI)),
            b.sequence(EXPRESSION_WITH_BLOCK, SPC, EXPRESSION_TERM, b.nextNot(SPC, RustPunctuator.SEMI))

          )),

        b.oneOrMore(
          b.firstOf(
            b.sequence(RustPunctuator.SEMI, SPC),
            b.sequence(EXPRESSION_WITHOUT_BLOCK, SPC, RustPunctuator.SEMI, SPC),
            b.sequence(EXPRESSION_WITH_BLOCK, b.nextNot(SPC, RustPunctuator.DOT), b.optional(SPC, RustPunctuator.SEMI), SPC),
            b.sequence(ITEM, SPC),
            b.sequence(LET_STATEMENT, SPC),
            b.sequence(MACRO_INVOCATION_SEMI, SPC))),

        b.sequence(EXPRESSION_WITH_BLOCK, SPC, EXPRESSION_TERM, b.nextNot(SPC, RustPunctuator.SEMI)),
        b.sequence(EXPRESSION_WITHOUT_BLOCK, b.nextNot(SPC, RustPunctuator.SEMI))

      )

    );

    b.rule(ASYNC_BLOCK_EXPRESSION).is(RustKeyword.KW_ASYNC, SPC,
      b.optional(RustKeyword.KW_MOVE, SPC), BLOCK_EXPRESSION);
    b.rule(UNSAFE_BLOCK_EXPRESSION).is(RustKeyword.KW_UNSAFE, SPC, BLOCK_EXPRESSION);

  }

  private static void path(LexerlessGrammarBuilder b) {
    b.rule(PATH_EXPRESSION).is(b.firstOf(PATH_IN_EXPRESSION, QUALIFIED_PATH_IN_EXPRESSION));
  }

  private static void literal(LexerlessGrammarBuilder b) {
    b.rule(LITERAL_EXPRESSION).is(b.firstOf(
      CHAR_LITERAL,
      STRING_LITERAL, RAW_STRING_LITERAL, BYTE_LITERAL, BYTE_STRING_LITERAL, RAW_BYTE_STRING_LITERAL, FLOAT_LITERAL, INTEGER_LITERAL, BOOLEAN_LITERAL));

  }

  /* https://doc.rust-lang.org/reference/expressions/struct-expr.html */
  public static void struct(LexerlessGrammarBuilder b) {
    b.rule(STRUCT_EXPRESSION).is(b.firstOf(
      STRUCT_EXPR_STRUCT,
      STRUCT_EXPR_TUPLE,
      STRUCT_EXPR_UNIT));
    b.rule(STRUCT_EXPR_STRUCT).is(PATH_IN_EXPRESSION, SPC, "{", SPC,
      b.optional(b.firstOf(STRUCT_EXPR_FIELDS, STRUCT_BASE)), SPC,
      "}"

    );
    b.rule(STRUCT_EXPR_FIELDS).is(STRUCT_EXPR_FIELD,
      b.zeroOrMore(b.sequence(RustPunctuator.COMMA, SPC, STRUCT_EXPR_FIELD)),
      b.firstOf(
        b.sequence(SPC, RustPunctuator.COMMA, SPC, STRUCT_BASE),
        b.optional(SPC, RustPunctuator.COMMA, SPC)));

    b.rule(STRUCT_EXPR_FIELD).is(b.firstOf(

      b.sequence(b.optional(OUTER_ATTRIBUTE, SPC), b.firstOf(IDENTIFIER, TUPLE_INDEX), SPC, RustPunctuator.COLON, SPC, EXPRESSION),
      b.sequence(b.optional(OUTER_ATTRIBUTE, SPC), IDENTIFIER)

    ));

    b.rule(STRUCT_BASE).is(RustPunctuator.DOTDOT, SPC, EXPRESSION);
    b.rule(STRUCT_EXPR_TUPLE).is(
      PATH_IN_EXPRESSION, "(", SPC,
      b.optional(
        b.sequence(
          EXPRESSION, SPC,
          b.zeroOrMore(b.sequence(RustPunctuator.COMMA, SPC, EXPRESSION, SPC)),
          b.optional(RustPunctuator.COMMA, SPC))),
      ")");
    b.rule(STRUCT_EXPR_UNIT).is(PATH_IN_EXPRESSION);
  }

  /* https://doc.rust-lang.org/reference/attributes.html */
  public static void attributes(LexerlessGrammarBuilder b) {
    b.rule(INNER_ATTRIBUTE).is("#![", SPC, ATTR, SPC, "]");
    b.rule(OUTER_ATTRIBUTE).is("#[", SPC, ATTR, SPC, "]");
    b.rule(ATTR).is(SIMPLE_PATH, SPC, b.optional(ATTR_INPUT, SPC));
    b.rule(ATTR_INPUT).is(b.firstOf(DELIM_TOKEN_TREE,
      b.sequence(RustPunctuator.EQ, SPC, EXPRESSION)));
    b.rule(META_ITEM).is(b.firstOf(
      b.sequence(SIMPLE_PATH, SPC, RustPunctuator.EQ, SPC, EXPRESSION),
      b.sequence(SIMPLE_PATH, SPC, "(", SPC, META_SEQ, SPC, ")"),
      SIMPLE_PATH));
    b.rule(META_SEQ).is(seq(b, META_ITEM_INNER, RustPunctuator.COMMA));
    b.rule(META_ITEM_INNER).is(b.firstOf(META_ITEM, EXPRESSION));
    b.rule(META_WORD).is(IDENTIFIER);
    b.rule(META_NAME_VALUE_STR).is(IDENTIFIER, SPC, RustPunctuator.EQ, SPC, b.firstOf(STRING_LITERAL, RAW_STRING_LITERAL));
    b.rule(META_LIST_PATHS).is(IDENTIFIER, "(", b.optional(seq(b, SIMPLE_PATH, RustPunctuator.COMMA)), ")");
    b.rule(META_LIST_IDENTS).is(IDENTIFIER, "(", b.optional(seq(b, IDENTIFIER, RustPunctuator.COMMA)), ")");
    b.rule(META_LIST_NAME_VALUE_STR).is(IDENTIFIER, "(", b.optional(seq(b, META_NAME_VALUE_STR, RustPunctuator.COMMA)), ")");

  }

  /* https://doc.rust-lang.org/reference/expressions/closure-expr.html */
  public static void closure(LexerlessGrammarBuilder b) {
    b.rule(CLOSURE_EXPRESSION).is(
      b.optional(RustKeyword.KW_MOVE, SPC),
      b.firstOf(b.sequence(RustPunctuator.OROR, SPC),
        b.sequence(RustPunctuator.OR, SPC, b.optional(CLOSURE_PARAMETERS, SPC), RustPunctuator.OR, SPC)),
      b.firstOf(EXPRESSION, b.sequence(SPC, RustPunctuator.RARROW, SPC, TYPE_NO_BOUNDS, SPC, BLOCK_EXPRESSION)));
    b.rule(CLOSURE_PARAMETERS).is(seq(b, CLOSURE_PARAM, RustPunctuator.COMMA));
    b.rule(CLOSURE_PARAM).is(b.zeroOrMore(OUTER_ATTRIBUTE, SPC),
      PATTERN_NO_TOP_ALT, SPC,
      b.optional(RustPunctuator.COLON, SPC, TYPE));
  }

  /* https://doc.rust-lang.org/reference/types.html#type-expressions */
  public static void type(LexerlessGrammarBuilder b) {
    b.rule(TYPE).is(b.firstOf(
      MACRO_INVOCATION,
      IMPL_TRAIT_TYPE,
      BARE_FUNCTION_TYPE,
      TRAIT_OBJECT_TYPE,
      PARENTHESIZED_TYPE,
      IMPL_TRAIT_TYPE_ONE_BOUND,
      TRAIT_OBJECT_TYPE_ONE_BOUND,
      TYPE_PATH,
      TUPLE_TYPE,
      NEVER_TYPE,
      RAW_POINTER_TYPE,
      REFERENCE_TYPE,
      ARRAY_TYPE,
      SLICE_TYPE,
      INFERRED_TYPE,
      QUALIFIED_PATH_IN_TYPE

    ));
    b.rule(TYPE_NO_BOUNDS).is(b.firstOf(
      MACRO_INVOCATION,
      BARE_FUNCTION_TYPE,
      PARENTHESIZED_TYPE,
      IMPL_TRAIT_TYPE_ONE_BOUND,
      TRAIT_OBJECT_TYPE_ONE_BOUND,
      TYPE_PATH,
      TUPLE_TYPE,
      NEVER_TYPE,
      RAW_POINTER_TYPE,
      REFERENCE_TYPE,
      ARRAY_TYPE,
      SLICE_TYPE,
      INFERRED_TYPE,
      QUALIFIED_PATH_IN_TYPE

    ));
    b.rule(PARENTHESIZED_TYPE).is("(", TYPE, ")");
    b.rule(TRAIT_OBJECT_TYPE).is(b.optional(RustKeyword.KW_DYN, SPC), TYPE_PARAM_BOUNDS);
    b.rule(TRAIT_OBJECT_TYPE_ONE_BOUND).is(b.optional(RustKeyword.KW_DYN, SPC), SPC, TRAIT_BOUND);
    b.rule(RAW_POINTER_TYPE).is(RustPunctuator.STAR, SPC, b.firstOf(RustKeyword.KW_MUT, RustKeyword.KW_CONST), SPC, TYPE_NO_BOUNDS);
    b.rule(INFERRED_TYPE).is(RustPunctuator.UNDERSCORE);
    b.rule(SLICE_TYPE).is("[", SPC, TYPE, SPC, "]");
    b.rule(ARRAY_TYPE).is("[", SPC, TYPE, SPC, RustPunctuator.SEMI, SPC, EXPRESSION, SPC, "]");
    b.rule(IMPL_TRAIT_TYPE).is(RustKeyword.KW_IMPL, SPC, TYPE_PARAM_BOUNDS);
    b.rule(IMPL_TRAIT_TYPE_ONE_BOUND).is(RustKeyword.KW_IMPL, SPC, TRAIT_BOUND);
    b.rule(NEVER_TYPE).is(RustPunctuator.NOT);

  }

  /* https://doc.rust-lang.org/reference/trait-bounds.html */
  public static void trait(LexerlessGrammarBuilder b) {
    b.rule(TYPE_PARAM_BOUNDS).is(SPC, TYPE_PARAM_BOUND, SPC,
      b.zeroOrMore(b.sequence(RustPunctuator.PLUS, SPC, TYPE_PARAM_BOUND, SPC)),
      b.optional(SPC, RustPunctuator.PLUS, SPC));
    b.rule(TYPE_PARAM_BOUND).is(b.firstOf(LIFETIME, TRAIT_BOUND));
    b.rule(TRAIT_BOUND).is(b.firstOf(
      b.sequence(b.optional(RustPunctuator.QUESTION, SPC), b.optional(FOR_LIFETIMES, SPC), TYPE_PATH, SPC),
      b.sequence("(", SPC, b.optional(RustPunctuator.QUESTION, SPC),
        b.optional(FOR_LIFETIMES, SPC), TYPE_PATH, SPC, ")")));
    b.rule(LIFETIME_BOUNDS).is(
      b.zeroOrMore(LIFETIME, SPC, RustPunctuator.PLUS, SPC),
      b.optional(LIFETIME, SPC));
    b.rule(LIFETIME).is(b.firstOf("'static", "'_", LIFETIME_OR_LABEL));
  }

  public static void tupletype(LexerlessGrammarBuilder b) {

    b.rule(TUPLE_TYPE).is(b.firstOf(
      b.sequence("(", SPC, ")"),
      b.sequence("(", SPC, b.oneOrMore(b.sequence(TYPE, SPC, RustPunctuator.COMMA, SPC)), b.optional(TYPE), SPC, ")")));

  }

  /* https://doc.rust-lang.org/reference/types/pointer.html */
  public static void pointer(LexerlessGrammarBuilder b) {
    b.rule(REFERENCE_TYPE).is(RustPunctuator.AND, b.optional(LIFETIME, SPC),
      b.optional(RustKeyword.KW_MUT, SPC), TYPE_NO_BOUNDS);
  }

  /* https://doc.rust-lang.org/reference/paths.html */
  public static void lexicalpath(LexerlessGrammarBuilder b) {
    b.rule(SIMPLE_PATH).is(
      b.optional(RustPunctuator.PATHSEP),
      SIMPLE_PATH_SEGMENT,
      b.zeroOrMore(b.sequence(RustPunctuator.PATHSEP, SIMPLE_PATH_SEGMENT)));
    b.rule(SIMPLE_PATH_SEGMENT).is(b.firstOf(
      b.sequence(RustKeyword.KW_SUPER, b.nextNot(IDENTIFIER)),
      RustKeyword.KW_SELF_VALUE, b.regexp("^crate$"), b.regexp(DOLLAR_CRATE_REGEX), IDENTIFIER));

    b.rule(PATH_IN_EXPRESSION).is(
      b.optional(RustPunctuator.PATHSEP),
      PATH_EXPR_SEGMENT,
      b.zeroOrMore(b.sequence(RustPunctuator.PATHSEP, PATH_EXPR_SEGMENT)));

    b.rule(PATH_EXPR_SEGMENT).is(
      PATH_IDENT_SEGMENT, b.optional(b.sequence(RustPunctuator.PATHSEP, GENERIC_ARGS)));

    b.rule(PATH_IDENT_SEGMENT).is(b.firstOf(
      b.sequence(RustKeyword.KW_SUPER, b.nextNot(IDENTIFIER)),
      b.regexp("^[sS]elf$"),
      b.sequence(RustKeyword.KW_CRATE, b.nextNot(IDENTIFIER)),
      b.regexp(DOLLAR_CRATE_REGEX),
      IDENTIFIER));

    b.rule(GENERIC_ARGS).is(b.firstOf(
      b.sequence(RustPunctuator.LT, RustPunctuator.GT),
      b.sequence(RustPunctuator.LT, SPC,
        seq(b, GENERIC_ARG, RustPunctuator.COMMA),
        SPC, RustPunctuator.GT

      )));
    b.rule(GENERIC_ARG).is(b.firstOf(
      GENERIC_ARGS_BINDING,
      LIFETIME,
      TYPE,
      GENERIC_ARGS_CONST

    ));

    b.rule(GENERIC_ARGS_CONST).is(b.firstOf(
      BLOCK_EXPRESSION,
      LITERAL_EXPRESSION,
      b.sequence(RustPunctuator.MINUS, SPC, LITERAL_EXPRESSION),
      SIMPLE_PATH_SEGMENT));

    b.rule(GENERIC_ARGS_BINDING).is(
      IDENTIFIER, SPC, RustPunctuator.EQ, SPC, TYPE);

    b.rule(QUALIFIED_PATH_IN_EXPRESSION).is(
      QUALIFIED_PATH_TYPE, b.oneOrMore(b.sequence(RustPunctuator.PATHSEP, PATH_EXPR_SEGMENT)));

    b.rule(QUALIFIED_PATH_TYPE).is(
      RustPunctuator.LT, SPC, TYPE, b.optional(SPC, RustKeyword.KW_AS, SPC, TYPE_PATH), SPC, RustPunctuator.GT);

    b.rule(QUALIFIED_PATH_IN_TYPE).is(QUALIFIED_PATH_TYPE, b.oneOrMore(
      b.sequence(RustPunctuator.PATHSEP, TYPE_PATH_SEGMENT)

    ));

    b.rule(TYPE_PATH_SEGMENT).is(
      PATH_IDENT_SEGMENT,
      b.optional(b.firstOf(
        GENERIC_ARGS,
        TYPE_PATH_FN,
        b.sequence(RustPunctuator.PATHSEP, GENERIC_ARGS),
        b.sequence(RustPunctuator.PATHSEP, TYPE_PATH_FN))));
    b.rule(TYPE_PATH_FN).is(
      "(",
      b.optional(TYPE_PATH_FN_INPUTS),
      ")",
      b.optional(b.sequence(SPC, RustPunctuator.RARROW, SPC, TYPE)));
    b.rule(TYPE_PATH_FN_INPUTS).is(
      TYPE,
      b.zeroOrMore(b.sequence(RustPunctuator.COMMA, SPC, TYPE)),
      b.optional(RustPunctuator.COMMA, SPC));
    b.rule(TYPE_PATH).is(
      // ::? TypePathSegment (:: TypePathSegment)*
      // b.optional(RustPunctuator.PATHSEP), TYPE_PATH_SEGMENT, b.zeroOrMore(b.sequence(RustPunctuator.PATHSEP, TYPE_PATH_SEGMENT))
      b.optional(RustPunctuator.PATHSEP),
      TYPE_PATH_SEGMENT,
      b.zeroOrMore(RustPunctuator.PATHSEP, TYPE_PATH_SEGMENT)

    );

  }

  public static void lexicaltoken(LexerlessGrammarBuilder b) {

    b.rule(TOKEN).is(b.firstOf(LITERALS, IDENTIFIER_OR_KEYWORD,
      LIFETIMES, PUNCTUATION, DELIMITERS));

    b.rule(LIFETIME_OR_LABEL).is(
      "'", NON_KEYWORD_IDENTIFIER);

    identifiers(b);

    b.rule(LIFETIME_TOKEN).is(b.firstOf(
      b.sequence("'", IDENTIFIER_OR_KEYWORD),
      b.sequence("'", RustPunctuator.UNDERSCORE)));
    b.rule(LIFETIMES).is(b.firstOf(LIFETIME_TOKEN, LIFETIME_OR_LABEL)); // not explicit in reference
    // LITERALS are not explicitly listed like below
    b.rule(LITERALS).is(b.firstOf(CHAR_LITERAL, STRING_LITERAL,
      RAW_STRING_LITERAL, BYTE_LITERAL, BYTE_STRING_LITERAL, RAW_BYTE_STRING_LITERAL, FLOAT_LITERAL,
      INTEGER_LITERAL, BOOLEAN_LITERAL, LIFETIMES));

    b.rule(DELIMITERS).is(b.firstOf("{", "}", "[", "]", "(", ")"));

    characters(b);
    bytes(b);
    integerliteral(b);
    floatliteral(b);
    b.rule(BOOLEAN_LITERAL).is(b.token(RustTokenType.BOOLEAN_LITERAL, b.sequence(b.firstOf("true", "false"), b.nextNot(IDENTIFIER))));

  }

  private static void floatliteral(LexerlessGrammarBuilder b) {

    b.rule(FLOAT_LITERAL).is(b.token(RustTokenType.FLOAT_LITERAL,
      b.firstOf(
        b.sequence(DEC_LITERAL, b.optional(b.sequence(".", DEC_LITERAL)), b.optional(FLOAT_EXPONENT), FLOAT_SUFFIX),
        b.sequence(DEC_LITERAL, RustPunctuator.DOT, DEC_LITERAL, b.optional(FLOAT_EXPONENT)),
        b.sequence(DEC_LITERAL, FLOAT_EXPONENT),
        b.sequence(DEC_LITERAL, RustPunctuator.DOT, b.nextNot(b.firstOf(IDENTIFIER, RustPunctuator.DOT, RustPunctuator.UNDERSCORE))))));
    b.rule(FLOAT_EXPONENT).is(b.regexp("[eE]+[+-]?[0-9][0-9_]*"));

    b.rule(FLOAT_SUFFIX).is(b.firstOf("f64", "f32"));
  }

  private static void bytes(LexerlessGrammarBuilder b) {

    b.rule(BYTE_LITERAL).is(b.token(RustTokenType.BYTE_LITERAL,
      b.firstOf(
        "b'\\''",
        b.sequence("b'", b.regexp("[\\x00-\\x08\\x11-\\x12\\x14-\\x26\\x28-x5b\\x5d-\\x7f]"), "'"),
        b.sequence("b'", BYTE_ESCAPE, "'"))));

    b.rule(ASCII_FOR_CHAR).is(b.regexp("[^\\'\\n\\r\\t\\\\].*"));
    b.rule(ASCII_FOR_STRING).is(b.regexp("[\\x00-\\x21\\x23-\\x5b\\x5d-\\x7f]"));// except ", \ and IsolatedCR (lookahead? (?![m-o])[a-z])

    b.rule(BYTE_STRING_LITERAL).is(b.token(RustTokenType.BYTE_STRING_LITERAL,
      b.sequence(
        "b\"",
        b.zeroOrMore(
          b.firstOf(
            "\\\"", ASCII_FOR_STRING, BYTE_ESCAPE, STRING_CONTINUE)),
        "\"")));

    b.rule(BYTE_ESCAPE).is(b.firstOf(b.sequence("\\x", HEX_DIGIT, HEX_DIGIT), "\\n", "\\r", "\\t", "\\\\", "\\0"));

    b.rule(RAW_BYTE_STRING_LITERAL).is(b.token(RustTokenType.RAW_BYTE_STRING_LITERAL,
      b.sequence("br", RAW_BYTE_STRING_CONTENT)));

    b.rule(RAW_BYTE_STRING_CONTENT).is(
      b.firstOf(
        b.regexp("(?=\"+)([\\s\\S]+?\"+)"),
        b.regexp("(#\"[\\s\\S]+?\\\"#)"),
        b.sequence("#", RAW_BYTE_STRING_CONTENT, "#")));

    b.rule(ASCII).is(b.regexp("[\\x00-\\x7F]"));
  }

  private static void identifiers(LexerlessGrammarBuilder b) {
    final String XID_START = "[_\\p{L}\\p{Nl}]";
    final String XID_CONTINUE = "[\\pL\\p{Nl}\\p{Mn}\\p{Mc}\\p{Nd}\\p{Pc}]";

    b.rule(IDENTIFIER_OR_KEYWORD).is(b.firstOf(
      b.sequence(b.regexp(XID_START), b.zeroOrMore(b.regexp(XID_CONTINUE))),
      b.sequence("_", b.oneOrMore(XID_CONTINUE))));

    b.rule(RAW_IDENTIFIER).is("r#",
      b.nextNot("crate"),
      b.nextNot("self"),
      b.nextNot("super"),
      b.nextNot("Self"),
      IDENTIFIER_OR_KEYWORD);

    b.rule(NON_KEYWORD_IDENTIFIER).is(b.firstOf(
      b.regexp(XID_START + XID_CONTINUE + "*" + exceptKeywords()),
      b.regexp("^_" + XID_CONTINUE + "+" + exceptKeywords())));

    b.rule(IDENTIFIER).is(b.token(RustTokenType.IDENTIFIER,
      b.firstOf(RAW_IDENTIFIER, NON_KEYWORD_IDENTIFIER))).skip();

  }

  private static String exceptKeywords() {
    StringBuilder sb = new StringBuilder("(?<!(");
    String[] values = RustKeyword.keywordValues();
    sb.append("^").append(values[0]).append("$");
    for (String kw : values) {
      sb.append("|^");
      sb.append(kw).append("$");
    }
    sb.append("))");

    return sb.toString();

  }

  private static void characters(LexerlessGrammarBuilder b) {

    b.rule(QUOTE_ESCAPE).is(b.firstOf("\\'", "\\\""));
    b.rule(ASCII_ESCAPE).is(b.firstOf(b.sequence("\\x", OCT_DIGIT, HEX_DIGIT),
      "\\n", "\\r", "\\t", "\\\\", "\\0"));
    b.rule(UNICODE_ESCAPE).is("\\u{", b.oneOrMore(b.sequence(HEX_DIGIT, b.zeroOrMore(RustPunctuator.UNDERSCORE))), "}");
    b.rule(STRING_CONTINUE).is("\\\n");

    b.rule(RAW_STRING_LITERAL).is(b.token(RustTokenType.RAW_STRING_LITERAL,

      b.sequence("r", RAW_STRING_CONTENT)));

    b.rule(RAW_STRING_CONTENT).is(
      b.firstOf(
        b.regexp("(?=\"+)([\\s\\S]+?\"+)"),
        b.regexp("(#\"[\\s\\S]+?\\\"#)"),
        b.sequence("#", RAW_STRING_CONTENT, "#")

      ));

  }

  /* https://doc.rust-lang.org/reference/tokens.html#integer-literals */
  private static void integerliteral(LexerlessGrammarBuilder b) {
    b.rule(INTEGER_LITERAL).is(b.token(RustTokenType.INTEGER_LITERAL,
      b.sequence(
        b.firstOf(HEX_LITERAL, OCT_LITERAL, BIN_LITERAL, DEC_LITERAL),
        b.optional(INTEGER_SUFFIX), SPC)));
    b.rule(DEC_LITERAL).is(DEC_DIGIT, b.zeroOrMore(b.firstOf(DEC_DIGIT, RustPunctuator.UNDERSCORE)));
    b.rule(TUPLE_INDEX).is(INTEGER_LITERAL);

    b.rule(BIN_LITERAL).is("0b", b.zeroOrMore(b.firstOf(BIN_DIGIT, RustPunctuator.UNDERSCORE)));
    b.rule(OCT_LITERAL).is("0o", b.zeroOrMore(b.firstOf(OCT_DIGIT, RustPunctuator.UNDERSCORE)));
    b.rule(HEX_LITERAL).is("0x", b.zeroOrMore(b.firstOf(HEX_DIGIT, RustPunctuator.UNDERSCORE)));

    b.rule(BIN_DIGIT).is(b.regexp("[0-1]"));
    b.rule(OCT_DIGIT).is(b.regexp("[0-7]"));
    b.rule(DEC_DIGIT).is(b.regexp("[0-9]"));
    b.rule(NON_ZERO_DEC_DIGIT).is(b.regexp("[1-9]"));
    b.rule(HEX_DIGIT).is(b.regexp("[0-9a-fA-F]"));
    b.rule(INTEGER_SUFFIX).is(b.firstOf("u8", "u16", "u32", "u64", "u128", "usize", "i8", "i16", "i32", "i64", "i128", "isize"));

  }

}
