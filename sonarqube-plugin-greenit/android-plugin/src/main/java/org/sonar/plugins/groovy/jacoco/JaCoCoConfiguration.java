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
package org.sonar.plugins.groovy.jacoco;

import java.util.Arrays;
import java.util.List;
import org.sonar.api.PropertyType;
import org.sonar.api.batch.ScannerSide;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Configuration;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.plugins.groovy.foundation.Groovy;

@ScannerSide
public class JaCoCoConfiguration {

  static final String REPORT_PATH_PROPERTY = "sonar.groovy.jacoco.reportPath";
  static final String REPORT_PATH_DEFAULT_VALUE = "target/jacoco.exec";
  static final String IT_REPORT_PATH_PROPERTY = "sonar.groovy.jacoco.itReportPath";
  static final String IT_REPORT_PATH_DEFAULT_VALUE = "target/jacoco-it.exec";
  static final String REPORT_MISSING_FORCE_ZERO = "sonar.groovy.jacoco.reportMissing.force.zero";
  static final String SONAR_GROOVY_BINARIES = "sonar.groovy.binaries";
  static final String SONAR_GROOVY_BINARIES_FALLBACK = "sonar.binaries";
  static final boolean REPORT_MISSING_FORCE_ZERO_DEFAULT_VALUE = false;

  private static final String SUBCATEGORY = "JaCoCo";

  private final Configuration settings;
  private final FileSystem fileSystem;

  public JaCoCoConfiguration(Configuration settings, FileSystem fileSystem) {
    this.settings = settings;
    this.fileSystem = fileSystem;
  }

  public boolean shouldExecuteOnProject(boolean reportFound) {
    return hasGroovyFiles() && (reportFound || isCoverageToZeroWhenNoReport());
  }

  private boolean hasGroovyFiles() {
    return fileSystem.hasFiles(fileSystem.predicates().hasLanguage(Groovy.KEY));
  }

  public String getReportPath() {
    return settings.get(REPORT_PATH_PROPERTY).orElseThrow(IllegalStateException::new);
  }

  public String getItReportPath() {
    return settings.get(IT_REPORT_PATH_PROPERTY).orElseThrow(IllegalStateException::new);
  }

  private boolean isCoverageToZeroWhenNoReport() {
    return settings.getBoolean(REPORT_MISSING_FORCE_ZERO).orElse(false);
  }

  public static List<PropertyDefinition> getPropertyDefinitions() {
    return Arrays.asList(
        PropertyDefinition.builder(JaCoCoConfiguration.REPORT_PATH_PROPERTY)
            .defaultValue(JaCoCoConfiguration.REPORT_PATH_DEFAULT_VALUE)
            .name("UT JaCoCo Report")
            .description(
                "Path to the JaCoCo report file containing coverage data by unit tests. The path may be absolute or relative to the project base directory.")
            .category(Groovy.NAME)
            .subCategory(SUBCATEGORY)
            .onQualifiers(Qualifiers.PROJECT)
            .build(),
        PropertyDefinition.builder(JaCoCoConfiguration.IT_REPORT_PATH_PROPERTY)
            .defaultValue(JaCoCoConfiguration.IT_REPORT_PATH_DEFAULT_VALUE)
            .name("IT JaCoCo Report")
            .description(
                "Path to the JaCoCo report file containing coverage data by integration tests. The path may be absolute or relative to the project base directory.")
            .category(Groovy.NAME)
            .subCategory(SUBCATEGORY)
            .onQualifiers(Qualifiers.PROJECT)
            .build(),
        PropertyDefinition.builder(JaCoCoConfiguration.REPORT_MISSING_FORCE_ZERO)
            .defaultValue(
                Boolean.toString(JaCoCoConfiguration.REPORT_MISSING_FORCE_ZERO_DEFAULT_VALUE))
            .name("Force zero coverage")
            .description("Force coverage to 0% if no JaCoCo reports are found during analysis.")
            .category(Groovy.NAME)
            .subCategory(SUBCATEGORY)
            .onQualifiers(Qualifiers.PROJECT)
            .type(PropertyType.BOOLEAN)
            .build(),
        PropertyDefinition.builder(SONAR_GROOVY_BINARIES)
            .name("Binary directories")
            .description(
                "Comma-separated list of optional directories that contain the compiled groovy sources.")
            .category(Groovy.NAME)
            .subCategory(SUBCATEGORY)
            .onQualifiers(Qualifiers.PROJECT)
            .multiValues(true)
            .build());
  }
}
