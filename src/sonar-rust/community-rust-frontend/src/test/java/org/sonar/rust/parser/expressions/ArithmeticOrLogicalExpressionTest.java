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
import org.sonar.rust.api.RustPunctuator;
import org.sonar.sslr.grammar.GrammarRuleKey;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class ArithmeticOrLogicalExpressionTest {

    @Test
    public void testShlExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.SHL_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("1 << 0")
                .matches("0<< 1 <<2")
        ;
    }




    @Test
    public void testAddition() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.ADDITION_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("0+0")
                .matches("0 + 0")
                .matches("40 + 2")
                .matches("a+ 2")
                .matches("2 + b")
                .matches("a + b + c")
                .matches("1+2+3+a+b + 56 + foo")
                .matches("calc() + 12")
                .matches("m.get(i) + 1")
                .matches("(40+1)+1")
                ;
    }

    @Test
    public void testSubstraction() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.SUBTRACTION_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("0-0")
                .matches("0 - 0")
                .matches("40 - 2")
                .matches("a- 2")
                .matches("2 - b")
                .matches("a- b - c")
                .matches("1-2-3-a-b - 56 - foo")
        ;
    }

    @Test
    public void testMultiplication() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.MULTIPLICATION_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("0*0")
                .matches("0 * 0")
                .matches("40 * 2")
                .matches("a* 2")
                .matches("2 * b")
                .matches("a* b* c")
                .matches("1*2*3*a*b * 56 * foo")
        ;
    }

    @Test
    public void testBitAnd() {
       // assertThat(RustGrammar.create().build().rule(RustGrammar.BITAND_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("0&0")
                .matches("0 & 0")
                .matches("40 & 2")
                .matches("a& 2")
                .matches("2 & b")
                .matches("a& b& c")
                .matches("1&2&3&a&b & 56 & foo")
        ;
    }


    @Test
    public void testDivision() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.DIVISION_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("0/0")
                .matches("0 / 0")
                .matches("40 / 2")
                .matches("a/ 2")
                .matches("2 / b")
                .matches("a/ b/ c")
                .matches("1/2/3/a/b / 56 / foo")
        ;
    }





    @Test
    public void testArithmeticOrLogicalExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.ARITHMETIC_OR_LOGICAL_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("1<<0")
                .matches("40+2")
                .matches("39+1+2")
                .matches("1+2+3+4*7-3/10")
                .matches("calc()+2")
                .notMatches("== b")
                .matches("m.get(i) + 1")
        ;
    }
}
