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
package org.sonar.rust;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.RecognitionException;

public class RustVisitorContext {

  private final RustFile file;
  private final AstNode rootTree;
  private final RecognitionException parsingException;

  public RustVisitorContext(RustFile file, AstNode tree) {
    this(file, tree, null);
  }

  public RustVisitorContext(RustFile file, RecognitionException parsingException) {
    this(file, null, parsingException);
  }

  private RustVisitorContext(RustFile file, AstNode rootTree, RecognitionException parsingException) {
    this.file = file;
    this.rootTree = rootTree;
    this.parsingException = parsingException;
  }

  public AstNode rootTree() {
    return rootTree;
  }

  public RustFile file() {
    return file;
  }

  public RecognitionException parsingException() {
    return parsingException;
  }

}
