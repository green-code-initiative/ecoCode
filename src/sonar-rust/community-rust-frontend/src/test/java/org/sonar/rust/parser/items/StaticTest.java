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

public class StaticTest {
    @Test
    public void testStatic() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STATIC_ITEM))
                .matches("static mut LEVELS: u32 = 0;")
                .matches("static mut LEVELS: u32;")
                .matches("static INIT_ARRAY: unsafe extern \"C\" fn(c::c_int, *mut *mut u8, *mut *mut u8) = {\n" +
                        "    unsafe extern \"C\" fn function(_argc: c::c_int, _argv: *mut *mut u8, envp: *mut *mut u8) {\n" +
                        "        init_from_envp(envp);\n" +
                        "    }\n" +
                        "    function\n" +
                        "};")


        ;

    }
}
