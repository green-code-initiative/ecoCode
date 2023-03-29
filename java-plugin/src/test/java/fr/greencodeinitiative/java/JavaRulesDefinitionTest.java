/*
 * SonarQube Java
 * Copyright (C) 2012-2021 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
package fr.greencodeinitiative.java;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction.Type;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Param;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;

import static org.assertj.core.api.Assertions.assertThat;

class JavaRulesDefinitionTest {

    private RulesDefinition.Repository repository;

    @BeforeEach
    void init() {
        final JavaRulesDefinition rulesDefinition = new JavaRulesDefinition();
        final RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        repository = context.repository(JavaRulesDefinition.REPOSITORY_KEY);
    }

    @Test
    void test() {
        assertThat(repository.name()).isEqualTo(JavaRulesDefinition.NAME);
        assertThat(repository.language()).isEqualTo(JavaRulesDefinition.LANGUAGE);
        assertThat(repository.rules()).hasSize(RulesList.getChecks().size());
        assertThat(repository.rules().stream().filter(Rule::template)).isEmpty();

        assertRuleProperties(repository);
        assertAllRuleParametersHaveDescription(repository);
    }

    @Test
    @DisplayName("All rule keys must be prefixed by 'EC'")
    void testRuleKeyPrefix() {
        SoftAssertions assertions = new SoftAssertions();
        repository.rules().forEach(
                rule -> assertions.assertThat(rule.key()).startsWith("EC")
        );
        assertions.assertAll();
    }

    private static void assertRuleProperties(Repository repository) {
        Rule rule = repository.rule("EC67");
        assertThat(rule).isNotNull();
        assertThat(rule.name()).isEqualTo("Use ++i instead of i++");
        assertThat(rule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
        assertThat(rule.type()).isEqualTo(RuleType.CODE_SMELL);
    }

    private static void assertAllRuleParametersHaveDescription(Repository repository) {
        for (Rule rule : repository.rules()) {
            for (Param param : rule.params()) {
                assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
            }
        }
    }

}
