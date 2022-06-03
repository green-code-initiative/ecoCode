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

import javax.annotation.CheckForNull;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.utils.PathUtils;

public class FileChooser {
  private final InvertPath tree = new InvertPath();

  FileChooser(Iterable<InputFile> inputFiles) {
    inputFiles.forEach(inputFile -> {
      String[] path = inputFile.relativePath().split("/");
      tree.index(inputFile, path);
    });
  }

  @CheckForNull
  InputFile getInputFile(String filePath) {
    String sanitizedPath = PathUtils.sanitize(filePath);
    if (sanitizedPath == null) {
      return null;
    }
    String[] pathElements = sanitizedPath.split("/");
    return tree.getFileWithSuffix(pathElements);
  }
}
