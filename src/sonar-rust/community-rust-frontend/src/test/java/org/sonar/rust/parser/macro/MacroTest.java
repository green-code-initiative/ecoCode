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
package org.sonar.rust.parser.macro;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class MacroTest {


    @Test
    public void testTokenExceptDelimiters() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TOKEN_EXCEPT_DELIMITERS))
                .matches("abc")
                .matches("42")
                .matches(";")
                .matches("\"hello\"")
                .notMatches("\"hello\")")
                .notMatches("(")
                .notMatches("{")
                .notMatches("[")
        ;
    }

    @Test
    public void testDelimTokenTree() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.DELIM_TOKEN_TREE))
                .matches("(abc)")
                .matches("(\"hello\")")
                .matches("()")
                .matches("(\"{}, {}\", word, j)")
                .notMatches("")
        ;
    }


    @Test
    public void testTokenTree() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TOKEN_TREE))
                .matches("abc")
                .matches("\"hello\"")
                .matches("(abc)")
                .matches("[abc]")
                .matches("{abc}")
                .matches("(a(bc))")
                .matches("{a(bc)[(de)(fgh)]}")
                .matches("()")
                .notMatches("")
        ;
    }


    @Test
    public void testMacroInvocationSemi() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_INVOCATION_SEMI))
                .matches("j!(AS);")
                .matches("println!(\"hello\");")
                .matches("println!(\"hello,world!\");")
                .matches("println!(\"hello,{}\",k);")
                .matches("println!(\"hello,{}\", j);")
                .matches("println!(\"{}, {}\", word, j);")
                .notMatches("")
                .matches("assert_eq!(state.borrow::<MyStruct>().value, 110);")
        ;
    }

    @Test
    public void testMacroInvocation() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_INVOCATION))
                .matches("std::io::Write!()")
                .matches("panic!()")
                .matches("println!(\"{}, {}\", word, j)")
                .notMatches("")
                .matches("Token![#]")
        ;
    }


    @Test
    public void testMacroFragSpec() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_FRAG_SPEC))
                .matches("block")
                .matches("expr")
                .matches("ident")
                .matches("item")
                .matches("lifetime")
                .matches("literal")
                .matches("meta")
                .matches("path")
                .matches("pat")
                .matches("pat_param")
                .matches("stmt")
                .matches("tt")
                .matches("ty")
                .matches("vis")

        ;
    }

    @Test
    public void testRepOp() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_REP_OP))
                .matches("+")
                .matches("*")
                .matches("?")
                .notMatches("a+")
                .notMatches("+a")
                .notMatches("+a+")


        ;
    }

    @Test
    public void testMacroMatch() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_MATCH))
                .matches("token")
                .matches("$(token token)*")
                .matches("$i:ident")
                .matches("(token)")
                .matches("[token]")
                .matches("{token}")
                .matches("($(token)*)")
                .matches("[$i:ident]")
                .matches("[($i:ident)]")
                .matches("($($i:ident)*)")
                .matches("$($key:expr => $value:expr)+")
                .matches("$($key:expr => $value:expr),+")
        ;
    }

    @Test
    public void testMacroMatcher() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_MATCHER))
                .matches("(token)")
                .matches("($(token token)*)")
                .matches("[$(token)*]")
                .matches("{$i:ident}")
                .matches("((token))")
                .matches("[[token]]")
                .matches("{{token}}")
                .matches("(($(token)*))")
                .matches("[[$i:ident]]")
                .matches("{[($i:ident)]}")
                .matches("(($($i:ident)*))")
                .matches("($l:tt)")
                .matches("{ $($key:expr => $value:expr)+ }")
                .matches("{ $($key:expr => $value:expr),+ }")
                .matches("($($f:ident $(< $($generic:ty),* > )? )::+($($arg:ty),*): Send & Sync)")
        ;
    }


    @Test
    public void testMacroRules() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_RULES))
                .matches("($l:tt) => { bar!($l); }")
                .matches("($($name:ident($ty:ty, $to:ident, $lt:lifetime);)*) => {\n" +
                        "        $(fn $name(self, v: $ty) -> JsResult<$lt> {\n" +
                        "            self.$to(v as _)\n" +
                        "        })*\n" +
                        "    };")
                .matches("{ $($key:expr => $value:expr),+ } => {\n" +
                        "      {\n" +
                        "        let mut m = ::std::collections::HashMap::new();\n" +
                        "        $(\n" +
                        "          m.insert($key, $value);\n" +
                        "        )+\n" +
                        "        m\n" +
                        "      }\n" +
                        "    };")

        ;
    }

    @Test
    public void testMacroRulesDef() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_RULES_DEF))
                .matches("{($l:tt) => { bar!($l); }}")
                .matches("{($($name:ident($ty:ty, $to:ident, $lt:lifetime);)*) => {\n" +
                        "        $(fn $name(self, v: $ty) -> JsResult<$lt> {\n" +
                        "            self.$to(v as _)\n" +
                        "        })*\n" +
                        "    };\n" +
                        "}")
                .matches("(\n" +
                        "    { $($key:expr => $value:expr),+ } => {\n" +
                        "      {\n" +
                        "        let mut m = ::std::collections::HashMap::new();\n" +
                        "        $(\n" +
                        "          m.insert($key, $value);\n" +
                        "        )+\n" +
                        "        m\n" +
                        "      }\n" +
                        "    };\n" +
                        "  );")


        ;
    }

    @Test
    public void testMacroRulesDefinition() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_RULES_DEFINITION))
                .matches("macro_rules! foo {\n" +
                        "    ($l:tt) => { bar!($l); }\n" +
                        "}")
                .matches("macro_rules! pat {\n" +
                        "    ($i:ident) => (Some($i))\n" +
                        "}")
                .matches("macro_rules! Tuple {\n" +
                        "    { $A:ty, $B:ty } => { ($A, $B) };\n" +
                        "}")
                .matches("macro_rules! const_maker {\n" +
                        "    ($t:ty, $v:tt) => { const CONST: $t = $v; };\n" +
                        "}")
                .matches("macro_rules! example {\n" +
                        "    () => { println!(\"Macro call in a macro!\") };\n" +
                        "}")
                .matches("macro_rules! forward_to {\n" +
                        "    ($($name:ident($ty:ty, $to:ident, $lt:lifetime);)*) => {\n" +
                        "        $(fn $name(self, v: $ty) -> JsResult<$lt> {\n" +
                        "            self.$to(v as _)\n" +
                        "        })*\n" +
                        "    };\n" +
                        "}")
                .matches("macro_rules! map (\n" +
                        "    { $($key:expr => $value:expr),+ } => {\n" +
                        "      {\n" +
                        "        let mut m = ::std::collections::HashMap::new();\n" +
                        "        $(\n" +
                        "          m.insert($key, $value);\n" +
                        "        )+\n" +
                        "        m\n" +
                        "      }\n" +
                        "    };\n" +
                        "  );")
                .matches("macro_rules! itest(\n" +
                        "  ($name:ident {$( $key:ident: $value:expr,)*})  => {\n" +
                        "    #[test]\n" +
                        "    fn $name() {\n" +
                        "      (util::CheckOutputIntegrationTest {\n" +
                        "        $(\n" +
                        "          $key: $value,\n" +
                        "         )*\n" +
                        "        .. Default::default()\n" +
                        "      }).run()\n" +
                        "    }\n" +
                        "  }\n" +
                        ");")
                .matches("macro_rules! a {\n" +
                        "    () => { a!(1); };\n" +
                        "    (1) => { a!(2); };\n" +
                        "    (2) => { a!(3); };\n" +
                        "    (3) => { a!(4); };\n" +
                        "    (4) => { };\n" +
                        "}")


        ;
    }


}
