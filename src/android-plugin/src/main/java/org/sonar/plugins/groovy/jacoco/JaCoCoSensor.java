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
package org.sonar.plugins.groovy.jacoco;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Configuration;
import org.sonar.api.notifications.AnalysisWarnings;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.foundation.GroovyFileSystem;

public class JaCoCoSensor implements Sensor {

  private static final Logger LOG = Loggers.get(JaCoCoSensor.class.getName());

  public static final String JACOCO_OVERALL = "jacoco-overall.exec";

  private final JaCoCoConfiguration configuration;
  private final GroovyFileSystem fileSystem;
  private final PathResolver pathResolver;
  private final Configuration settings;
  private final AnalysisWarnings analysisWarnings;
  static final String JACOCO_XML_PROPERTY = "sonar.coverage.jacoco.xmlReportPaths";
  private static final String[] JACOCO_XML_DEFAULT_PATHS = {
    "target/site/jacoco/jacoco.xml", "build/reports/jacoco/test/jacocoTestReport.xml"
  };

  public JaCoCoSensor(
      JaCoCoConfiguration configuration,
      GroovyFileSystem fileSystem,
      PathResolver pathResolver,
      Configuration settings,
      AnalysisWarnings analysisWarnings) {
    this.configuration = configuration;
    this.fileSystem = fileSystem;
    this.pathResolver = pathResolver;
    this.settings = settings;
    this.analysisWarnings = analysisWarnings;
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name("Groovy JaCoCo Coverage").onlyOnLanguage(Groovy.KEY);
  }

  @Override
  public void execute(SensorContext context) {
    boolean hasXmlReport = hasXmlReport(context);
    File baseDir = fileSystem.baseDir();
    File reportUTs = pathResolver.relativeFile(baseDir, configuration.getReportPath());
    File reportITs = pathResolver.relativeFile(baseDir, configuration.getItReportPath());

    if (reportUTs.isFile()) {
      warnAboutDeprecatedProperty(hasXmlReport, JaCoCoConfiguration.REPORT_PATH_PROPERTY);
    }
    if (reportITs.isFile()) {
      warnAboutDeprecatedProperty(hasXmlReport, JaCoCoConfiguration.IT_REPORT_PATH_PROPERTY);
    }
    if (hasXmlReport) {
      LOG.debug(
          "JaCoCo XML report found, skipping processing of binary JaCoCo exec report.",
          JACOCO_XML_PROPERTY);
      return;
    }

    if (shouldExecuteOnProject(reportUTs.isFile(), reportITs.isFile())) {
      Path reportOverall = context.fileSystem().workDir().toPath().resolve(JACOCO_OVERALL);
      JaCoCoReportMerger.mergeReports(reportOverall, reportUTs, reportITs);
      new JaCoCoAnalyzer(fileSystem, settings, reportOverall).analyse(context);
    }
  }

  private void warnAboutDeprecatedProperty(boolean hasXmlReport, String deprecatedProperty) {
    if (!hasXmlReport) {
      addAnalysisWarning(
          "Property '%s' is deprecated (JaCoCo binary format). '%s' should be used instead (JaCoCo XML format)."
              + " Please check that the JaCoCo plugin is installed on your SonarQube Instance.",
          deprecatedProperty, JACOCO_XML_PROPERTY);
    } else if (settings.hasKey(deprecatedProperty)) {
      // only log for those properties which were set explicitly
      LOG.info(
          "Both '{}' and '{}' were set. '{}' is deprecated therefore, only '{}' will be taken into account."
              + " Please check that the JaCoCo plugin is installed on your SonarQube Instance.",
          deprecatedProperty,
          JACOCO_XML_PROPERTY,
          deprecatedProperty,
          JACOCO_XML_PROPERTY);
    }
  }

  private static boolean hasXmlReport(SensorContext context) {
    return context.config().hasKey(JACOCO_XML_PROPERTY)
        || Arrays.stream(JACOCO_XML_DEFAULT_PATHS)
            .map(path -> context.fileSystem().baseDir().toPath().resolve(path))
            .anyMatch(Files::isRegularFile);
  }

  private void addAnalysisWarning(String format, Object... args) {
    String msg = String.format(format, args);
    LOG.warn(msg);
    analysisWarnings.addUnique(msg);
  }

  // VisibleForTesting
  boolean shouldExecuteOnProject(boolean hasUT, boolean hasIT) {
    boolean foundOneReport = hasUT || hasIT;
    boolean shouldExecute = configuration.shouldExecuteOnProject(foundOneReport);
    if (!foundOneReport && shouldExecute) {
      JaCoCoExtensions.logger().info("JaCoCoSensor: No JaCoCo report found.");
    }
    return shouldExecute;
  }
}
