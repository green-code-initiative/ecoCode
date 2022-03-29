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
package org.sonar.plugins.groovy.codenarc;

import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.rule.internal.NewActiveRule;
import org.sonar.api.rule.RuleKey;

public class ActiveRulesBuilderWrapper {

  private ActiveRulesBuilder builder = new ActiveRulesBuilder();
  private NewActiveRule.Builder lastRule;

  boolean newType = false;

  public ActiveRulesBuilderWrapper addRule(String key) {
    addLastRule();
    RuleKey ruleKey = RuleKey.of(CodeNarcRulesDefinition.REPOSITORY_KEY, key);
    lastRule = new NewActiveRule.Builder();
    lastRule.setRuleKey(ruleKey);
    setInternalKey(key);
    return this;
  }

  public ActiveRulesBuilderWrapper setName(String name) {
    lastRule.setName(name);
    return this;
  }

  public ActiveRulesBuilderWrapper setInternalKey(String key) {
    lastRule.setInternalKey(key);
    return this;
  }

  public ActiveRulesBuilderWrapper addParam(String key, String value) {
    lastRule.setParam(key, value);
    return this;
  }

  private void addLastRule() {
    if (lastRule != null) {
      builder.addRule(lastRule.build());
      lastRule = null;
    }
  }

  public ActiveRules build() {
    addLastRule();
    return builder.build();
  }
}
