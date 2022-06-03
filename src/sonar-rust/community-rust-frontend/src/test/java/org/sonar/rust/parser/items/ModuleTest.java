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

public class ModuleTest {

    @Test
    public void testModule() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MODULE))
                .matches("mod foo ;")
                .matches("mod bar {}")
                .matches("mod foobar{#![crate_type = \"lib\"]}")
                .matches("mod foobar{#![crate_type = \"lib\"]\n" +
                        "}")
                .matches("mod tests {\n" +
                        "    use super::Identifier;\n" +
                        "    use super::MyError;\n" +
                        "    use super::Version;\n" +
                        "    use std::result;\n" +
                        "}")
                .matches("mod math {\n" +
                        "    type Complex = (f64, f64);\n" +
                        "    fn sin(f: f64) -> f64 {\n" +
                        "        /* ... */\n" +
                        "    }\n" +
                        "    fn cos(f: f64) -> f64 {\n" +
                        "        /* ... */\n" +
                        "    }\n" +
                        "    fn tan(f: f64) -> f64 {\n" +
                        "        /* ... */\n" +
                        "    }\n" +
                        "}")
                .matches("unsafe mod math {\n" +
                        "    type Complex = (f64, f64);\n" +
                        "    fn sin(f: f64) -> f64 {\n" +
                        "        /* ... */\n" +
                        "    }\n" +
                        "    fn cos(f: f64) -> f64 {\n" +
                        "        /* ... */\n" +
                        "    }\n" +
                        "    fn tan(f: f64) -> f64 {\n" +
                        "        /* ... */\n" +
                        "    }\n" +
                        "}")

        ;

    }
}