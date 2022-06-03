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
package org.elegoff.plugins.communityrust.clippy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.sonar.api.batch.rule.Severity;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rules.RuleType;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.analyzer.commons.ExternalReportProvider;
import org.sonarsource.analyzer.commons.internal.json.simple.parser.ParseException;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class ClippySensor implements Sensor {

  public static final String REPORT_PROPERTY_KEY = "community.rust.clippy.reportPaths";
  static final String LINTER_KEY = "clippy";
  static final String LINTER_NAME = "Clippy";
  private static final Logger LOG = Loggers.get(ClippySensor.class);
  private static final Long DEFAULT_CONSTANT_DEBT_MINUTES = 5L;
  private static final int MAX_LOGGED_FILE_NAMES = 20;

  private static void saveIssue(SensorContext context, ClippyJsonReportReader.ClippyIssue clippyIssue, Set<String> unresolvedInputFiles) {
    if (isEmpty(clippyIssue.ruleKey) || isEmpty(clippyIssue.filePath) || isEmpty(clippyIssue.message)) {
      LOG.debug("Missing information for ruleKey:'{}', filePath:'{}', message:'{}'", clippyIssue.ruleKey, clippyIssue.filePath, clippyIssue.message);
      return;
    }

    var inputFile = context.fileSystem().inputFile(context.fileSystem().predicates().hasPath(clippyIssue.filePath));
    if (inputFile == null) {
      unresolvedInputFiles.add(clippyIssue.filePath);
      return;
    }

    var newExternalIssue = context.newExternalIssue();
    newExternalIssue
      .type(RuleType.CODE_SMELL)
      .severity(toSonarQubeSeverity(clippyIssue.severity))
      .remediationEffortMinutes(DEFAULT_CONSTANT_DEBT_MINUTES);

    NewIssueLocation primaryLocation = newExternalIssue.newLocation()
      .message(clippyIssue.message)
      .on(inputFile);

    if (clippyIssue.lineNumberStart != null) {
      primaryLocation.at(inputFile.newRange(clippyIssue.lineNumberStart, clippyIssue.colNumberStart - 1, clippyIssue.lineNumberEnd, clippyIssue.colNumberEnd - 1));
    }

    newExternalIssue.at(primaryLocation);
    newExternalIssue.engineId(LINTER_KEY).ruleId(clippyIssue.ruleKey);
    newExternalIssue.save();
  }

  private static Severity toSonarQubeSeverity(String severity) {
    if ("error".equalsIgnoreCase(severity)) {
      return Severity.MAJOR;
    } else
      return Severity.MINOR;
  }

  @Override
  public void execute(SensorContext context) {
    Set<String> unresolvedInputFiles = new HashSet<>();
    List<File> reportFiles = ExternalReportProvider.getReportFiles(context, reportPathKey());
    reportFiles.forEach(report -> importReport(report, context, unresolvedInputFiles));
    logUnresolvedInputFiles(unresolvedInputFiles);
  }

  private void importReport(File rawReport, SensorContext context, Set<String> unresolvedInputFiles) {
    LOG.info("Importing {}", rawReport);

    try {
      InputStream in = ClippyJsonReportReader.toJSON(rawReport);
      String projectDir = rawReport.getParent();
      ClippyJsonReportReader.read(in, projectDir, clippyIssue -> saveIssue(context, clippyIssue, unresolvedInputFiles));
    } catch (IOException | ParseException e) {
      LOG.error("No issues information will be saved as the report file '{}' can't be read. " +
        e.getClass().getSimpleName() + ": " + e.getMessage(), rawReport, e);
    }
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
      .onlyWhenConfiguration(conf -> conf.hasKey(reportPathKey()))
      .onlyOnLanguage(RustLanguage.KEY)
      .name("Import of " + linterName() + " issues");
  }

  private void logUnresolvedInputFiles(Set<String> unresolvedInputFiles) {
    if (unresolvedInputFiles.isEmpty()) {
      return;
    }
    String fileList = unresolvedInputFiles.stream().sorted().limit(MAX_LOGGED_FILE_NAMES).collect(Collectors.joining(";"));
    if (unresolvedInputFiles.size() > MAX_LOGGED_FILE_NAMES) {
      fileList += ";...";
    }
    logger().warn("Failed to resolve {} file path(s) in " + linterName() + " report. No issues imported related to file(s): {}", unresolvedInputFiles.size(), fileList);
  }

  protected String linterName() {
    return LINTER_NAME;
  }

  protected String reportPathKey() {
    return REPORT_PROPERTY_KEY;
  }

  protected Logger logger() {
    return LOG;
  }
}
