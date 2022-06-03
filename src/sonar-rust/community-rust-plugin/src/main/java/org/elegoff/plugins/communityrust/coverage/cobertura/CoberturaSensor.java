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
package org.elegoff.plugins.communityrust.coverage.cobertura;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.stream.XMLStreamException;
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

public class CoberturaSensor implements Sensor {

  private static final Logger LOG = Loggers.get(CoberturaSensor.class);

  private static Set<File> deduplicate(List<File> reports) {
    return reports.stream()
      .map(File::getAbsoluteFile)
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static List<File> fetchReports(String baseDir, Configuration config) {
    if (config.hasKey(CommunityRustPlugin.COBERTURA_REPORT_PATHS)) {
      return Arrays.stream(config.getStringArray(CommunityRustPlugin.COBERTURA_REPORT_PATHS))
        .flatMap(path -> getIncludedFiles(config, baseDir, CommunityRustPlugin.COBERTURA_REPORT_PATHS, path).stream())
        .collect(Collectors.toList());
    }
    return getIncludedFiles(config, baseDir, CommunityRustPlugin.COBERTURA_REPORT_PATHS, CommunityRustPlugin.DEFAULT_COBERTURA_REPORT_PATHS);

  }

  private static Map<InputFile, NewCoverage> importReport(File report, SensorContext sensorContext) {
    Map<InputFile, NewCoverage> coverageMeasures = new HashMap<>();
    try {
      var parser = new CoberturaParser();
      parser.importReport(report, sensorContext, coverageMeasures);
    } catch (CoberturaException e) {
      LOG.warn("Ignoring report '{}' which seems to be empty. '{}'", report, e);
    } catch (XMLStreamException e) {
      throw new IllegalStateException("Failed to import report '" + report + "'", e);
    }
    return coverageMeasures;
  }

  private static void saveCoverageMeasures(Map<InputFile, NewCoverage> coverageMeasures, HashSet<InputFile> coveredFiles) {
    coverageMeasures.forEach((inputFile, value) -> {
      coveredFiles.add(inputFile);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Saving coverage measures for file '{}'", inputFile.toString());
      }
      value.save();
    });
  }

  public static List<File> getIncludedFiles(Configuration config, String baseDirPath, String reportPathPropertyKey, String reportPath) {
    LOG.debug("Using pattern '{}' to find reports", reportPath);

    var rustFileSystem = new RustFileSystem(new File(baseDirPath), WildcardPattern.create(reportPath));
    List<File> includedFiles = rustFileSystem.getIncludedFiles();

    if (includedFiles.isEmpty()) {
      if (config.hasKey(reportPathPropertyKey)) {
        var file = new File(reportPath);
        if (file.exists()) {
          includedFiles.add(file);
        } else {
          LOG.warn("No report was found for {} using pattern {}", reportPathPropertyKey, reportPath);
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
      .name("Cobertura Sensor for Rust")
      .onlyOnLanguage(RustLanguage.KEY);
  }

  @Override
  public void execute(SensorContext context) {
    String baseDir = context.fileSystem().baseDir().getPath();
    Configuration config = context.config();

    var filesCovered = new HashSet<InputFile>();
    List<File> reports = fetchReports(baseDir, config);
    if (!reports.isEmpty()) {
      LOG.info("Rust cobertura coverage");
      for (File report : deduplicate(reports)) {
        Map<InputFile, NewCoverage> coverageMeasures = importReport(report, context);
        saveCoverageMeasures(coverageMeasures, filesCovered);
      }
    }
  }

}
