/*
 * Sonar CodeNarc Converter
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
package org.sonar.plugins.groovy.codenarc;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang.StringUtils;

@AutoValue
public abstract class RuleParameter implements Comparable<RuleParameter> {

  public abstract String key();

  public abstract String description();

  public abstract String defaultValue();

  public static RuleParameter createEmpty() {
    return create("", "", "");
  }

  public static RuleParameter create(String key, String defaultValue) {
    return create(key, "", defaultValue);
  }

  public static RuleParameter create(String key, String description, String defaultValue) {
    return new AutoValue_RuleParameter(key, description, defaultValue);
  }

  public boolean isEmpty() {
    return StringUtils.isBlank(key())
        && StringUtils.isBlank(defaultValue())
        && StringUtils.isBlank(description());
  }

  public boolean hasDefaultValue() {
    return StringUtils.isNotBlank(defaultValue());
  }

  public RuleParameter merge(RuleParameter parameter) {
    if (key() != null && key().equals(parameter.key())) {
      String newDescription = selectValue(description(), parameter.description());
      String newDefaultValue = selectValue(defaultValue(), parameter.defaultValue());
      return create(key(), newDescription, newDefaultValue);
    } else {
      return this;
    }
  }

  public RuleParameter withExpandedKey(String keyAdd) {
    return create(key() + keyAdd, description(), defaultValue());
  }

  public RuleParameter withNewDefaultValue(String newDefaultValue) {
    return create(key(), description(), newDefaultValue);
  }

  public RuleParameter withExpandedDescription(String descAdd) {
    String newDesc = description();
    newDesc += (newDesc.isEmpty() ? "" : " ") + descAdd;
    return create(key(), newDesc, defaultValue());
  }

  private static String selectValue(String currentValue, String newValue) {
    if (StringUtils.isBlank(currentValue) && StringUtils.isNotBlank(newValue)) {
      return newValue;
    }
    return currentValue;
  }

  @Override
  public String toString() {
    return "RuleParameter [key="
        + key()
        + ", defaultValue="
        + defaultValue()
        + ", description="
        + StringUtils.abbreviate(description(), 30)
        + "]";
  }

  @Override
  public int compareTo(RuleParameter o) {
    return key().compareTo(o.key());
  }
}
