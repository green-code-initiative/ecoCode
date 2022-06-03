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
package org.elegoff.plugins.communityrust.settings;

import java.util.List;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import static java.util.Arrays.asList;

/**
 * Wrapper class for the class settings
 */
public class RustLanguageSettings {
  public static final String FILE_SUFFIXES_KEY = "sonar.rust.file.suffixes";
  public static final String FILTER_UTF8_LB_KEY = "sonar.rust.filter.utf8_lb";
  public static final String FILE_SUFFIXES_DEFAULT_VALUE = ".rs";

  /**
   * Hide constructor
   */
  private RustLanguageSettings() {
  }

  /**
   * Returns the configuration properties of the plugin
   *
   * @return the configuration properties of the plugin
   */
  public static List<PropertyDefinition> getProperties() {
    return asList(
      PropertyDefinition.builder(FILE_SUFFIXES_KEY)
        .name("File Suffixes")
        .description("Comma-separated list of suffixes for files to analyze.")
        .defaultValue(FILE_SUFFIXES_DEFAULT_VALUE)
        .multiValues(true)
        .category("Rust")
        .onQualifiers(Qualifiers.PROJECT)
        .build(),
      PropertyDefinition.builder(FILTER_UTF8_LB_KEY)
        .name("Filter UTF-8 Line Breaks")
        .description("Tells if UTF-8 line breaks (U+2028, U+2029 and U+0085) that may not be correctly supported by SonarQube are filtered out from the RUST code.")
        .type(PropertyType.BOOLEAN)
        .defaultValue("false")
        .category("Rust")
        .onQualifiers(Qualifiers.PROJECT)
        .build());
  }
}
