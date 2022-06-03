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

public class ArrayExpressionTest {
    @Test
    public void testArrayExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ARRAY_EXPRESSION))
                .matches("[]")
                .matches("[42]")
                .matches("[42,43]")
                .matches("[ 42 , 43 ]")
                .matches("[\"forty_two\"]")
                .matches("[\"s1\",\"s2\",\"s3\"]")
                .matches("[BIT1, BIT2]")
                .matches("[1, 2, 3, 4]")
                .matches("[\"a\", \"b\", \"c\", \"d\"]")
                .matches("[0; 128]")// array with 128 zeros
                .matches("[0u8, 0u8, 0u8, 0u8,]")
                .matches("[[1, 0, 0], [0, 1, 0], [0, 0, 1]]") // 2D array
                .matches("[b' ', b' ', b' ']")
     ;
    }

    @Test
    public void testIndexExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.INDEX_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("arr[42]")
                .matches("pair[0]")
                .matches("[42,43][0]")
                .matches("[42,43][0..1]")
                .matches("[b' ', b' ', b' '][0]")
                .matches("[b' ', b' ', b' '][0..1]")
                //FIXME.matches("[b' ', b' ', b' '][0..(4 - (len & 3)) & 3]")
                .matches("resp_header[0..8]")

        ;
    }
}
