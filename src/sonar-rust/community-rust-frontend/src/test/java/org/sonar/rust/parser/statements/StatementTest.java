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
package org.sonar.rust.parser.statements;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class StatementTest {

    @Test
    public void testLetStatement() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.LET_STATEMENT))
                .matches("let y=42;")
                .matches("let x;")
                .matches("let z = 40 + 2;")
                .matches("let res=calc(42);")
                .matches("let mut account=0.0;")
                .matches("let mut vec = Vec::new();")
                .matches("let three: i32 = add(1i32, 2i32);")
                .matches("let name: &'static str = (|| \"Rust\")();")
                .matches("let p = if mycondition {\n" +
                        "             42\n" +
                        "         } else {\n" +
                        "             43\n" +
                        "         };")
                .matches("let mystruct{a,}=value;")
                .matches("let ModuleSource{code,module_url_specified,module_url_found,}=info;")
                .matches("let e = JsError {\n" +
                        "      message: \"TypeError: baz\".to_string(),\n" +
                        "      source_line: Some(\"foo\".to_string()),\n" +
                        "      script_resource_name: Some(\"foo_bar.ts\".to_string()),\n" +
                        "      line_number: Some(4),\n" +
                        "      start_column: Some(16),\n" +
                        "      end_column: None,\n" +
                        "      frames: vec![],\n" +
                        "      stack: None,\n" +
                        "    };")
                .matches("let ref5 = fut5.await;")
                .matches("let mut6 = fut6.await;")
                .matches("let ref ref5 = fut5.await;")
                .matches("let mut mut6 = fut6.await;")
                .matches("let ref mut refmut = fut.await;")
                .matches("let x = i32;")
                .matches("let zero = <X as Default>::default();")


        ;
    }

    @Test
    public void testExpressionStatement() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXPRESSION_STATEMENT))
                .matches("return None;")
                .matches("a.b();")

        ;
    }

    @Test
    public void testStatement() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STATEMENT))
                .matches(";")
                .matches("extern crate pcre;")
                .matches("let y=42;")
                .matches("let x;")
                .matches("let z = 40 + 2;")
                .matches("use std::error::Error;")
                .matches("j.set(i.get());")
                .matches("j.set(i.get() + 1);")
                .matches("mod foobar{#![crate_type = \"lib\"]\n" +
                        "}")
                .matches("dest.write_char('n');")
                .matches("#[allow(unrooted_must_root)]\n" +
                        "    pub fn new(\n" +
                        "        window: &Window,\n" +
                        "        context: &AudioContext,\n" +
                        "        media_element: &HTMLMediaElement,\n" +
                        "    ) -> Fallible<DomRoot<MediaElementAudioSourceNode>> {\n" +
                        "        let node = MediaElementAudioSourceNode::new_inherited(context, media_element)?;\n" +
                        "        Ok(reflect_dom_object(Box::new(node), window))\n" +
                        "    }")
                .matches("state.put::<req::Client>({node_fetch::create_http_client(user_agent.clone(), my_data.clone()).unwrap()});")
                .matches("return;")
                .matches("return None;")
                .matches("Box::new(move |state : Rc<RefCell<OpState>>, bufs: BufVec| -> Op {\n" +
                        "        let mut b = 42;\n" +
                        "    });")
                .matches("obj.local();")
                .matches("async move {}.local();")
                .notMatches("let y=42; 42")


        ;
    }

    @Test
    public void testStatements() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STATEMENTS))

                //single statement
                .matches(";")
                .matches("let y=42;")
                .matches("let e = JsError {\n" +
                        "      message: \"TypeError: baz\".to_string(),\n" +
                        "      source_line: Some(\"foo\".to_string()),\n" +
                        "      script_resource_name: Some(\"foo_bar.ts\".to_string()),\n" +
                        "      line_number: Some(4),\n" +
                        "      start_column: Some(16),\n" +
                        "      end_column: None,\n" +
                        "      frames: vec![],\n" +
                        "      stack: None,\n" +
                        "    };")
                .matches("extern crate pcre;")

                //multi statements
                .matches("let y=42;let y=43;")
                .matches("let y=42; let y=43;")
                .matches("let y = 42;return y;")
                .matches("let a = 41;\n" +
                        "let c = 42;")
                //expression without block
                .matches("a.todo()")
                .matches("Vec::new")
                .matches("\"HELLO\"")
                .matches("other.major")
                .matches("a.major()")
                .matches("async {}.inc()")
                .matches("unsafe {}.inc()")
                .matches("async {}\n" +
                        ".inc()")
                //statement +ewob
                .matches("let y=42;42")
                .matches("let y=42; 42")
                .matches("let y=42; async {}.inc()")
                //ewb
                .matches("unsafe {}")
                .matches("unsafe {};")
                .matches("v8::V8::shutdown_platform();")
                .matches("unsafe {};\n" +
                        "let x=42;")
                .matches("unsafe {}let x=42;")
                .matches("unsafe {} let x=42;")
                .matches("unsafe {}\n" +
                        "let x=42;")
                //macro invocation semi
                .matches("assert_eq!(state.borrow::<MyStruct>().value, 110);")
                .matches("let x=42;\n" +
                        "assert_eq!(state.borrow::<MyStruct>().value, 110);")
                .matches("assert_eq!(state.borrow::<MyStruct>().value, 110);\n" +
                        "unsafe {}")
                .matches("unsafe {}\n" +
                        "assert_eq!(state.borrow::<MyStruct>().value, 110);")
                .matches("mystruct{};")
                .matches("test() ;")



        ;
    }
}
