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
package rust.checks;

import javax.annotation.CheckForNull;

public class Issue {

  private final RustCheck check;
  private final Integer line;
  private final String message;

  private Issue(RustCheck check, Integer line, String message) {
    this.check = check;
    this.line = line;
    this.message = message;
  }

  public static Issue fileIssue(RustCheck check, String message) {
    return new Issue(check, null, message);
  }

  public static Issue lineIssue(RustCheck check, int line, String message) {
    return new Issue(check, line, message);
  }

  public RustCheck check() {
    return check;
  }

  @CheckForNull
  public Integer line() {
    return line;
  }

  public String message() {
    return message;
  }
}
