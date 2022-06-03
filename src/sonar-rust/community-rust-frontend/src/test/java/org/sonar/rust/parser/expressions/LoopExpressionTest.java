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

public class LoopExpressionTest {

    @Test
    public void testBreakExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.BREAK_EXPRESSION))
                .matches("break")
                .matches("break 42")
                .matches("break foo")
                .matches("break a.method()")
                .matches("break 'a b.method()")
                .matches("break Ok(Poll::Pending)")
                ;
    }

    @Test
    public void testContinueExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.CONTINUE_EXPRESSION))
                .matches("continue 'outer")

        ;
    }

    @Test
    public void testLoopExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.LOOP_EXPRESSION))
                .matches("while i < 10 {\n" +
                        "    println!(\"hello\");\n" +
                        "    i = i + 1;\n" +
                        "}")
                .matches("while let Some(y) = x.pop() {\n" +
                        "    println!(\"y = {}\", y);\n" +
                        "}")
                .matches("while let _ = 5 {\n" +
                        "    println!(\"Irrefutable patterns are always true\");\n" +
                        "    break;\n" +
                        "}")

                .matches("while let Some(v @ 1) | Some(v @ 2) = vals.pop() {\n" +
                        "    // Prints 2, 2, then 1\n" +
                        "    println!(\"{}\", v);\n" +
                        "}")


                .matches("for text in v {\n" +
                        "    println!(\"I like {}.\", text);\n" +
                        "}")
                .matches("for n in 1..11 {\n" +
                        "    sum += n;\n" +
                        "}")
                .matches("while i < j {\n" +
                        "    println!(\"hello\");\n" +
                        "    i = i + 1;\n" +
                        "}")
                .matches("for (i, n) in hist.normalized_bins().enumerate() {\n" +
                        "        let bin = (n as f64) / (N_SAMPLES as f64) ;\n" +
                        "        diff[i] = (bin - expected[i]).abs();\n" +
                        "    }")
        ;
    }
}
