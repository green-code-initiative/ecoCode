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
package org.elegoff.plugins.communityrust.language;

import java.io.IOException;
import java.util.Optional;
import org.sonar.api.batch.fs.InputFile;

public class RustSourceCode {

  private final InputFile rustFile;
  private String content = null;

  /**
   * Constructor. Parses the passed file to determine if it is syntactically correct.
   *
   * @param rustFile a supposedly Rust file
   * @param filter   {@code true} to filter out UTF-8 line break characters (U+2028, U+2029 and U+0085) that may not be
   *                 correctly supported by SonarQube
   * @throws IOException if there is a problem reading the passed file
   */
  public RustSourceCode(InputFile rustFile, Optional<Boolean> filter) throws IOException {
    this.rustFile = rustFile;

  }

  /**
   * Returns the {@code InputFile} of this class.
   * <p><strong>WARNING!!!</strong> Do not use {@code getRustFile.contents()} to get the source; use {@link #getContent()}
   * instead.</p>
   *
   * @return the {@code InputFile} of this class
   * @see InputFile
   * @see #getContent()
   */
  public InputFile getRustFile() {
    return rustFile;
  }

  /**
   * Returns the content of the RUST file as a {@code String} with UTF-8 line breaks possibly removed.
   * <p><strong>WARNING!!</strong> Use this method instead of {@code InputFile.contents()} in order to have the source
   * code to be cleanup if needed.</p>
   *
   * @return the RUST content
   * @throws IOException if an error occurred reading the RUST file
   * @see #RustSourceCode(InputFile, Optional)
   */
  public String getContent() throws IOException {
    if (content == null) {
      this.content = rustFile.contents();
    }
    return content;
  }
}
