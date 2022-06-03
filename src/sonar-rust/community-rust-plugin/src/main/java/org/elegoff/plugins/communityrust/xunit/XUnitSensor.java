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
package org.elegoff.plugins.communityrust.xunit;

import java.io.File;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Configuration;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.analyzer.commons.FileProvider;

public class XUnitSensor implements Sensor {
  public static final String REPORT_PATH_KEY = "community.rust.test.reportPath";
  public static final String DEFAULT_REPORT_PATH = "rust-test.xml";
  private static final Logger LOG = Loggers.get(XUnitSensor.class);

  public static List<File> getReports(Configuration conf, String baseDirPath, String reportPathPropertyKey, String reportPath) {
    LOG.debug("Using pattern '{}' to find reports", reportPath);

    FileProvider provider = new FileProvider(new File(baseDirPath), reportPath);
    List<File> matchingFiles = provider.getMatchingFiles();

    if (matchingFiles.isEmpty()) {
      if (conf.hasKey(reportPathPropertyKey)) {
        // try absolute path
        File file = new File(reportPath);
        if (!file.exists()) {
          LOG.warn("No report was found for {} using pattern {}", reportPathPropertyKey, reportPath);
        } else {
          matchingFiles.add(file);
        }
      } else {
        LOG.debug("No report was found for {} using default pattern {}", reportPathPropertyKey, reportPath);
      }
    }
    return matchingFiles;
  }

  private static void saveMeasure(SensorContext context, InputComponent component, Metric<Integer> metric, int value) {
    context.<Integer>newMeasure()
      .on(component)
      .forMetric(metric)
      .withValue(value)
      .save();
  }

  private static void saveMeasure(SensorContext context, InputComponent component, Metric<Long> metric, long value) {
    context.<Long>newMeasure()
      .on(component)
      .forMetric(metric)
      .withValue(value)
      .save();
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
      .name("XUnit Sensor for Rust")
      .onlyOnLanguage(RustLanguage.KEY)
      .onlyOnFileType(InputFile.Type.MAIN);
  }

  @Override
  public void execute(SensorContext context) {
    Configuration conf = context.config();
    String reportPathPropertyKey = REPORT_PATH_KEY;
    String reportPath = conf.get(reportPathPropertyKey).orElse(DEFAULT_REPORT_PATH);

    try {
      List<File> reports = getReports(conf, context.fileSystem().baseDir().getPath(), reportPathPropertyKey, reportPath);
      processReports(context, reports);
    } catch (Exception e) {
      LOG.warn("Cannot read report '{}', the following exception occurred: {}", reportPath, e.getMessage());
    }
  }

  private void processReports(SensorContext context, List<File> reports) throws XMLStreamException {
    TestSuiteParser parserHandler = new TestSuiteParser();
    StaxParser parser = new StaxParser(parserHandler);
    for (File report : reports) {
      parser.parse(report);
    }
    TestResult total = new TestResult();
    parserHandler.getParsedReports().forEach(testSuite -> testSuite.getTestCases().forEach(total::addTestCase));

    if (total.getTests() > 0) {
      InputComponent module = context.project();
      saveMeasure(context, module, CoreMetrics.TESTS, total.getExecutedTests());
      saveMeasure(context, module, CoreMetrics.SKIPPED_TESTS, total.getSkipped());
      saveMeasure(context, module, CoreMetrics.TEST_ERRORS, total.getErrors());
      saveMeasure(context, module, CoreMetrics.TEST_FAILURES, total.getFailures());
      saveMeasure(context, module, CoreMetrics.TEST_EXECUTION_TIME, total.getTime());
    }

  }

}
