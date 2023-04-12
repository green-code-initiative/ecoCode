/*
 * Copyright (C) 2023 Green Code Initiative
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
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.greencodeinitiative.java;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sonar.api.SonarRuntime;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction.Type;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Rule;
import org.sonar.api.utils.Version;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class JavaRulesDefinitionTest {

    private RulesDefinition.Repository repository;
    private RulesDefinition.Context context;

    @BeforeEach
    void init() {
        // TODO: Remove this check after Git repo split
        /*
            On an IDE (like IntelliJ), if the developer runs the unit tests without building/generating the Maven goals on the
            "ecocode-rules-specifications" module before, the unit tests will not see the generated HTML descriptions (from ASCIIDOC files).
            The developer must therefore configure his IDE to build the `ecocode-rules-specifications` module before launching the Tests.

            When the `java-plugin` submodule is in a specific Git repository, `ecocode-rules-specifications` will be fetched from a classic
            external Maven dependency. There will therefore no longer be any need to perform this specific configuration.
         */
        if (JavaRulesDefinition.class.getResource("/io/ecocode/rules/java/EC4.json") == null) {
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

        final SonarRuntime sonarRuntime = mock(SonarRuntime.class);
        doReturn(Version.create(0, 0)).when(sonarRuntime).getApiVersion();
        JavaRulesDefinition rulesDefinition = new JavaRulesDefinition(sonarRuntime);
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        repository = context.repository(rulesDefinition.repositoryKey());
    }

    @Test
    @DisplayName("Test repository metadata")
    void testMetadata() {
        assertThat(repository.name()).isEqualTo("ecoCode");
        assertThat(repository.language()).isEqualTo("java");
        assertThat(repository.key()).isEqualTo("ecocode-java");
        assertThat(repository.rules()).hasSize(19);
    }

    @Test
    void testRegistredRules() {
        assertThat(repository.rules()).hasSize(19);
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

    @Test
    void assertRuleProperties() {
        Rule rule = repository.rule("EC67");
        assertThat(rule).isNotNull();
        assertThat(rule.name()).isEqualTo("Use ++i instead of i++");
        assertThat(rule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
        assertThat(rule.type()).isEqualTo(RuleType.CODE_SMELL);
    }

    @Test
    void testAllRuleParametersHaveDescription() {
        SoftAssertions assertions = new SoftAssertions();
        repository.rules().stream()
                .flatMap(rule -> rule.params().stream())
                .forEach(param -> assertions.assertThat(param.description()).as("description for " + param.key()).isNotEmpty());
        assertions.assertAll();
    }

}
