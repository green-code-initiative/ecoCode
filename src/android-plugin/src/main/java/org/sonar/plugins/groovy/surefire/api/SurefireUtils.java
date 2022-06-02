/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2021 SonarQube Community
 *  
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
package org.sonar.plugins.groovy.surefire.api;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public final class SurefireUtils {

  private static final Logger LOGGER = Loggers.get(SurefireUtils.class);
  /** @since 1.7 */
  public static final String SUREFIRE_REPORT_PATHS_PROPERTY = "sonar.junit.reportPaths";

  private SurefireUtils() {}

  /**
   * Find the directories containing the surefire reports.
   *
   * @param settings Analysis settings.
   * @param fs FileSystem containing indexed files.
   * @param pathResolver Path solver.
   * @return The directories containing the surefire reports or default one
   *     (target/surefire-reports) if not found (not configured or not found).
   */
  public static List<File> getReportDirectories(
      Configuration settings, FileSystem fs, PathResolver pathResolver) {
    List<File> dirs = getReportDirectoriesFromProperty(settings, fs, pathResolver);
    if (dirs.size() > 0) {
      return dirs;
    }
    return Collections.singletonList(new File(fs.baseDir(), "target/surefire-reports"));
  }

  private static List<File> getReportDirectoriesFromProperty(
      Configuration settings, FileSystem fs, PathResolver pathResolver) {
    if (settings.hasKey(SUREFIRE_REPORT_PATHS_PROPERTY)) {
      return Arrays.stream(settings.getStringArray(SUREFIRE_REPORT_PATHS_PROPERTY))
          .map(String::trim)
          .map(path -> resolvePath(pathResolver, fs.baseDir(), path))
          .filter(Objects::nonNull)
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  @CheckForNull
  private static File resolvePath(PathResolver pathResolver, File baseDir, String path) {
    try {
      return pathResolver.relativeFile(baseDir, path);
    } catch (InvalidPathException e) {
      // This probably won't happen in production SQ, catch anyways.
      LOGGER.info("Surefire report path: {}/{} not found.", baseDir, path);
    }
    return null;
  }
}
