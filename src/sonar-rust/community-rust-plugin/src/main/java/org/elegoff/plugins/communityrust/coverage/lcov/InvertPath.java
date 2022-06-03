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
package org.elegoff.plugins.communityrust.coverage.lcov;

import java.util.LinkedHashMap;
import java.util.Map;
import org.sonar.api.batch.fs.InputFile;

public class InvertPath {
  private final Node root = new Node();

  private static InputFile getFirstLeaf(Node node) {
    while (!node.children.isEmpty()) {
      node = node.children.values().iterator().next();
    }
    return node.file;
  }

  void index(InputFile inputFile, String[] path) {
    var currentNode = root;
    for (int i = path.length - 1; i >= 0; i--) {
      currentNode = currentNode.children.computeIfAbsent(path[i], e -> new Node());
    }
    currentNode.file = inputFile;
  }

  InputFile getFileWithSuffix(String[] path) {
    var currentNode = root;

    for (int i = path.length - 1; i >= 0; i--) {
      currentNode = currentNode.children.get(path[i]);
      if (currentNode == null) {
        return null;
      }
    }
    return getFirstLeaf(currentNode);
  }

  static class Node {
    final Map<String, Node> children = new LinkedHashMap<>();
    InputFile file = null;
  }
}
