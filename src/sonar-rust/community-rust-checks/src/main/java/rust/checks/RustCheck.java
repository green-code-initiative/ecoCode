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

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.sonar.rust.RustVisitor;
import org.sonar.rust.RustVisitorContext;

public class RustCheck extends RustVisitor {
  private final List<Issue> issues = new ArrayList<>();

  public List<Issue> scanFileForIssues(RustVisitorContext context) {
    issues.clear();
    scanFile(context);
    return Collections.unmodifiableList(new ArrayList<>(issues));
  }

  public void addIssue(String message, AstNode node) {
    addIssue(message, node.getToken());
  }

  public void addIssue(String message, Token token) {
    addLineIssue(message, token.getLine());
  }

  public void addLineIssue(String message, int line) {
    issues.add(Issue.lineIssue(this, line, message));
  }

  public void addFileIssue(String message) {
    issues.add(Issue.fileIssue(this, message));
  }
}
