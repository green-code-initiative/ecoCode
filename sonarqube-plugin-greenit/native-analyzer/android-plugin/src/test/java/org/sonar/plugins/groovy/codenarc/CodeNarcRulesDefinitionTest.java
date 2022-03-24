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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Rule;
import org.sonar.plugins.groovy.foundation.Groovy;

public class CodeNarcRulesDefinitionTest {

  @Test
  public void test() {
    CodeNarcRulesDefinition definition = new CodeNarcRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    definition.define(context);
    RulesDefinition.Repository repository =
        context.repository(CodeNarcRulesDefinition.REPOSITORY_KEY);

    assertThat(repository.name()).isEqualTo(CodeNarcRulesDefinition.REPOSITORY_NAME);
    assertThat(repository.language()).isEqualTo(Groovy.KEY);

    List<Rule> rules = repository.rules();
    assertThat(rules).hasSize(394);

    List<String> missingDebt = new LinkedList<>();
    for (Rule rule : rules) {
      assertThat(rule.key()).isNotNull();
      assertThat(rule.internalKey()).isNotNull();
      assertThat(rule.name()).isNotNull();
      assertThat(rule.htmlDescription()).isNotNull();
      if (rule.debtRemediationFunction() == null) {
        missingDebt.add(rule.key());
      }
    }
    // From SONARGROOV-36, 'org.codenarc.rule.generic.IllegalSubclassRule' does not have debt by
    // purpose
    assertThat(missingDebt).containsOnly("org.codenarc.rule.generic.IllegalSubclassRule.fixed");

    Rule rule = repository.rule("org.codenarc.rule.braces.ElseBlockBracesRule");
    assertThat(rule.params()).hasSize(1);
    assertThat(rule.params().get(0).defaultValue()).isEqualToIgnoringCase("false");
  }
}
