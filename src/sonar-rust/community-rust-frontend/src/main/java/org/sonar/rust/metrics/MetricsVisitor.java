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

import java.util.Set;
import org.sonar.rust.RustGrammar;
import org.sonar.rust.RustLexer;
import org.sonar.rust.RustParserConfiguration;
import org.sonar.rust.RustVisitorContext;

public class MetricsVisitor {

  private final LinesOfCodeVisitor linesOfCodeVisitor;
  private final CommentsVisitor commentsVisitor;
  private final ComplexityVisitor complexityVisitor;
  private int numberOfStatements;
  private int numberOfFunctions;

  public MetricsVisitor(RustParserConfiguration conf) {
    linesOfCodeVisitor = new LinesOfCodeVisitor(RustLexer.create(conf));
    commentsVisitor = new CommentsVisitor();
    complexityVisitor = new ComplexityVisitor();
  }

  public void scanFile(RustVisitorContext context) {
    linesOfCodeVisitor.scanFile(context);
    commentsVisitor.scanFile(context);
    complexityVisitor.scanFile(context);
    numberOfStatements = context.rootTree().getDescendants(RustGrammar.STATEMENT).size();
    numberOfFunctions = context.rootTree().getDescendants(RustGrammar.FUNCTION).size();
  }

  public Set<Integer> linesOfCode() {
    return linesOfCodeVisitor.linesOfCode();
  }

  public Set<Integer> commentLines() {
    return commentsVisitor.commentLines();
  }

  public int numberOfStatements() {
    return numberOfStatements;
  }

  public int numberOfFunctions() {
    return numberOfFunctions;
  }

  public int complexity() {
    return complexityVisitor.complexity();
  }

}
