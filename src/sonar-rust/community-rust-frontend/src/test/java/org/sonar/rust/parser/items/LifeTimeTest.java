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

public class LifeTimeTest {


    @Test
    public void testForLifetimes(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.FOR_LIFETIMES))
                .matches("for <'a>")
                .matches("for <'ABC>")
                .matches("for <'a>")
                .matches("for <'ABC>")
               //FIXME .notMatches("for <'as>") //no keyword allowed
                //FIXME .notMatches("for<'trait>") //no keyword allowed
        ;

    }

    @Test
    public void testTypeBoundWhereClauseItem(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_BOUND_CLAUSE_ITEM))
                .matches("i32 :")
                .matches("for <'ABC> i32 :")
                .matches("for <'ABC> i32 : 'a")
                .matches("for <'ABC> i32 : 'a + 'b +'c")
                ;
    }

    @Test
    public void testLifetimeWhereClauseItem(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.LIFETIME_WHERE_CLAUSE_ITEM))
                .matches("'a:'b+'c+'d")
                .matches("'a : 'b+'c+'d")
                .matches("'ABC : 'b+ 'c+ 'd")

        ;
    }



    @Test
    public void testWhereClauseItem(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.WHERE_CLAUSE_ITEM))
                //type bound clause item
                .matches("i32 :")
                .matches("for <'ABC> i32 :")
                .matches("for <'ABC> i32 : 'a")
                .matches("for <'ABC> i32 : 'a + 'b +'c")
                //LifetimeWhereClauseItem
                .matches("'a:'b+'c+'d")
                .matches("'a : 'b+'c+'d")
                .matches("'ABC : 'b+ 'c+ 'd")
                .matches(" R: ValueOrVector")
                .matches("F: Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError> + 'static")
        ;
    }

    @Test
    public void testWhereClause(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.WHERE_CLAUSE))
                .matches("where i32 :")
                .matches("where i32 :,f64:")
                .matches("where for <'ABC> i32 :, i32:")
                .matches("where for <'ABC> i32 : 'a")
                .matches("where for <'ABC> i32 : 'a + 'b +'c")
                .matches("where 'a:'b+'c+'d")
                .matches("where 'a : 'b +'c +'d")
                .matches("where 'ABC : 'a+ 'b+ 'c, 'DEF : 'd + 'e + 'f")
                .notMatches("where 'a : 'b +'c +'d {}")
                .matches("where\n" +
                        "        R: ValueOrVector,")
                .matches("where\n" +
                        "        F: Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError> + 'static,\n" +
                        "        R: ValueOrVector,")
        ;
    }



    @Test
    public void testLifetime(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.LIFETIME))
                .matches("'a")
                .matches("'ABC")
                .notMatches("'trait") //no keyword allowed
                .matches("'static")
                .matches("'_")
                .notMatches("'a self")
                ;

    }

    @Test
    public void testTraitBound(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.TRAIT_BOUND))
                .matches("? abc::def")
                .matches("for <'a> abc::def")
                .matches("? for <'a> abc::def<T>")
                .matches("(abc::def::ghi)")
                .matches("(? abc::def)")
                .matches("( for <'a> abc::def )")
                .matches("(? for <'a> abc::def)")
                .matches("Fn(&mut OpState, u32, &mut [ZeroCopyBuf]) -> Result<R, AnyError>")


        ;

    }

    @Test
    public void testTypeParamBound(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_PARAM_BOUND))
                //lifetime
                .matches("'a")
                .matches("'ABC")
                .notMatches("'trait") //no keyword allowed
                .matches("'static")
                .matches("'_")
                .notMatches("'a self")
                //trait bound
                .matches("abc::def::foo<T>")
                .matches("? abc::def")
                .matches("for <'a> abc::def")
                .matches("? for <'a> abc::def<T>")
                .matches("(abc::def)")
                .matches("(? abc::def)")
                .matches("( for <'a> abc::def::ghi )")
                .matches("(? for <'a> abc::foo)")
        ;

    }

    @Test
    public void testTypeParamBounds(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_PARAM_BOUNDS))
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

        ;

    }

    @Test
    public void testTypeParam(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.TYPE_PARAM))
                .matches("AAA")
                .matches("#[test] AAA")
                .matches("#[test] AAA : i32")
                .matches("T")
        ;
    }

    @Test
    public void testConstParam(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.CONST_PARAM))
                .matches("const AAA : i32")

        ;
    }


    @Test
    public void testLifetimeParam(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.LIFETIME_PARAM))
                .matches("'a")
                .matches("'de")
                .matches("'ABC")
                .notMatches("'trait")
                .notMatches("'as")
                .notMatches("'as>")
                .notMatches("<'as")
                .notMatches("<'as>")
                .notMatches("'trait>")
        ;
    }

    @Test
    public void testLifetimeBounds(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.LIFETIME_BOUNDS))
                .matches("'a+'b+'c")
                .matches("'a + 'b + 'c")
                .matches("'BCD")
                ;
    }

    @Test
    public void testGenericParam(){
        assertThat(RustGrammar.create().build().rule(RustGrammar.GENERIC_PARAM))
                .matches("T")
                .matches("'de")
                .notMatches("'trait")
                .notMatches("'trait>")
                .matches("#[outer] 'ABC")
                .matches("#[outer] id = f64")
                .matches("#[outer] const AAA : i32")
                .matches("r = Result<T>")
                .matches("r = Result<T,U>")
                .matches("r = Result<(T,U)>")
                .matches("r = Result<(T,U),V>")
                .matches("Output = Result<CachedModule,(ModuleSpecifier,AnyError)>")
                .matches("r = Result<T,(U,V)>")
                .matches("r = Result<T, (U, V)>")
                .matches("Output = Result<CachedModule, (ModuleSpecifier, AnyError)>")



        ;
    }


    @Test
    public void testGenericParams() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.GENERIC_PARAMS))
                .matches("<>")
                .matches("< >")
                .matches("<T>")
                .matches("<'a,T>")
                .matches("<'a>")
                .matches("<'de>")
                .matches("<CachedModule>")
                .matches("< A, B >")
      ;

    }
}
