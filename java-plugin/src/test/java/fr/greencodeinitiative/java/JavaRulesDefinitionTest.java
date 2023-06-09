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

import io.ecocode.rules.java.JavaRulesSpecificationsRepository;
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
import org.sonar.api.utils.Version;

import static fr.greencodeinitiative.java.JavaRulesDefinition.ANNOTATED_RULE_CLASSES;
import static fr.greencodeinitiative.java.JavaRulesDefinition.ANNOTATED_RULE_TEST_CLASSES;
import static fr.greencodeinitiative.java.JavaRulesDefinition.SONARQUBE_RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class JavaRulesDefinitionTest {
    private static final Version MINIMAL_SONARQUBE_VERSION_COMPATIBILITY = Version.create(9, 8);

    private RulesDefinition.Repository repository;
    private RulesDefinition.Context context;

    @BeforeEach
    void init() {
        // TODO: check to revome after git repo split
        if (JavaRulesDefinition.class.getResource("/io/ecocode/rules/java/EC1.json") == null) {
            String message = "'ecocode-rules-specification' resources corrupted. Please check build of 'ecocode-rules-specification' module";
            if (System.getProperties().keySet().stream().anyMatch(k -> k.toString().startsWith("idea."))) {
                message += "\n\nOn 'IntelliJ IDEA':" +
                        "\n1. go to settings :" +
                        "\n   > Build, Execution, Deployment > Build Tools > Maven > Runner" +
                        "\n2. check option:" +
                        "\n   > Delegate IDE build/run actions to Maven" +
                        "\n3. Click on menu: " +
                        "\n   > Build > Build Project"
                ;
            }
            fail(message);
        }

        final JavaRulesDefinition rulesDefinition = new JavaRulesDefinition();
        context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        repository = context.repository(JavaRulesSpecificationsRepository.REPOSITORY_KEY);
    }

    @Test
    void test() {
        assertThat(repository.name()).isEqualTo(JavaRulesSpecificationsRepository.NAME);
        assertThat(repository.language()).isEqualTo(JavaRulesSpecificationsRepository.LANGUAGE);
        assertThat(repository.rules()).hasSize(ANNOTATED_RULE_CLASSES.size() + ANNOTATED_RULE_TEST_CLASSES.size());
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

    @Test
    void testPluginCompatibility() {
        SonarRuntime sonarRuntime = SONARQUBE_RUNTIME;
        assertThat(MINIMAL_SONARQUBE_VERSION_COMPATIBILITY.isGreaterThanOrEqual(sonarRuntime.getApiVersion()))
                .describedAs("Plugin must be compatible with SonarQube 9.8")
                .isTrue();
        assertThat(sonarRuntime.getProduct())
                .describedAs("Plugin should applied to SonarQube")
                .isEqualTo(SonarProduct.SONARQUBE);
        assertThat(sonarRuntime.getEdition())
                .describedAs("Plugin should be compatible with Community Edition")
                .isEqualTo(SonarEdition.COMMUNITY);
        assertThat(sonarRuntime.getSonarQubeSide())
                .describedAs("Plugin should be executed by scanner")
                .isEqualTo(SonarQubeSide.SCANNER);
    }
}
