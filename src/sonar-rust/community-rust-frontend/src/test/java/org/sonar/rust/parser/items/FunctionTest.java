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
package org.sonar.rust.parser.items;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class FunctionTest {

    @Test
    public void testFunctionQualifiers() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.FUNCTION_QUALIFIERS))
                .matches("const")
                .matches("async")
                .matches("")
                .matches("const unsafe")
                .matches("async unsafe")
                .matches("async")
                .matches("extern \"C\"")

        ;

    }



    @Test
    public void testAbi() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ABI))
                .matches("r\"foo\"")
                .matches("\"abc\"")
                .matches("r#\"foo\"#")
                .matches("\"C\"")
        ;

    }


    @Test
    public void testFunctionReturnType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.FUNCTION_RETURN_TYPE))
                .matches("->i32")
                .matches("-> i32")
        ;

    }


    @Test
    public void testFunctionParam() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.FUNCTION_PARAM))
                .matches("x : i32")
                .matches("y:i64")
                .matches("f: &mut fmt::Formatter")
                .matches("bytes : u8")
                .matches("bytes: &[u8]")



        ;

    }

    @Test
    public void testFunctionParameters() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.FUNCTION_PARAMETERS))
                .matches("y:i64")
                .matches("x : i32")
                .matches("x : i32, y:i64")
                .matches("&self, n:u32")
                .matches("*mut *mut u8, *mut *mut u8")
                .matches("c::c_int, *mut *mut u8, *mut *mut u8")

        ;

    }


    @Test
    public void testFunction() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.FUNCTION))
                .matches("fn same(x : i32)->i32 {;}")
                .matches("fn same(x : i32) -> i32 {;}")
                .matches("fn answer_to_life_the_universe_and_everything() -> i32 {\n" +
                        "    let y=42;\n" +
                        "}")
                .matches("fn main() {\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "}")
                .matches("fn main() {\n" +
                        "    abc()\n" +
                        "}")
                .matches("fn main() {\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "    println!(\"A second line \");\n" +
                        "}")
                .matches("fn main() {\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "    abc()\n" +
                        "}")
                .matches("fn new_inherited(\n" +
                        "        context: &AudioContext,\n" +
                        "        media_element: &HTMLMediaElement,\n" +
                        "    ) -> Fallible<MediaElementAudioSourceNode> {\n" +
                        "        let node = AudioNode::new_inherited(\n" +
                        "            AudioNodeInit::MediaElementSourceNode,\n" +
                        "            &*context.base(),\n" +
                        "            Default::default(),\n" +
                        "            0,\n" +
                        "            1,\n" +
                        "        )?;\n" +
                        "        let (sender, receiver) = mpsc::channel();\n" +
                        "        node.message(AudioNodeMessage::MediaElementSourceNode(\n" +
                        "            MediaElementSourceNodeMessage::GetAudioRenderer(sender),\n" +
                        "        ));\n" +
                        "        let audio_renderer = receiver.recv().unwrap();\n" +
                        "        media_element.set_audio_renderer(audio_renderer);\n" +
                        "        let media_element = Dom::from_ref(media_element);\n" +
                        "        Ok(MediaElementAudioSourceNode {\n" +
                        "            node,\n" +
                        "            media_element,\n" +
                        "        })\n" +
                        "    }")
                .matches("fn from_raw(bytes: &[u8]) -> Option<Self> {\n" +
                        "\n" +
                        "        Some(Self {\n" +
                        "            request_id: u64::from_le_bytes(bytes[0..8].try_into().unwrap()),\n" +
                        "            argument: u32::from_le_bytes(bytes[8..12].try_into().unwrap()),\n" +
                        "        })\n" +
                        "    }")
                .matches("fn from_raw(bytes: &[u8]) -> Option<Self> {\n" +
                        "        if bytes.len() < 3 * 4 {\n" +
                        "            return None;\n" +
                        "        }\n" +
                        "\n" +
                        "        Some(Self {\n" +
                        "            request_id: u64::from_le_bytes(bytes[0..8].try_into().unwrap()),\n" +
                        "            argument: u32::from_le_bytes(bytes[8..12].try_into().unwrap()),\n" +
                        "        })\n" +
                        "    }")

                .matches("fn gen_padding_32bit(len: usize) -> &'static [u8] {\n" +

                        "    &[b' ', b' ', b' '][0..(4 - (len & 3)) & 3]\n" +
                        "}")
                .matches("fn foo() -> u8\n" +
                        "{\n" +
                        "    Box::new(move |state : Rc<RefCell<OpState>>, bufs: BufVec| -> Op {\n" +
                        "        let mut b = 42;\n" +
                        "    })\n" +
                        "}")
                .matches("fn foo() -> u8\n" +
                        "{\n" +
                        "   let mut b = 42;\n" +
                        "   a.local()\n" +
                        "}")
                .matches("fn foo() -> u8\n" +
                        "{\n" +
                        "   let mut b = 42;\n" +
                        "   async move {}.local()\n" +
                        "}")
                .matches("fn foo(f : FnOp) -> u8\n" +
                        "{\n" +
                        "   let mut b = 42;\n" +
                        "   async move {}.local()\n" +
                        "}")
                .matches("fn foo(f: impl FnOnce()) -> u8\n" +
                        "{\n" +
                        "   let mut b = 42;\n" +
                        "   async move {}.local()\n" +
                        "}")
                .matches("fn apply_source() -> JsError {\n" +
                        "    JsError {}     \n" +
                        "}")




        ;

    }
}