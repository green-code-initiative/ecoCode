/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2021 SonarQube Community
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
package org.sonar.plugins.groovy.codenarc;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.codenarc.CodeNarcRunner;
import org.codenarc.rule.Violation;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.plugins.groovy.codenarc.CodeNarcXMLParser.CodeNarcViolation;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.foundation.GroovyFileSystem;

public class CodeNarcSensor implements Sensor {

  @Deprecated static final String CODENARC_REPORT_PATH = "sonar.groovy.codenarc.reportPath";
  static final String CODENARC_REPORT_PATHS = "sonar.groovy.codenarc.reportPaths";

  private static final Logger LOG = Loggers.get(CodeNarcSensor.class);

  private final ActiveRules activeRules;
  private final GroovyFileSystem groovyFileSystem;

  public CodeNarcSensor(ActiveRules activeRules, GroovyFileSystem groovyFileSystem) {
    this.activeRules = activeRules;
    this.groovyFileSystem = groovyFileSystem;
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
        .name("CodeNarc")
        .onlyOnLanguage(Groovy.KEY)
        .createIssuesForRuleRepositories(CodeNarcRulesDefinition.REPOSITORY_KEY);
  }

  @Override
  public void execute(SensorContext context) {
    // Should we reuse existing report from CodeNarc ?
    if (context.config().hasKey(CODENARC_REPORT_PATHS)) {
      // Yes
      String[] codeNarcReportPaths = context.config().getStringArray(CODENARC_REPORT_PATHS);
      if (codeNarcReportPaths.length == 0) {
        Optional<String> legacyOption = context.config().get(CODENARC_REPORT_PATH);
        if (legacyOption.isPresent()) {
          codeNarcReportPaths = new String[] {legacyOption.get()};
        }
      }
      List<File> reports = new ArrayList<>();
      for (String path : codeNarcReportPaths) {
        File report = context.fileSystem().resolvePath(path);
        if (!report.isFile() || !report.exists()) {
          LOG.warn("Groovy report " + CODENARC_REPORT_PATHS + " not found at {}", report);
        } else {
          reports.add(report);
        }
      }
      if (!reports.isEmpty()) {
        parseReport(context, reports);
      }
    } else {
      // No, run CodeNarc
      runCodeNarc(context);
    }
  }

  private void parseReport(SensorContext context, List<File> reports) {
    for (File report : reports) {
      Collection<CodeNarcViolation> violations =
          CodeNarcXMLParser.parse(report, context.fileSystem());
      for (CodeNarcViolation violation : violations) {
        ActiveRule activeRule =
            context
                .activeRules()
                .findByInternalKey(CodeNarcRulesDefinition.REPOSITORY_KEY, violation.getRuleName());
        if (activeRule != null) {
          InputFile inputFile = inputFileFor(context, violation.getFilename());
          insertIssue(context, violation, activeRule.ruleKey(), inputFile);
        } else {
          LOG.warn(
              "No such rule in SonarQube, so violation from CodeNarc will be ignored: {}",
              violation.getRuleName());
        }
      }
    }
  }

  private static void insertIssue(
      SensorContext context,
      CodeNarcViolation violation,
      RuleKey ruleKey,
      @Nullable InputFile inputFile) {
    insertIssue(context, ruleKey, violation.getLine(), violation.getMessage(), inputFile);
  }

  private static void insertIssue(
      SensorContext context,
      RuleKey ruleKey,
      @Nullable Integer lineNumber,
      @Nullable String message,
      @Nullable InputFile inputFile) {
    if (inputFile != null) {
      NewIssue newIssue = context.newIssue().forRule(ruleKey);
      NewIssueLocation location = newIssue.newLocation().on(inputFile);
      if (lineNumber != null && lineNumber > 0) {
        location = location.at(inputFile.selectLine(lineNumber));
      }
      if (message != null) {
        location = location.message(message);
      }
      newIssue.at(location).save();
    }
  }

  private void runCodeNarc(SensorContext context) {
    LOG.info("Executing CodeNarc");

    File workdir = new File(context.fileSystem().workDir(), "codenarc");
    prepareWorkDir(workdir);
    File codeNarcConfiguration = new File(workdir, "profile.xml");
    exportCodeNarcConfiguration(codeNarcConfiguration);

    CodeNarcRunner runner = new CodeNarcRunner();
    runner.setRuleSetFiles("file:" + codeNarcConfiguration.getAbsolutePath());

    CodeNarcSourceAnalyzer analyzer =
        new CodeNarcSourceAnalyzer(groovyFileSystem.sourceInputFiles());
    runner.setSourceAnalyzer(analyzer);
    runner.execute();
    reportViolations(context, analyzer.getViolationsByFile());
  }

  private void reportViolations(
      SensorContext context, Map<InputFile, List<Violation>> violationsByFile) {
    for (Entry<InputFile, List<Violation>> violationsOnFile : violationsByFile.entrySet()) {
      InputFile groovyFile = violationsOnFile.getKey();
      if (groovyFile == null) {
        continue;
      }
      for (Violation violation : violationsOnFile.getValue()) {
        String ruleKey = violation.getRule().getName();
        ActiveRule activeRule =
            context
                .activeRules()
                .findByInternalKey(CodeNarcRulesDefinition.REPOSITORY_KEY, ruleKey);
        if (activeRule != null) {
          insertIssue(
              context,
              activeRule.ruleKey(),
              violation.getLineNumber(),
              violation.getMessage(),
              groovyFile);
        } else {
          LOG.warn(
              "No such rule in SonarQube, so violation from CodeNarc will be ignored: {}", ruleKey);
        }
      }
    }
  }

  @CheckForNull
  private InputFile inputFileFor(SensorContext context, String path) {
    return context.fileSystem().inputFile(context.fileSystem().predicates().hasAbsolutePath(path));
  }

  private void exportCodeNarcConfiguration(File file) {
    try {
      StringWriter writer = new StringWriter();
      new CodeNarcProfileExporter(writer).exportProfile(activeRules);
      FileUtils.writeStringToFile(file, writer.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Can not generate CodeNarc configuration file", e);
    }
  }

  private static void prepareWorkDir(File dir) {
    try {
      FileUtils.forceMkdir(dir);
      // directory is cleaned, because Sonar 3.0 will not do this for us
      FileUtils.cleanDirectory(dir);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot create directory: " + dir, e);
    }
  }

  public static List<Object> getExtensions() {
    return Arrays.asList(
        CodeNarcRulesDefinition.class,
        CodeNarcSensor.class,
        PropertyDefinition.builder(CODENARC_REPORT_PATHS)
            .name("CodeNarc Reports")
            .description(
                "Path to the CodeNarc XML reports. Paths may be absolute or relative to the project base directory.")
            .category(Groovy.NAME)
            .subCategory("CodeNarc")
            .onQualifiers(Qualifiers.PROJECT)
            .multiValues(true)
            .deprecatedKey(CODENARC_REPORT_PATH)
            .build());
  }
}
