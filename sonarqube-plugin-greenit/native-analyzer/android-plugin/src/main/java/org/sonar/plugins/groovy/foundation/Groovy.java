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
package org.sonar.plugins.groovy.foundation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Configuration;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.resources.Qualifiers;

public class Groovy extends AbstractLanguage {

  public static final String KEY = "grvy";
  public static final String NAME = "Groovy";
  public static final String FILE_SUFFIXES_KEY = "sonar.groovy.file.suffixes";
  static final String DEFAULT_FILE_SUFFIXES = ".groovy";

  /** Settings of the plugin. */
  private final Configuration settings;

  /** Default constructor */
  public Groovy(Configuration settings) {
    super(KEY, NAME);
    this.settings = settings;
  }

  @Override
  public String[] getFileSuffixes() {
    String[] suffixes = filterEmptyStrings(settings.getStringArray(FILE_SUFFIXES_KEY));
    if (suffixes.length == 0) {
      suffixes = StringUtils.split(DEFAULT_FILE_SUFFIXES, ",");
    }
    return addDot(suffixes);
  }

  private static String[] addDot(String[] suffixes) {
    String[] results = new String[suffixes.length];
    for (int i = 0; i < suffixes.length; i++) {
      String suffix = suffixes[i];
      String dot = suffix.startsWith(".") ? "" : ".";
      results[i] = dot + suffix;
    }
    return results;
  }

  private static String[] filterEmptyStrings(String[] stringArray) {
    List<String> nonEmptyStrings = new ArrayList<>();
    for (String string : stringArray) {
      if (StringUtils.isNotBlank(string.trim())) {
        nonEmptyStrings.add(string.trim());
      }
    }
    return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
  }

  public static List<Object> getExtensions() {
    return Arrays.asList(
        Groovy.class,
        GroovyFileSystem.class,
        PropertyDefinition.builder(FILE_SUFFIXES_KEY)
            .name("File suffixes")
            .description(
                "Comma-separated list of suffixes for files to analyze. To not filter, leave the list empty.")
            .category(Groovy.NAME)
            .subCategory("Base")
            .onQualifiers(Qualifiers.PROJECT)
            .defaultValue(DEFAULT_FILE_SUFFIXES)
            .multiValues(true)
            .build());
  }
}
