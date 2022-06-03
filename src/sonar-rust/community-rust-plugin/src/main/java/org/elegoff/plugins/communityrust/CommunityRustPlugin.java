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
package org.elegoff.plugins.communityrust;

import org.elegoff.plugins.communityrust.clippy.ClippyRulesDefinition;
import org.elegoff.plugins.communityrust.clippy.ClippySensor;
import org.elegoff.plugins.communityrust.coverage.cobertura.CoberturaSensor;
import org.elegoff.plugins.communityrust.coverage.lcov.LCOVSensor;
import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.elegoff.plugins.communityrust.language.RustQualityProfile;
import org.elegoff.plugins.communityrust.rules.RustRulesDefinition;
import org.elegoff.plugins.communityrust.settings.RustLanguageSettings;
import org.elegoff.plugins.communityrust.xunit.XUnitSensor;
import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class CommunityRustPlugin implements Plugin {

  public static final String LCOV_REPORT_PATHS = "community.rust.lcov.reportPaths";
  public static final String DEFAULT_LCOV_REPORT_PATHS = "lcov.info";
  public static final String COBERTURA_REPORT_PATHS = "community.rust.cobertura.reportPaths";
  public static final String DEFAULT_COBERTURA_REPORT_PATHS = "cobertura.xml";
  public static final String UNIT_TEST_ATTRIBUTES = "community.rust.unittests.attributes";
  public static final String IGNORE_DUPLICATION_FOR_TESTS = "community.rust.cpd.ignoretests";
  public static final String TEST_AND_COVERAGE = "Test and Coverage";
  public static final String DEFAULT_UNIT_TEST_ATTRIBUTES = "test,tokio::test";
  private static final String EXTERNAL_ANALYZERS_CATEGORY = "External Analyzers";
  private static final String RUST_SUBCATEGORY = "Rust";

  @Override
  public void define(Context context) {
    context.addExtension(RustLanguage.class);
    context.addExtension(RustQualityProfile.class);

    // Add plugin settings (file extensions, etc.)
    context.addExtensions(RustLanguageSettings.getProperties());
    context.addExtensions(RustRulesDefinition.class, RustSensor.class);

    // clippy rules
    context.addExtension(ClippySensor.class);
    context.addExtensions(
      PropertyDefinition.builder(ClippySensor.REPORT_PROPERTY_KEY)
        .name("Clippy Report Files")
        .description("Paths (absolute or relative) to json files with Clippy issues.")
        .category(EXTERNAL_ANALYZERS_CATEGORY)
        .subCategory(RUST_SUBCATEGORY)
        .onQualifiers(Qualifiers.PROJECT)
        .multiValues(true)
        .build(),
      ClippyRulesDefinition.class,

      LCOVSensor.class,
      PropertyDefinition.builder(LCOV_REPORT_PATHS)
        .defaultValue(DEFAULT_LCOV_REPORT_PATHS)
        .name("LCOV Files")
        .description("Paths (absolute or relative) to the files with LCOV data.")
        .onQualifiers(Qualifiers.PROJECT)
        .subCategory(TEST_AND_COVERAGE)
        .category("Rust")
        .multiValues(true)
        .build(),

      CoberturaSensor.class,
      PropertyDefinition.builder(COBERTURA_REPORT_PATHS)
        .defaultValue(DEFAULT_COBERTURA_REPORT_PATHS)
        .name("LCOV Files")
        .description("Paths (absolute or relative) to the files with LCOV data.")
        .onQualifiers(Qualifiers.PROJECT)
        .subCategory(TEST_AND_COVERAGE)
        .category("Rust")
        .multiValues(true)
        .build(),

      PropertyDefinition.builder(UNIT_TEST_ATTRIBUTES)
        .defaultValue(DEFAULT_UNIT_TEST_ATTRIBUTES)
        .name("Unit tests")
        .description("Comma separated list of Rust attributes for Unit Tests")
        .onQualifiers(Qualifiers.PROJECT)
        .subCategory(TEST_AND_COVERAGE)
        .category("Rust")
        .multiValues(true)
        .build(),
      PropertyDefinition.builder(IGNORE_DUPLICATION_FOR_TESTS)
        .defaultValue(Boolean.toString(false))
        .name("Duplications on Unit tests")
        .description("If true, CPD ignores functions identified as unit tests (see " + UNIT_TEST_ATTRIBUTES + ")")
        .onQualifiers(Qualifiers.PROJECT)
        .subCategory(TEST_AND_COVERAGE)
        .category("Rust")
        .type(PropertyType.BOOLEAN)
        .build());

    context.addExtensions(
      PropertyDefinition.builder(XUnitSensor.REPORT_PATH_KEY)
        .name("Path to xunit report(s)")
        .description("Path to the report of test execution, relative to project's root. Ant patterns are accepted. The reports have to conform to the junitreport XML format.")
        .category("Rust")
        .subCategory(TEST_AND_COVERAGE)
        .onQualifiers(Qualifiers.PROJECT)
        .defaultValue(XUnitSensor.DEFAULT_REPORT_PATH)
        .build(),
      XUnitSensor.class);

  }
}
