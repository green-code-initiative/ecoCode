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

public class LiteralExpressionTest {

    @Test
    public void testLiteralExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.LITERAL_EXPRESSION))
                .matches("'f'")
                .matches("\"b\"")
                .matches("\"52\"")
                .matches("r\"foo\"")
                .matches("b'5'")
                .notMatches("+")
                .notMatches("{")
                .notMatches("\"hello\")")
                .matches("\"hello,world!\"")
                .notMatches("== b")
                .matches("42")
                .notMatches("42..")
                .matches("0.0")
        ;
    }
}
