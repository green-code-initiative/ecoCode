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
package org.sonar.rust.parser.lexer;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class PathTest {
    @Test
    public void testSimplePathSegment() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.SIMPLE_PATH_SEGMENT))
                .matches("super")
                .matches("self")
                .matches("crate")
                .matches("$crate")
                .matches("abc")
                .matches("r#a")
                .matches("U213")
                .matches("crate_type")
        ;
    }

    @Test
    public void testSimplePath() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.SIMPLE_PATH))
                .matches("std::io::Write")
                .matches("std::io::super")
                .matches("Write")
                .matches("crate_type")
        ;
    }

    @Test
    public void testPathExprSegment() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.PATH_EXPR_SEGMENT))
                .matches("f")
                .matches("f::<>")
                .matches("f::<T>")


        ;
    }

    @Test
    public void testPathInExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.PATH_IN_EXPRESSION))
                .matches("Vec::<u8>::with_capacity")
                .matches("collect::<Vec<_>>")
                .matches("S")
                .matches("S::T")
                .matches("result::Result")
                .matches("Identifier::Numeric")
                .notMatches("match")
                .matches("Token::BackQuote")


        ;
    }


    @Test
    public void testGenericArgsBinding() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.GENERIC_ARGS_BINDING))
                .matches("V=f64")
                .matches("U=Circle")
        ;
    }


    @Test
    public void testGenericArg() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.GENERIC_ARG))
                .matches("T")
                .matches("i32")
                .matches("Circle")
                .matches("U=i32")
                .matches("V=f64")
                .matches("Pin<T>")
                .matches("Pin<Box<T>>")
                .matches("Pin<Box<(dyn Future<T>)>>")
                .matches("Pin<Box<(dyn Future<A = B>)>>")
                .matches("Pin<Box<(dyn Future<A = Result<T>>)>>")
                .matches("Pin<Box<(dyn Future<A = Result<T,(U,V)>>)>>")


        ;
    }


    @Test
    public void testGenericArgs() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.GENERIC_ARGS))
                .matches("<>")
                .matches("<T>")
                .matches("<T,>")
                .matches("<i32>")
                .matches("<u8>")
                .matches("<Circle>")
                .matches("<Circle, f64, u8>")
                .matches("<U=i32>")
                .matches("<V=f64>")
                .matches("<T,U,V=f64>")
                .matches("<Pin<T>>")
                .matches("<Pin<Box<T>>>")
                .matches("<Pin<Box<(dyn Future<T>)>>>")
                .matches("<Pin<Box<(dyn Future<A = B>)>>>")
                .matches("<Pin<Box<(dyn Future<A = Result<T>>)>>>")
                .matches("<Pin<Box<(dyn Future<A = Result<T,(U,V)>>)>>>")

        ;
    }

    @Test
    public void testQualifiedPathType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.QUALIFIED_PATH_TYPE))
                .matches("<T1>")
                .matches("<T1 as T>")
                .matches("<X as Default>")


        ;
    }

    @Test
    public void testQualifiedPathInExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.QUALIFIED_PATH_IN_EXPRESSION))
                .matches("<S as T1>::f")
                .matches("<X as Default>::default")


        ;
    }

    @Test
    public void testQualifiedPathInType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.QUALIFIED_PATH_IN_TYPE))
                .matches("<S as T1>::f")
                .matches("<X as Default>::default()")
                .matches("<[T] as SpanlessEq>::eq(self, other)")
        ;
    }


    @Test
    public void testPathIdentSegment() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.PATH_IDENT_SEGMENT))
                .matches("super")
                .matches("self")
                .matches("crate")
                .matches("$crate")
                .matches("abc")
                .matches("r#a")
                .matches("U213")
        ;


    }

    @Test
    public void testTypePathFnInputs() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_PATH_FN_INPUTS))
                .matches("isize")
                .matches("&mut OpState, u32, &mut [ZeroCopyBuf]")

        ;
    }

    @Test
    public void testTypePathFn() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_PATH_FN))
                .matches("(isize) -> isize")
                .matches("(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError>")

        ;
    }

    @Test
    public void testTypePathSegment() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_PATH_SEGMENT))
                .matches("super")
                .matches("abc")
                .matches("r#a")
                .matches("U213")
                .matches("abc::(isize) -> isize")
                .matches("abc::<>")
                .matches("abc::(isize) -> isize")
                .notMatches("abc::abc for")
                .matches("Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError>")
        ;
    }

    @Test
    public void testTypePath() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_PATH))
                .matches("abc::(isize) -> isize")
                .notMatches("abc::abc for")
                .matches("T")
                .matches("abc::def")
                .matches("Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError>")

        ;
    }
}
