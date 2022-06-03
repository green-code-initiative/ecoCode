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

public class AssociatedTest {


    @Test
    public void testTypedSelf() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPED_SELF))
                .matches("self:i32")
                .matches("self: Self")
                .matches("self : i32")
                .matches("mut self:f64")
                .matches("mut self : f64")
                .matches("self: &mut Arc<Rc<Box<Alias>>>")
                .matches("self: &'a Arc<Rc<Box<Alias>>>")

        ;
    }

    @Test
    public void testShortHandSelf() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.SHORTHAND_SELF))
                .matches("self")
                .matches("mut self")
                .matches("&self")
                .matches("&mut self")
                .matches("&'ABC self")



        ;
    }

    @Test
    public void testSelfParam() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.SELF_PARAM))
                .matches("self")
                .matches("mut self")
                .matches("&self")
                .matches("&mut self")
                .matches("&'ABC self")
                .matches("self:i32")
                .matches("self: Self")
                .matches("self : i32")
                .matches("#[test] mut self:f64")
                .matches("#[test] mut self : f64")
                .matches("self: &Self")
                .matches("self: Self")
                .matches("self: &mut Self")
                .matches("self: Box<Self>")
                .matches("self: Rc<Self>")
                .matches("self: Arc<Self>")
                .matches("self: Pin<&Self>")
                .matches("self: Arc<Example>")
                .matches("self: &'a Self")
                .matches("self: &mut  Arc<Rc<Box<Alias>>>")
                .matches("self: &'a  Arc<Rc<Box<Alias>>>")
                .matches("self: <Example as Trait>::Output")

        ;
    }



}
