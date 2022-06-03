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
package org.elegoff.plugins.communityrust.language;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.elegoff.plugins.communityrust.settings.RustLanguageSettings;
import org.sonar.api.config.Configuration;
import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

/**
 * This class defines the Rust language.
 */
public final class RustLanguage extends AbstractLanguage {

  public static final String NAME = "Rust";
  public static final String KEY = "rust";
  private static final Logger LOGGER = Loggers.get(RustLanguage.class);
  private final Configuration config;

  public RustLanguage(Configuration config) {
    super(KEY, NAME);
    this.config = config;
  }

  @Override
  public String[] getFileSuffixes() {
    String[] suffixes = filterEmptyStrings(config.getStringArray(RustLanguageSettings.FILE_SUFFIXES_KEY));
    if (suffixes.length == 0) {
      suffixes = StringUtils.split(RustLanguageSettings.FILE_SUFFIXES_DEFAULT_VALUE, ",");
    }
    LOGGER.debug("Rust language file suffixes " + suffixes.length + " => " + suffixes[0]);
    return suffixes;
  }

  private String[] filterEmptyStrings(String[] stringArray) {
    List<String> nonEmptyStrings = new ArrayList<>();
    for (String string : stringArray) {
      if (StringUtils.isNotBlank(string.trim())) {
        nonEmptyStrings.add(string.trim());
      }
    }
    return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RustLanguage)) {
      return false;
    }
    RustLanguage that = (RustLanguage) o;
    return KEY.equals(that.getKey());

  }

  @Override
  public int hashCode() {
    return KEY.hashCode();
  }

  @Override
  public String toString() {
    return NAME;
  }

}
