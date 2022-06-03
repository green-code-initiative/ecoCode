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
package org.sonar.rust.parser.types;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class TypeTest {

    @Test
    public void testParenthesisType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.PARENTHESIZED_TYPE))
                .matches("(i32)")
                .matches("( i32 )")

        ;
    }

    @Test
    public void testImplTraitTypeOneBound() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.IMPL_TRAIT_TYPE_ONE_BOUND))
                .matches("impl ? abc::def")
                .matches("impl for <'a> abc::def")
                .matches("impl ? for <'a> abc::def<T>")
                .matches("impl (abc::def::ghi)")
                .matches("impl (? abc::def)")
                .matches("impl ( for <'a> abc::def )")
                .matches("impl (? for <'a> abc::def)")
                .matches("impl Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError>")

        ;
    }

    @Test
    public void testTraitObjectTypeOneBound() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TRAIT_OBJECT_TYPE_ONE_BOUND))
                .matches("? abc::def")
                .matches("for <'a> abc::def")
                .matches("? for <'a> abc::def<T>")
                .matches("(abc::def::ghi)")
                .matches("(? abc::def)")
                .matches("( for <'a> abc::def )")
                .matches("(? for <'a> abc::def)")
                .matches("Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError>")
                .matches("dyn ? abc::def")
                .matches("dyn for <'a> abc::def")
                .matches("dyn ? for <'a> abc::def<T>")
                .matches("dyn (abc::def::ghi)")
                .matches("dyn (? abc::def)")
                .matches("dyn ( for <'a> abc::def )")
                .matches("dyn (? for <'a> abc::def)")
                .matches("dyn Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError>")
                .matches("dyn Future<Input = Result<CachedModule, (ModuleSpecifier, AnyError)>>")

        ;
    }

    @Test
    public void testBareFunctionType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.BARE_FUNCTION_TYPE))
                .matches("extern \"C\" fn(this: *mut iasset) -> i32")


        ;
    }

    @Test
    public void testTypeNoBounds() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_NO_BOUNDS))
                .matches("i32")
                .matches("(i32, u8)")
                .matches("Circle")
                .notMatches("Circle{")
                .matches("[u8]")
                .matches("extern \"C\" fn(this: *mut iasset) -> i32")
                .matches("Token![#]")



        ;
    }

    @Test
    public void testimplTraitType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.IMPL_TRAIT_TYPE))
            .matches("impl Foo")
                .matches("impl Foo")
                .matches("impl FnOnce()")


        ;
    }

    @Test
    public void testTraitObjectType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TRAIT_OBJECT_TYPE))
                .matches("'a")
                .matches("'a+'a")
                .matches("'a + 'b + 'c")
                .matches("'ABC")
                .matches("'ABC+'DCE")
                .notMatches("'trait") //no keyword allowed
                .matches("'static")
                .matches("'_")
                .matches("'_+'a")
                .matches("'_+'a+'ABC")
                .notMatches("'a self")
                .matches("(? for <'a> abc::def)+'a+abc::def")
                .matches("ValueOrVector")
                .matches("Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError> + 'static")
                .matches("dyn 'a")
                .matches("dyn 'a+'a")
                .matches("dyn 'a + 'b + 'c")
                .matches("dyn 'ABC")
                .matches("dyn 'ABC+'DCE")
                .notMatches("dyn 'trait") //no keyword allowed
                .matches("dyn 'static")
                .matches("dyn '_")
                .matches("dyn '_+'a")
                .matches("dyn '_+'a+'ABC")
                .notMatches("dyn 'a self")
                .matches("dyn (? for <'a> abc::def)+'a+abc::def")
                .matches("dyn ValueOrVector")
                .matches("dyn Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError> + 'static")
                .matches("dyn Future<Output = i32>\n" +
                        "       + 'static\n" +
                        "       + Send")
                .matches("dyn Future<Output = Result<CachedModule, (ModuleSpecifier, AnyError)>>\n" +
                        "    + 'static\n" +
                        "    + Send")




        ;
    }




    @Test
    public void testType() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE))
                .matches("extern \"C\" fn(this: *mut iasset) -> i32")
                .matches("i32")
                .matches("(i32, u8)")
                .matches("Circle")
                .notMatches("Circle{")
                .matches("semver_parser::version")
                .matches("semver_parser::version::Identifier")
                .matches("From<semver_parser::version::Identifier>")
                .matches("&hhh")
                .matches("&u8")
                .matches("&[u8]")
                .matches("Result<CachedModule, ModuleSpecifier>")
                .matches("Result<(CachedModule, ModuleSpecifier)>")
                .matches("Result<(CachedModule, ModuleSpecifier),AnyError >")
                .matches("Result<T,(U,V)>")
                .matches("Result<T, (U, V)>")
                .matches("Result<CachedModule, (ModuleSpecifier, AnyError)>")
                .matches("dyn Future<Input = Result<CachedModule, (ModuleSpecifier, AnyError)>>")
                .matches("dyn Future<Output = Result<CachedModule, (ModuleSpecifier, AnyError)>>\n" +
                        "    + 'static\n" +
                        "    + Send")
                .matches("impl FnOnce()")
                .matches("<X as Default>::default()")
                .matches("Token![#]")
                .matches("Self")




        ;
    }
}
