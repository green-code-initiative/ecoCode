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

public class ExternBlockTest {



    @Test
    public void testExternalItem() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXTERNAL_ITEM))
                .matches("println!(\"hi\");") //macro invocation semi
                .matches("#[outer] println!(\"hi\");")
                .matches("static fdf : f64;") //external static item
                .matches("pub static fdf : f64;")
                .matches("#[outer] pub static fdf : f64;")
                .matches("fn draw()->Circle;") //external function item
                .matches("pub fn draw()->Circle;")
                .matches("#[outer] pub fn draw()->Circle;")


        ;

    }

    @Test
    public void testExternBlock() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.EXTERN_BLOCK))
                .matches("extern \"stdcall\" {}")
                .matches("extern \"stdcall\" {\n}")
                .matches("extern {}")
                .matches("extern r\"foo\" {}") //raw string
                .matches("extern \"foo\" {#![inner]}")
                //with external item
                 .matches("extern {\n" +
                "    fn foo(x: i32, ...);\n" +
                "}")
                .matches("extern {pub fn draw()->Circle;}")
                .matches("extern {\n" +
                        "    pub fn draw()->Circle;\n" +
                        "    }")
                .matches("extern {\n" +
                        "    pub fn draw()->Circle;\n" +
                        "    #[outer] pub fn draw()->Circle;\n" +
                        "    static fdf : f64;\n" +
                        "    }")


        ;

    }
}
