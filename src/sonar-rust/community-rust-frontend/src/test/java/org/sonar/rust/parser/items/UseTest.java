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

public class UseTest {


    @Test
    public void testUseDeclaration() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.USE_DECLARATION))
                .matches("use std::option::Option::{Some, None};")
                .matches("use std::collections::hash_map::{self, HashMap};")
                .matches("use std::collections::hash_map::{self, HashMap, Foo, Bar};")
                .matches("use serde::de::{self, Deserialize, Deserializer, Visitor};")
                .matches("use rusty_v8 as v8;")


        ;

    }
}
