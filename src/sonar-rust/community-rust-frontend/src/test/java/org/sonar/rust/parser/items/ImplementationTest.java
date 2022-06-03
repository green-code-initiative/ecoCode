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

public class ImplementationTest {

    @Test
    public void testInherentImplItem() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ASSOCIATED_ITEM))
                .matches("println!(\"hello\");") //macro invocation semi
                .matches("#[outer] println!(\"hello\");") //macro invocation semi
                .matches("const BIT2: u32 = 1 << 1;") //constant
                .matches("#[outer] fn answer_to_life_the_universe_and_everything() -> i32 {\n" +
                        "    let y=42;\n" +
                        "}") //function
                .matches("fn by_ref(self: &Self) {}") //method
                .matches("#[inline]\n" +
                        "    fn fanswer_to_life_the_universe_and_everything() -> i32 {\n" +
                        "    let y=42;\n" +
                        "    }")
                .matches("#[inline] fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {\n" +
                        "        match *self {\n" +
                        "            Identifier::Numeric(ref n) => fmt::Display::fmt(n, f),\n" +
                        "            Identifier::AlphaNumeric(ref s) => fmt::Display::fmt(s, f),\n" +
                        "        }\n" +
                        "    }")
                .matches("#[inline] \n" +
                        "fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {\n" +
                        "        match *self {\n" +
                        "            Identifier::Numeric(ref n) => fmt::Display::fmt(n, f),\n" +
                        "            Identifier::AlphaNumeric(ref s) => fmt::Display::fmt(s, f),\n" +
                        "        }\n" +
                        "    }")
                .matches("#[allow(unrooted_must_root)]\n" +
                        "    pub fn new(\n" +
                        "        window: &Window,\n" +
                        "        context: &AudioContext,\n" +
                        "        media_element: &HTMLMediaElement,\n" +
                        "    ) -> Fallible<DomRoot<MediaElementAudioSourceNode>> {\n" +
                        "        let node = MediaElementAudioSourceNode::new_inherited(context, media_element)?;\n" +
                        "        Ok(reflect_dom_object(Box::new(node), window))\n" +
                        "    }")
                .matches("pub const BIT2: u32 = 1 << 1;") //constant


        ;

    }

    @Test
    public void testInherentImpl() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.INHERENT_IMPL))
                .matches("impl Color {}")
                .matches("impl <T> Color {}")
                .matches("impl Color where 'a : 'b +'c +'d {}")
                .matches("impl Color {#![inner]}")
                .matches("impl Color {fn by_ref(self: &Self) {}}")
                .matches("impl Color {#![inner] fn by_ref(self: &Self) {}}")
                .matches("impl Color where 'a : 'b +'c +'d {#![inner] fn by_ref(self: &Self) {}}")
                .matches("impl <U> Color where 'a : 'b +'c +'d {#![inner] fn by_ref(self: &Self) {}}")
                .matches("impl MediaElementAudioSourceNode {\n" +
                        "    #[allow(unrooted_must_root)]\n" +
                        "    fn new_inherited(\n" +
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
                        "    }\n" +
                        " \n" +

                        "    #[allow(unrooted_must_root)]\n" +
                        "    pub fn new(\n" +
                        "        window: &Window,\n" +
                        "        context: &AudioContext,\n" +
                        "        media_element: &HTMLMediaElement,\n" +
                        "    ) -> Fallible<DomRoot<MediaElementAudioSourceNode>> {\n" +
                        "        let node = MediaElementAudioSourceNode::new_inherited(context, media_element)?;\n" +
                        "        Ok(reflect_dom_object(Box::new(node), window))\n" +
                        "    }\n" +
                        "\n" +
                        "    #[allow(non_snake_case)]\n" +
                        "    pub fn Constructor(\n" +
                        "        window: &Window,\n" +
                        "        context: &AudioContext,\n" +
                        "        options: &MediaElementAudioSourceOptions,\n" +
                        "    ) -> Fallible<DomRoot<MediaElementAudioSourceNode>> {\n" +
                        "        MediaElementAudioSourceNode::new(window, context, &*options.mediaElement)\n" +
                        "    }\n" +
                        "}")

        ;

    }


    @Test
    public void testTraitImpl() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TRAIT_IMPL))
                .matches("impl abc::(isize) -> isize for Circle {}")
                .matches("impl abc::(isize) -> isize for Circle {println!(\"hello\");}")
                .matches("impl abc::(isize) -> isize for Circle {type Point = (u8, u8);}")
                .matches("impl abc::(isize) -> isize for Circle {fn answer_to_life_the_universe_and_everything() -> i32 {\n}" +
                        "    }")
                .matches("impl abc::(isize) -> isize for Circle {fn by_ref(self: &Self) {}}")
                .matches("unsafe impl abc::(isize) -> isize for Circle {}")
                .matches("impl <T>abc::(isize) -> isize for Circle {}")
                .matches("impl !abc::(isize) -> isize for Circle {}")
                .matches("impl abc::(isize) -> isize for Circle {#[outer]fn answer_to_life_the_universe_and_everything() -> i32 {\n}" +
                        "    }")
                .matches("impl abc::(isize) -> isize for Circle {" +
                        "#[outer]" +
                        "fn answer_to_life_the_universe_and_everything() -> i32 {\n}" +
                        "    }")
                .matches("impl<'de> Deserialize<'de> for Identifier {\n" +
                        "    fn deserialize<D>(deserializer: D) -> result::Result<Self, D::Error>\n" +
                        "    where\n" +
                        "        D: Deserializer<'de>,\n" +
                        "    {\n" +
                        "        struct IdentifierVisitor;\n" +
                        "\n" +
                        "        // Deserialize Identifier from a number or string.\n" +
                        "        impl<'de> Visitor<'de> for IdentifierVisitor {\n" +
                        "            type Value = Identifier;\n" +
                        "\n" +
                        "            fn expecting(&self, formatter: &mut fmt::Formatter) -> fmt::Result {\n" +
                        "                formatter.write_str(\"a SemVer pre-release or build identifier\")\n" +
                        "            }\n" +
                        "\n" +
                        "            fn visit_u64<E>(self, numeric: u64) -> result::Result<Self::Value, E>\n" +
                        "            where\n" +
                        "                E: de::Error,\n" +
                        "            {\n" +
                        "                Ok(Identifier::Numeric(numeric))\n" +
                        "            }\n" +
                        "\n" +
                        "            fn visit_str<E>(self, alphanumeric: &str) -> result::Result<Self::Value, E>\n" +
                        "            where\n" +
                        "                E: de::Error,\n" +
                        "            {\n" +
                        "                Ok(Identifier::AlphaNumeric(alphanumeric.to_owned()))\n" +
                        "            }\n" +
                        "        }\n" +
                        "\n" +
                        "        deserializer.deserialize_any(IdentifierVisitor)\n" +
                        "    }\n" +
                        "}")



        ;

    }


    @Test
    public void testTraitImplItem() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ASSOCIATED_ITEM))
                .matches("println!(\"hello\");") //macro invocation semi
                .matches("#[outer] println!(\"hello\");") //macro invocation semi
                .matches("type Point = (u8, u8);") //type alias
                .matches("#[outer] type Point = (u8, u8);") //type alias
                .matches("const BIT2: u32 = 1 << 1;") //constant
                .matches("#[outer] fn answer_to_life_the_universe_and_everything() -> i32 {\n" +
                        "    let y=42;\n" +
                        "}") //function
                .matches("fn by_ref(self: &Self) {}") //method
                .matches("#[inline]\n" +
                        "    fn fmt(&self,f: &mut fmt::Formatter) -> fmt::Result {\n" +
                        "        match *self {\n" +
                        "            Identifier::Numeric(ref n) => fmt::Display::fmt(n, f),\n" +
                        "            Identifier::AlphaNumeric(ref s) => fmt::Display::fmt(s, f),\n" +
                        "        }\n" +
                        "    }")

        ;

    }

    @Test
    public void testImplementation() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.IMPLEMENTATION))
                //trait impl
                .matches("impl abc::(isize) -> isize for Circle {}")
                .matches("impl abc::(isize) -> isize for Circle {println!(\"hello\");}")
                .matches("impl abc::(isize) -> isize for Circle {type Point = (u8, u8);}")
                .matches("impl abc::(isize) -> isize for Circle {fn answer_to_life_the_universe_and_everything() -> i32 {\n}" +
                        "    }")
                .matches("impl abc::(isize) -> isize for Circle {fn by_ref(self: &Self) {}}")
                .matches("unsafe impl abc::(isize) -> isize for Circle {}")
                .matches("impl <T>abc::(isize) -> isize for Circle {}")
                .matches("impl !abc::(isize) -> isize for Circle {}")
                //InherentImpl
                .matches("impl Color {}")
                .matches("impl <T> Color {}")
                .matches("impl Color where 'a : 'b +'c +'d {}")
                .matches("impl Color {#![inner]}")
                .matches("impl Color {fn by_ref(self: &Self) {}}")
                .matches("impl Color {#![inner] fn by_ref(self: &Self) {}}")
                .matches("impl Color where 'a : 'b +'c +'d {#![inner] fn by_ref(self: &Self) {}}")
                .matches("impl <U> Color where 'a : 'b +'c +'d {#![inner] fn by_ref(self: &Self) {}}")
                .matches("impl MediaElementAudioSourceNode {\n" +
                        "    #[allow(unrooted_must_root)]\n" +
                        "    fn new_inherited(\n" +
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
                        "    }\n" +

                        "    #[allow(unrooted_must_root)]\n" +
                        "    pub fn new(\n" +
                        "        window: &Window,\n" +
                        "        context: &AudioContext,\n" +
                        "        media_element: &HTMLMediaElement,\n" +
                        "    ) -> Fallible<DomRoot<MediaElementAudioSourceNode>> {\n" +
                        "        let node = MediaElementAudioSourceNode::new_inherited(context, media_element)?;\n" +
                        "        Ok(reflect_dom_object(Box::new(node), window))\n" +
                        "    }\n" +
                        "\n" +
                        "    #[allow(non_snake_case)]\n" +
                        "    pub fn Constructor(\n" +
                        "        window: &Window,\n" +
                        "        context: &AudioContext,\n" +
                        "        options: &MediaElementAudioSourceOptions,\n" +
                        "    ) -> Fallible<DomRoot<MediaElementAudioSourceNode>> {\n" +
                        "        MediaElementAudioSourceNode::new(window, context, &*options.mediaElement)\n" +
                        "    }\n" +
                        "}")
                .matches("impl<'a, 'b, 'c, S> ser::SerializeTupleVariant\n" +
                        "  for VariantSerializer<'a, 'b, 'c, S>\n" +
                        "where\n" +
                        "  S: ser::SerializeTupleStruct<Ok = JsValue<'a>, Error = Error>,\n" +
                        "{\n" +
                        "  type Ok = JsValue<'a>;\n" +
                        "  type Error = Error;\n" +
                        "\n" +
                        "  fn serialize_field<T: ?Sized + Serialize>(\n" +
                        "    &mut self,\n" +
                        "    value: &T,\n" +
                        "  ) -> Result<()> {\n" +
                        "    self.inner.serialize_field(value)\n" +
                        "  }\n" +
                        "\n" +
                        "  fn end(self) -> JsResult<'a> {\n" +
                        "    self.end(S::end)\n" +
                        "  }\n" +
                        "}")


        ;

    }


}
