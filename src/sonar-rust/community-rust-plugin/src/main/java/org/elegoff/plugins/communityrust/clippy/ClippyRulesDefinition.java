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

import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.ExternalRuleLoader;

import static org.elegoff.plugins.communityrust.clippy.ClippySensor.LINTER_KEY;
import static org.elegoff.plugins.communityrust.clippy.ClippySensor.LINTER_NAME;

public class ClippyRulesDefinition implements RulesDefinition {

  private static final String RULES_JSON = "org/elegoff/l10n/rust/rules/clippy/clippylints.json";
  private static final String RULE_REPOSITORY_LANGUAGE = RustLanguage.KEY;
  static final ExternalRuleLoader RULE_LOADER = new ExternalRuleLoader(LINTER_KEY, LINTER_NAME, RULES_JSON, RULE_REPOSITORY_LANGUAGE);

  @Override
  public void define(Context context) {
    RULE_LOADER.createExternalRuleRepository(context);
  }
}
