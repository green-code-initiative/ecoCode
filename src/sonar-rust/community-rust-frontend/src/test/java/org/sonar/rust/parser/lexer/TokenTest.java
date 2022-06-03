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

public class TokenTest {

    @Test
    public void testTupleIndex() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TUPLE_INDEX))
                .matches("0")
                .matches("42")
                .matches("123")
                .matches("1")

        ;
    }


    @Test
    public void testToken() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TOKEN))
                .matches("a") //identifiers
                .matches("abc")
                .matches("A")
                .matches("AbCD")
                .matches("U123")
                .matches("as") //keywords
                .matches("break")
                .matches("const")
                .matches("continue")
                .matches("\"hello,world!\"")
                ;
    }
}
