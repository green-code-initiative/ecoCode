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

public class EnumerationTest {


    @Test
    public void testEnumerationItem() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ENUM_ITEM))
                .matches("Dog")

                .matches("#[allow(missing_docs)]\n" +
                        "    CubicBezier {\n" +
                        "        x1: Number,\n" +
                        "        y1: Number,\n" +
                        "        x2: Number,\n" +
                        "        y2: Number,\n" +
                        "    }")
                .matches("#[allow(missing_docs)]#[css(comma, function)]\n" +
                        "    CubicBezier {\n" +
                        "        x1: Number,\n" +
                        "        y1: Number,\n" +
                        "        x2: Number,\n" +
                        "        y2: Number,\n" +
                        "    }")
                .matches("#[allow(missing_docs)]\n" +
                        "    #[css(comma, function)]\n" +
                        "    CubicBezier {\n" +
                        "        x1: Number,\n" +
                        "        y1: Number,\n" +
                        "        x2: Number,\n" +
                        "        y2: Number,\n" +
                        "    }")



        ;
    }

    @Test
    public void testEnumeration() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.ENUMERATION))
                .matches("enum Empty {}")
                .matches("enum Animal {\n" +
                        "    Dog,\n" +
                        "    Cat,\n" +
                        "}")
                .matches("enum TimingFunction<Integer, Number> {\n" +
                        "    /// `linear | ease | ease-in | ease-out | ease-in-out`\n" +
                        "    Keyword(TimingKeyword),\n" +
                        "    /// `cubic-bezier(<number>, <number>, <number>, <number>)`\n" +
                        "    #[allow(missing_docs)]\n" +
                        "    #[css(comma, function)]\n" +
                        "    CubicBezier {\n" +
                        "        x1: Number,\n" +
                        "        y1: Number,\n" +
                        "        x2: Number,\n" +
                        "        y2: Number,\n" +
                        "    },\n" +
                        "    /// `step-start | step-end | steps(<integer>, [ <step-position> ]?)`\n" +
                        "    /// `<step-position> = jump-start | jump-end | jump-none | jump-both | start | end`\n" +
                        "    #[css(comma, function)]\n" +
                        "    #[value_info(other_values = \"step-start,step-end\")]\n" +
                        "    Steps(Integer, #[css(skip_if = \"is_end\")] StepPosition),\n" +
                        "}")


                .matches("enum TimingFunction<Integer, Number> {\n" +
                        "    /// `linear | ease | ease-in | ease-out | ease-in-out`\n" +
                        "    Keyword(TimingKeyword),\n" +
                        "    /// `cubic-bezier(<number>, <number>, <number>, <number>)`\n" +
                        "    #[allow(missing_docs)]\n" +
                        "    #[css(comma, function)]\n" +
                        "    CubicBezier {\n" +
                        "        x1: Number,\n" +
                        "        y1: Number,\n" +
                        "        x2: Number,\n" +
                        "        y2: Number,\n" +
                        "    },\n" +
                        "    /// `step-start | step-end | steps(<integer>, [ <step-position> ]?)`\n" +
                        "    /// `<step-position> = jump-start | jump-end | jump-none | jump-both | start | end`\n" +
                        "    #[css(comma, function)]\n" +
                        "    #[value_info(other_values = \"step-start,step-end\")]\n" +
                        "    Steps(Integer, #[css(skip_if = \"is_end\")] StepPosition),\n" +
                        "}")


        ;

    }

}
