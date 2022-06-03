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
package org.sonar.rust.parser.attributes;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class AttributeTest {


    @Test
    public void testAttribute() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ATTR))
                .matches("foo")
                .matches("foo_bar")
                .matches("foo_type")
                .matches("crate_type")
        ;
    }


    @Test
    public void testInnerAttribute() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.INNER_ATTRIBUTE))
                .matches("#![crate_type = \"lib\"]")
                .matches("#![feature(const_fn_fn_ptr_basics)]")
                .matches("#![allow(unused_variables)]")
        ;
    }

    @Test
    public void testOuterAttribute() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.OUTER_ATTRIBUTE))
                .matches("#[test]")
                .matches("#[cfg(feature = \"serde\")]")
                .matches("#[inline]")
                .matches("#[allow(unrooted_must_root)]")
                .matches("#[cfg(not(any(target_os = \"macos\", windows)))]")
                .matches("#[allow(non_camel_case_types)]")
        ;
    }

    @Test
    public void testMetaWord() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.META_WORD))
                .matches("no_std")
        ;
    }

    @Test
    public void testMetaNameValueStr() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.META_NAME_VALUE_STR))
                .matches("doc = \"example\"")
        ;
    }

    @Test
    public void testMetaListPaths() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.META_LIST_PATHS))
                .matches("allow(unused, clippy::inline_always)")
        ;
    }

    @Test
    public void testMetaListIdents() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.META_LIST_IDENTS))
                .matches("macro_use(foo, bar)")
        ;
    }

    @Test
    public void testMetaListNameValueStr() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.META_LIST_NAME_VALUE_STR))
                .matches("link(name = \"CoreFoundation\", kind = \"framework\")")
        ;
    }
}