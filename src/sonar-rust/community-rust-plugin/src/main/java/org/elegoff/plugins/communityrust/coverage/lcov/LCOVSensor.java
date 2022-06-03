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

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import org.elegoff.plugins.communityrust.CommunityRustPlugin;
import org.elegoff.plugins.communityrust.coverage.RustFileSystem;
import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.coverage.NewCoverage;
import org.sonar.api.config.Configuration;
import org.sonar.api.utils.WildcardPattern;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class LCOVSensor implements Sensor {
  private static final Logger LOG = Loggers.get(LCOVSensor.class);

  private static void saveCoverageFromLcovFiles(SensorContext context, List<File> lcovFiles) {
    LOG.info("Importing {}", lcovFiles);

    var fileSystem = context.fileSystem();
    var mainFilePredicate = fileSystem.predicates().hasLanguages(RustLanguage.KEY);
    var fileChooser = new FileChooser(fileSystem.inputFiles(mainFilePredicate));
    var parser = LCOVParser.build(context, lcovFiles, fileChooser);

    Map<InputFile, NewCoverage> coveredFiles = parser.getFileCoverage();

    for (Iterator<InputFile> iterator = fileSystem.inputFiles(mainFilePredicate).iterator(); iterator.hasNext();) {
      var inputFile = iterator.next();
      NewCoverage fileCoverage = coveredFiles.get(inputFile);

      if (fileCoverage != null) {
        fileCoverage.save();
      }
    }

    List<String> unresolvedPaths = parser.unknownPaths();
    if (!unresolvedPaths.isEmpty()) {
      LOG.warn(String.format("Could not resolve %d file paths in %s", unresolvedPaths.size(), lcovFiles));
      if (LOG.isDebugEnabled()) {
        LOG.debug("Unresolved paths:\n" + String.join("\n", unresolvedPaths));
      } else {
        LOG.warn("First unresolved path: " + unresolvedPaths.get(0) + " (Run in DEBUG mode to get full list of unresolved paths)");
      }
    }

    int pbCount = parser.pbCount();
    if (pbCount > 0) {
      LOG.warn("Found {} inconsistencies in coverage report", pbCount);
    }
  }

  @CheckForNull
  private static File getFile(File baseDir, String path) {
    var file = new File(path);
    if (!file.isAbsolute()) {
      file = new File(baseDir, path);
    }
    if (!file.isFile()) {
      LOG.warn("No coverage information will be saved because LCOV file cannot be found.");
      LOG.warn("Provided LCOV file path: {}. Seek file with path: {}", path, file.getAbsolutePath());
      return null;
    }
    return file;
  }

  private static List<File> getLcovReports(String baseDir, Configuration config) {
    if (!config.hasKey(CommunityRustPlugin.LCOV_REPORT_PATHS)) {
      return getReports(config, baseDir, CommunityRustPlugin.LCOV_REPORT_PATHS, CommunityRustPlugin.DEFAULT_LCOV_REPORT_PATHS);
    }

    return Arrays.stream(config.getStringArray(CommunityRustPlugin.LCOV_REPORT_PATHS))
      .flatMap(path -> getReports(config, baseDir, CommunityRustPlugin.LCOV_REPORT_PATHS, path).stream())
      .collect(Collectors.toList());
  }

  public static List<File> getReports(Configuration conf, String baseDirPath, String reportPathPropertyKey, String reportPath) {
    LOG.debug("Using pattern '{}' to find reports", reportPath);

    var rustFileSystem = new RustFileSystem(new File(baseDirPath), WildcardPattern.create(reportPath));
    List<File> includedFiles = rustFileSystem.getIncludedFiles();

    if (includedFiles.isEmpty()) {
      if (conf.hasKey(reportPathPropertyKey)) {
        var file = new File(reportPath);
        if (!file.exists()) {
          LOG.warn("No report was found for {} using pattern {}", reportPathPropertyKey, reportPath);
        } else {
          includedFiles.add(file);
        }
      } else {
        LOG.debug("No report was found for {} using default pattern {}", reportPathPropertyKey, reportPath);
      }
    }
    return includedFiles;
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
      .name("LCOV Sensor for Rust coverage")
      .onlyOnLanguage(RustLanguage.KEY);
  }

  @Override
  public void execute(SensorContext context) {
    String baseDir = context.fileSystem().baseDir().getPath();
    List<File> lcovFiles = getLcovReports(baseDir, context.config());

    if (lcovFiles.isEmpty()) {
      LOG.warn("No coverage information will be saved because all LCOV files cannot be found.");
      return;
    }
    saveCoverageFromLcovFiles(context, lcovFiles);
  }

}
