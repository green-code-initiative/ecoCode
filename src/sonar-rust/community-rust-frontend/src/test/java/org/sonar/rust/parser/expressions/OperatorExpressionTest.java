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
package org.sonar.rust.parser.expressions;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class OperatorExpressionTest {


    @Test
    public void testBoxExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.BOX_EXPRESSION))
                .matches("box 42")
                .matches("box foo")
                .matches("box a.method()")
                .matches("box Ok(Poll::Pending)")
        ;
    }


    @Test
    public void testBorrowExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.BORROW_EXPRESSION))
                .matches("&7")
                .matches("& 7")
                .matches("& Value")
                .matches("&mut array")
                .matches("&& 10")
                .matches("& & 10")
                .matches("&&&& mut 10")
                .matches("&& && mut 10")
                .matches("& & & & mut 10")
                .matches("&[b' ', b' ', b' ']")



        ;
    }

    @Test
    public void testDereferenceExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.DEREFERENCE_EXPRESSION))
                .matches("*x")
                .notMatches("**")
                .notMatches("== b")
                .matches("*position")
        ;
    }

    @Test
    public void testErrPropagationExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.ERROR_PROPAGATION_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("foo?")
                .matches("None?")
                .matches("Some(42)?")
                .notMatches("== b")
                .matches("self.get_cache_filename(url)?")
                .matches("match path.parent() {\n" +
                        "             Some(ref parent) => self.ensure_dir_exists(parent),\n" +
                        "             None => Ok(()),\n" +
                        "         }?")
                .matches("check_source_files(config, paths).await?")


        ;
    }

    @Test
    public void testNegationExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.NEGATION_EXPRESSION))
                .matches("!foo")
                .matches("-5")
                .matches("-bar")
                .notMatches("== b")


        ;
    }

    @Test
    public void testComparisonExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.COMPARISON_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("a==b")
                .matches("a == b")
                .notMatches("== b")
                .notMatches("a = = b")
                .matches("a!=b")
                .matches("a != b")
                .notMatches("a ! = b")
                .matches("a>b")
                .matches("a > b")
                .matches("a<b")
                .matches("a <   b")
                .notMatches("a <==> b")
                .matches("a<=b")
                .matches("a <= b")
                .notMatches("a < < = b")
                .matches("a>=b")
                .matches("a >= b")
                .notMatches("a > == b")
                .matches("position == 42")
                .matches("*position == 42")
                .matches("*position == StepPosition::JumpEnd")



        ;
    }

    @Test
    public void testLazyBooleanExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.LAZY_BOOLEAN_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("false || true")
                .matches("false && panic!()")
                .notMatches("== b")
                .matches("*position == StepPosition::JumpEnd || *position == StepPosition::End")

        ;
    }

    @Test
    public void testTypeCastExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_CAST_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("value as f64")
                .matches("{} as i32")
                .matches("test as i32")
                .matches("self.len() as u32")
                .matches("abc() as i32")
                .matches("len(vals) as f64")
        ;
    }


    @Test
    public void testAssignmentExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.ASSIGNMENT_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("x=42")
                .matches("x = 42")
                .notMatches("x = = 42")
                .notMatches("x = 42 =")
                .matches("self.state = 42")

        ;
    }

    @Test
    public void testCompoundAssignmentExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.COMPOUND_ASSIGNMENT_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("x+=42")
                .matches("x -= y")
                .matches("a *= y")
                .matches("b /= y")
                .matches("c %= 42")
                .matches("d &= y")
                .matches("e |= value")
                .matches("f1 ^= r")
                .matches("g12 <<= t")
                .matches("h24 >>= t")

        ;
    }

    @Test
    public void testOperatorExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.OPERATOR_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                //borrow expr
                .matches("&7")
                .matches("&mut array")
                .matches("&& 10")
                .matches("& & 10")
                .matches("&&&& mut 10")
                .matches("&& && mut 10")
                .matches("& & & & mut 10")
                //deref
                .matches("*thing")
                //err propagation
                .matches("foo?")
                .matches("None?")
                .matches("Some(42)?")
                //negation expression
                .matches("!foo")
                .matches("-5")
                .matches("-bar")
                .notMatches("== b")
                //arith expr
                .matches("1<<0")
                .matches("1+1")
                .matches("1+1+1+1")
                .matches("3*2")
                .matches("3*2*8")
                .matches("22/7")
                .matches("2^4")
                .matches("1-3")
                .matches("1-3-2")
                .matches("22 % 7")
                .matches("s_error.start_column.unwrap() - sc")
                .matches("m.get(i) + 1")
                //comparisons
                .matches("a == b")
                .matches("small < big")
                //lazy bool
                .matches("false || true")
               //type cast
                .matches("value as f64")
                //assignment
                .matches("value = 42")
                //compound assignment
                .matches("counter += 1")



        ;
    }
}
