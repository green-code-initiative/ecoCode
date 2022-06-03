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

public class ClosureExpressionTest {


    @Test
    public void testClosureParameters() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.CLOSURE_PARAMETERS))
                .matches("k:i32")
                .matches("j")
                .matches("state: Rc<RefCell<OpState>>, bufs: BufVec")
        ;
    }

    @Test
    public void testClosureParameter() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.CLOSURE_PARAM))
                .matches("k:i32")
                .matches("j")
                .matches("state: Rc<RefCell< OpState>>")
                .matches("bufs : BufVec")
                .matches("&i")
                .notMatches("&i| i")
        ;
    }

    @Test
    public void testClosureExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.CLOSURE_EXPRESSION))
                .matches("|k:i32|->(){println!(\"hello,{}\",k)}")
                .matches("|j: i32| -> () { println!(\"hello, {}\", j); }")
                .matches("move |j| println!(\"{}, {}\", word, j)")
                .matches("move |state: Rc<RefCell<OpState>>, bufs: BufVec| -> Op {\n" +
                        "        let mut bufs_iter = bufs.into_iter();\n" +
                        "}")
                .matches("move |state : Rc<RefCell<OpState>>, bufs: BufVec| -> Op {\n" +
                        "        let mut b = 42;\n" +
                        "    }")
                .matches("|paths: Vec<PathBuf>| {\n" +
                        "        let config = get_typescript_config();\n" +
                        "        a\n" +
                        "            .boxed_local()\n" +
                        "    }")
                .matches("|&i|{i==NUM_MSG}")
                .matches("|| i == NUM_MSG")
                .matches("|i| i == NUM_MSG")
                .matches("|&i| i == NUM_MSG")

            ;
    }
}
