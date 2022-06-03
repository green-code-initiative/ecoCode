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
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;
import java.util.List;
import java.util.Set;

public class RustVisitor {

  private RustVisitorContext context;

  public Set<AstNodeType> subscribedKinds() {
    return Set.of();
  }

  public void visitFile(AstNode node) {
    // Do nothing
  }

  public void leaveFile(AstNode node) {
    // Do nothing
  }

  public void visitNode(AstNode node) {
    // Do nothing
  }

  public void visitToken(Token token) {
    // Do nothing
  }

  public void leaveNode(AstNode node) {
    // Do nothing
  }

  public RustVisitorContext getContext() {
    return context;
  }

  // for testing purpose only
  public void setContext(RustVisitorContext context) {
    this.context = context;
  }

  public void scanFile(RustVisitorContext context) {
    this.context = context;
    AstNode tree = context.rootTree();
    visitFile(tree);
    if (tree != null) {
      scanNode(tree, subscribedKinds());
    }
    leaveFile(tree);
  }

  private void scanNode(AstNode node, Set<AstNodeType> subscribedKinds) {
    boolean isSubscribedType = subscribedKinds.contains(node.getType());

    if (isSubscribedType) {
      visitNode(node);
    }

    List<AstNode> children = node.getChildren();
    if (children.isEmpty()) {
      for (Token token : node.getTokens()) {
        visitToken(token);
      }
    } else {
      for (AstNode child : children) {
        scanNode(child, subscribedKinds);
      }
    }

    if (isSubscribedType) {
      leaveNode(node);
    }
  }

}
