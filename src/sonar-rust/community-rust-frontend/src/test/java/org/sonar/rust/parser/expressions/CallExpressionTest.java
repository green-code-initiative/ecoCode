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

public class CallExpressionTest {

    @Test
    public void testCallParams() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.CALL_PARAMS))
                .matches("..")
                .matches("1i32")
                .matches("{let y=42;}")
                .matches("{;}")
                .matches("0")
                .matches("3+2")
                .matches("1 << 1")
                .matches("foo")
                .matches("foo + 1")
                .matches("get()")
                .matches("get(i)")
                .matches("get(i) + 1")
                .matches("m.get(i) + 1")
                .matches("MediaElementAudioSourceNode {\n" +
                        "            node,\n" +
                        "            media_element,\n" +
                        "        }")
                .matches("|| 42")
                .matches("|i| 42")




        ;
    }

    @Test
    public void testCallExpression() {
//        assertThat(RustGrammar.create().build().rule(RustGrammar.CALL_EXPRESSION))
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION))
                .matches("foo()")
                .matches("abc()")
                .matches("add(1i32,2i32)")
                .matches("add(1i32, 2i32)")
                .matches("calc(get(i) + 1)")
                .matches("Vec::new()")
                .matches("Box::new(42)")
                .matches("PathBuf::from(\"/demo_dir/\")")
                .matches("PathBuf::from(r\"C:\\demo_dir\\\")")
                .matches("Box::new(|j: i32| -> () { println!(\"hello, {}\", j); })")
                .matches("Identifier::Numeric(n)")
                .matches("Ok(MediaElementAudioSourceNode {\n" +
                        "            node,\n" +
                        "            media_element,\n" +
                        "        })")
                .matches("node_fetch::create_http_client(user_agent.clone(), my_data.clone())")
                .matches("Box::new(move |state : Rc<RefCell<OpState>>, bufs: BufVec| -> Op {\n" +
                        "        let mut b = 42;\n" +
                        "    })")
                .matches("(state.get_error_class_fn)(1)")
                .matches("(state.borrow().get_error_class_fn)(&error)")
                .matches("Some(ec - (js_error.start_column.unwrap() - sc))")
                .matches("Ok(2)")
                .matches("Ok(())")
                .matches("f(stdout[..40])")
                .matches("<X as Default>::default()")
        ;
    }


}
