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
package org.sonar.rust.metrics;

import com.sonar.sslr.api.AstNode;
import org.junit.Test;
import org.sonar.rust.RustFile;
import org.sonar.rust.RustGrammar;
import org.sonar.rust.RustVisitorContext;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class LinesOfCodeVisitorTest {


    @Test
    public void testVisit(){

        ParserAdapter<LexerlessGrammar> parser = new ParserAdapter<>(StandardCharsets.UTF_8, RustGrammar.create().build());
        AstNode rootNode = parser.parse("");
        LinesOfCodeVisitor lcv = new LinesOfCodeVisitor(parser);
        RustFile source = new RustFile() {
            @Override
            public String name() {
                return null;
            }

            @Override
            public String content() {
                return "";
            }

            @Override
            public URI uri() {
                return null;
            }
        };
        RustVisitorContext context = new RustVisitorContext(source, rootNode);
       lcv.setContext(context);
        lcv.visitFile(rootNode);
        Set<Integer> lines = lcv.linesOfCode();

        assertThat(lines).hasSize(0);


    }

}
