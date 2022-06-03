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

public class ItemTest {



        /*

Item:
   OuterAttribute*
      VisItem
   | MacroItem

VisItem:
   Visibility?
   (
         Module
      | ExternCrate
      | UseDeclaration
      | Function
      | TypeAlias
      | Struct
      | Enumeration
      | Union
      | ConstantItem
      | StaticItem
      | Trait
      | Implementation
      | ExternBlock
   )

MacroItem:
      MacroInvocationSemi
   | MacroRulesDefinition
         */


    @Test
    public void VisItem() {

        assertThat(RustGrammar.create().build().rule(RustGrammar.VIS_ITEM))
                //MODULE,


                .matches("extern crate pcre;") //extern  crate
                .matches("fn main() {\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "    abc()\n" +
                        "}") // function
                .matches("trait Seq<T> {\n" +
                        "    fn len(&self) -> u32;\n" +
                        "    fn elt_at(&self, n: u32) -> T;\n" +
                        "    fn iter<F>(&self, f: F) where F: Fn(T);\n" +
                        "}") //trait
        /* TODO
        USE_DECLARATION,
                TYPE_ALIAS,
                STRUCT,
                ENUMERATION,
                UNION,
                CONSTANT_ITEM,
                STATIC_ITEM,
                IMPLEMENTATION,
                EXTERN_BLOCK

         */
        ;
    }

    @Test
    public void MacroItem() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.MACRO_ITEM))
                .matches("j!(AS);")
                .matches("println!(\"hello\");")
                .notMatches("")
                .matches("macro_rules! foo {\n" +
                        "    ($l:tt) => { bar!($l); }\n" +
                        "}") //macro rules definition
        ;
    }


    @Test
    public void testItem() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ITEM))
                .matches("mod foo ;") //module item
                .matches("extern crate pcre;") //extern crate item
                .matches("use std::collections::hash_map::{self, HashMap};") //use item
                .matches("fn answer_to_life_the_universe_and_everything() -> i32 {\n" +
                        "    let y=42;\n" +
                        "}")//function item
                .matches("type Point = (u8, u8);") //type alias
                .matches("struct Point {x:i32, y: i32}") //struct
                .matches("enum Animal {\n" +
                        "    Dog,\n" +
                        "    Cat,\n" +
                        "}") //enumeration
                .matches("union MyUnion {\n" +
                        "    f1: u32,\n" +
                        "    f2: f32,\n" +
                        "}") //union
                .matches("const STRING: &'static str = \"bitstring\";")//const
                .matches("static mut LEVELS: u32 = 0;") //static
                .matches("trait Seq<T> {\n" +
                        "    fn len(&self) -> u32;\n" +
                        "    fn elt_at(&self, n: u32) -> T;\n" +
                        "    fn iter<F>(&self, f: F) where F: Fn(T);\n" +
                        "}") //trait
                //implementation
                .matches("impl <U> Color where 'a : 'b +'c +'d {#![inner] fn by_ref(self: &Self) {}}")
                //extern block
                .matches("extern {\n" +
                        "    pub fn draw()->Circle;\n" +
                        "    #[outer] pub fn draw()->Circle;\n" +
                        "    static fdf : f64;\n" +
                        "    }")
                .matches("j!(AS);")
                .matches("println!(\"hello\");")
                .notMatches("")
                .matches("macro_rules! foo {\n" +
                        "    ($l:tt) => { bar!($l); }\n" +
                        "}") //macro rules definition
                .matches("#[inline]\n" +
                        "    fn todo() -> String {\n" +
                        "        \"result\"\n" +
                        "    }")

        ;
    }


}