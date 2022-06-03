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

public class BlockExpressionTest {

    /*
    BlockExpression :
   {
      InnerAttribute*
      Statements?
   }

Statements :
      Statement+
   | Statement+ ExpressionWithoutBlock
   | ExpressionWithoutBlock
     */




    @Test
    public void testAsyncBlockExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ASYNC_BLOCK_EXPRESSION))
                .matches("async {}")
                .matches("async move {}")
        ;
    }

    @Test
    public void testBlockExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.BLOCK_EXPRESSION))
                .matches("{}")
                .matches("{\n" +
                        "    // comment\n" +
                        "}")
                .matches("{let y=42;}")
                .matches("{println!(\"hi there\");}")
                .matches("{abc()}")
                .matches("{\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "    abc()\n" +
                        "}")
                .matches("{\n" +
                        "    fn_call();\n" +
                        "}")
                .matches("{\n" +
                        "    fn_call();\n" +
                        "    5\n" +
                        "}")
                .matches("{println!(\"hello,{}\",k)}")
                .matches("{ println!(\"hello, {}\", j); }")
                .matches("{ i.set(i.get()); false }")
                .matches("{ i.set(i.get() + 1); false }")
                .matches("{\n" +
                        "      node_fetch::create_http_client(user_agent.clone(), my_data.clone())\n" +
                        "          .unwrap()\n" +
                        "    }")
                .matches("{\n" +
                        "            return None;\n" +
                        "        }")
                .matches("{\n" +
                        "        self.len() as u32\n" +
                        "    }")
                .matches("{\n" +
                        "    &[b' ', b' ', b' '][0..(4 - (len & 3)) & 3]\n" +
                        "}")

                .matches("{ Box::new(move |state : Rc<RefCell<OpState>>, bufs: BufVec| -> Op {\n" +
                        "        let mut b = 42;\n" +
                        "    })\n" +
                        "}")
                .matches("{\n" +
                        "            PathBuf::from(\"/demo_dir/\")\n" +
                        "        }")
                .matches("{PathBuf::from(r\"C:\\demodir\\\")}")
                .matches("{\n" +
                        "            if check {\n" +
                        "                check_source_files(config, paths).res1;\n" +
                        "            } else {\n" +
                        "                format_source_files(config, paths).res2;\n" +
                        "            }\n" +
                        "            Ok(())\n" +
                        "        }")
                .matches("{\n" +
                        "    JsError {}     \n" +
                        "}")
                .matches("{\n" +
                        "  continue 'outer;\n" +
                        "}")




        ;
    }
}
