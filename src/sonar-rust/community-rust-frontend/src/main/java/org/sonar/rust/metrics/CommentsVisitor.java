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
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import java.util.HashSet;
import java.util.Set;
import org.sonar.rust.RustVisitor;

public class CommentsVisitor extends RustVisitor {

  private Set<Integer> comments;
  private boolean seenFirstToken;

  public Set<Integer> commentLines() {
    return comments;
  }

  private void addCommentLine(int line) {
    comments.add(line);
  }

  @Override
  public void visitFile(AstNode astNode) {
    comments = new HashSet<>();
    seenFirstToken = false;
  }

  @Override
  public void visitToken(Token token) {
    if (seenFirstToken) {
      for (Trivia trivia : token.getTrivia()) {
        if (trivia.isComment()) {
          String[] commentLines = getContents(trivia.getToken().getOriginalValue())
            .split("(\r)?\n|\r", -1);
          int line = trivia.getToken().getLine();

          for (String commentLine : commentLines) {
            if (!isBlank(commentLine)) {
              addCommentLine(line);
            }

            line++;
          }
        }
      }
    }

    seenFirstToken = true;
  }

  public boolean isBlank(String line) {
    for (int i = 0; i < line.length(); i++) {
      if (Character.isLetterOrDigit(line.charAt(i))) {
        return false;
      }
    }

    return true;
  }

  public String getContents(String comment) {
    int l = comment.length();

    String res = "";
    if (l > 3) {
      res = comment.substring(2, l - 2);
    }
    return res;
  }

}
