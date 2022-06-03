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
package org.elegoff.plugins.communityrust;

import java.io.IOException;
import java.net.URI;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.rust.RustFile;

public class SonarQubeRustFile implements RustFile {
  private final InputFile inputFile;

  private SonarQubeRustFile(InputFile inputFile) {
    this.inputFile = inputFile;
  }

  static SonarQubeRustFile create(InputFile inputFile) {
    return new SonarQubeRustFile(inputFile);
  }

  @Override
  public String name() {
    return inputFile.filename();
  }

  @Override
  public URI uri() {
    return inputFile.uri();
  }

  private InputFile inputFile() {
    return inputFile;
  }

  @Override
  public String content() {
    try {
      return inputFile().contents();
    } catch (IOException e) {
      throw new IllegalStateException("Could not read content of input file " + inputFile(), e);
    }
  }
}
