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
package org.sonar.rust.parser;

import org.junit.Test;
import org.sonar.rust.RustGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class RustGrammarTest {
    @Test
    public void matchingEmpty() {
        LexerlessGrammar g = RustGrammar.create().build();
        RustGrammar[] rustGrammars = RustGrammar.values();

        Set<RustGrammar> couldMatch = new HashSet<RustGrammar>(Arrays.asList(
                // RustGrammar.CALL_PARAMS_TERM,
                RustGrammar.COMPILATION_UNIT,
                RustGrammar.EOF,
                RustGrammar.FUNCTION_QUALIFIERS,
                RustGrammar.FUNCTION_TYPE_QUALIFIERS,
                RustGrammar.LIFETIME_BOUNDS,
                RustGrammar.SPC
        ));

        for (RustGrammar r : rustGrammars) {
            if (couldMatch.contains(r)) {
                assertThat(RustGrammar.create().build().rule(r))
                        .matches("");
            } else {
                assertThat(RustGrammar.create().build().rule(r))
                        .notMatches("");
            }


        }

    }

    @Test
    public void minimal() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.DELIMITERS)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.CHAR_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.BYTE_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.BYTE_STRING_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.INTEGER_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.FLOAT_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.BOOLEAN_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.STRING_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.RAW_STRING_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.HEX_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.OCT_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.RAW_BYTE_STRING_LITERAL)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.IDENTIFIER)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.KEYWORD)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.ANY_TOKEN)).notMatches("");
        assertThat(RustGrammar.create().build().rule(RustGrammar.STATEMENT)).notMatches("");
        //semi colon
        assertThat(RustGrammar.create().build().rule(RustGrammar.ANY_TOKEN)).notMatches(";");
        assertThat(RustGrammar.create().build().rule(RustGrammar.STATEMENT)).matches(";");
    }


}
