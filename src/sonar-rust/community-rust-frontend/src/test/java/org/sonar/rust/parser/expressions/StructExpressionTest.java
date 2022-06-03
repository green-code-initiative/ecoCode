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

public class StructExpressionTest {

    //case 3
    @Test
    public void testStructExprUnit() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRUCT_EXPR_UNIT))
                .matches("Vec::<u8>::with_capacity")
                .matches("collect::<Vec<_>>")
                .matches("some_fn::<Cookie>")

        ;

    }

    //case 2
    @Test
    public void testStructExprTuple() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRUCT_EXPR_TUPLE))
                .matches("Vec::<u8>::with_capacity()")
                .matches("collect::<Vec<_>>()")
                .matches("some_fn::<Cookie>()")
                .matches("some_fn::<Cookie>(Cookie)")
        ;

    }


    @Test
    public void testStructExprBase() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRUCT_BASE))
                .matches("..foo")
                .matches(".. bar")


        ;

    }

    //case 1
    @Test
    public void testStructExprStruct() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRUCT_EXPR_STRUCT))
                .matches("game::User{name:\"Joe\",age:35,score:100_000}")
                .matches("game::User {name: \"Joe\", age: 35, score: 100_000}")
                .matches("TuplePoint { 0: 10.0, 1: 20.0}")
                .matches("TuplePoint { .. bar}")
                .matches("TuplePoint { age :35,.. structbase}")
                .matches("Point {x: 10, y: 20}")
                .matches("Self {\n" +
                        "            current_mouse_cursor,\n" +
                        "            mouse_visible: true,\n" +
                        "            windowed_context,\n" +
                        "            #[cfg(not(any(target_os = \"macos\", windows)))]\n" +
                        "            should_draw: Arc::new(AtomicBool::new(true)),\n" +
                        "            #[cfg(all(feature = \"wayland\", not(any(target_os = \"macos\", windows))))]\n" +
                        "            wayland_surface,\n" +
                        "            dpr,\n" +
                        "        }")
                .matches("Self {\n" +
                        "            current_mouse_cursor,\n" +
                        "            mouse_visible: true,\n" +
                        "            windowed_context,\n" +
                        "            #[cfg(not(any(target_os = \"macos\", windows)))]\n" +
                        "            should_draw: Arc::new(AtomicBool::new(true)),\n" +
                        "            #[cfg(all(feature = \"wayland\", not(any(target_os = \"macos\", windows))))]\n" +
                        "            wayland_surface,\n" +
                        "            dpr,\n" +
                        "        }")

        ;
    }


    @Test
    public void testStructExprFields() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRUCT_EXPR_FIELDS))
                .matches("name:\"Joe\",age:35,score:100_000")
                .matches("name: \"Joe\", age : 35, score : 100_000")
                .matches("0: 10.0, 1: 20.0")
          ;



    }

    @Test
    public void testStructExprField() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRUCT_EXPR_FIELD))
                .matches("name")
                .matches("name:\"Joe\"")
                .matches("age:35")
                .matches("score:100_000")
                .matches("score : 100_000")
                .matches("0: 10.0")
                .matches("name: user.getName()")
                .matches("name: user.name")
        ;



    }



    @Test
    public void testStructExpression() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRUCT_EXPRESSION))
                .matches("Point{x:10.0,y:20.0}")
                .matches("Point {x: 10.0, y: 20.0}")
                .matches("NothingInMe {}")
                .matches("TuplePoint { 0: 10.0, 1: 20.0 }")
                .matches("game::User {name: \"Joe\", age: 35, score: 100_000}")
                .matches("some_fn::<Cookie>(Cookie)")
                .matches("Version {\n" +
                        "            name:  user.firstName,\n" +
                        "            major: other.major,\n" +
                        "            minor: other.minor,\n" +
                        "            patch: other.patch,\n" +
                        "        }")
                .matches("MediaElementAudioSourceNode {\n" +
                        "            node,\n" +
                        "            media_element,\n" +
                        "        }")
                .notMatches("match path.parent() {\n" +
                "             Some(ref parent) => self.ensure_dir_exists(parent),\n" +
                "             None => Ok(()),\n" +
                "         }")
                .matches("JsError {\n" +
                        "message: js_error.message.clone(),\n" +
                        "source_line,\n" +
                        "script_resource_name,\n" +
                        "line_number,\n" +
                        "start_column,\n" +
                        "end_column,\n" +
                        "frames: js_error.frames.clone(),\n" +
                        "stack: None,\n" +
                        "}")
                .matches("Self {\n" +
                        "            current_mouse_cursor,\n" +
                        "            mouse_visible: true,\n" +
                        "            windowed_context,\n" +
                        "            #[cfg(not(any(target_os = \"macos\", windows)))]\n" +
                        "            should_draw: Arc::new(AtomicBool::new(true)),\n" +
                        "            #[cfg(all(feature = \"wayland\", not(any(target_os = \"macos\", windows))))]\n" +
                        "            wayland_surface,\n" +
                        "            dpr,\n" +
                        "        }")



        ;



    }
}
