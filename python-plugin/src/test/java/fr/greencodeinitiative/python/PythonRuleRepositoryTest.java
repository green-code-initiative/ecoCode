/*
 * SonarQube Python Plugin
 * Copyright (C) 2012-2019 SonarSource SA
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
package fr.greencodeinitiative.python;

import static fr.greencodeinitiative.python.PythonRuleRepository.SONARQUBE_RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

import io.ecocode.rules.python.PythonRulesSpecificationsRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.utils.Version;

public class PythonRuleRepositoryTest {
  private static final Version MINIMAL_SONARQUBE_VERSION_COMPATIBILITY = Version.create(9, 8);

  private PythonRuleRepository pythonRuleRepository;
  private RulesDefinition.Context context;
  private RulesDefinition.Repository repository;

  @Before
  public void init() {
    pythonRuleRepository = new PythonRuleRepository();
    context = new RulesDefinition.Context();
    pythonRuleRepository.define(context);
    repository = context.repository(PythonRulesSpecificationsRepository.REPOSITORY_KEY);
  }

  @Test
  public void test() {
    assertThat(pythonRuleRepository.repositoryKey()).isEqualTo(PythonRulesSpecificationsRepository.REPOSITORY_KEY);
    assertThat(context.repositories()).hasSize(1).extracting("key").containsExactly(pythonRuleRepository.repositoryKey());
    assertThat(context.repositories().get(0).rules()).hasSize(10);
    assertThat(pythonRuleRepository.checkClasses()).hasSize(10);
  }


  /**
   * Check all rule keys must be prefixed by 'EC'
   */
  @Test
  public void testRuleKeyPrefix() {
    SoftAssertions assertions = new SoftAssertions();
    repository.rules().forEach(
            rule -> assertions.assertThat(rule.key()).startsWith("EC")
    );
    assertions.assertAll();
  }

  @Test()
  public void testRepositoryKey() {
    assertThat(pythonRuleRepository.repositoryKey()).isEqualTo("ecocode-python");
  }

  @Test()
  public void testAllRuleParametersHaveDescription() {
    context.repositories().stream()
            .flatMap(repository -> repository.rules().stream())
            .flatMap(rule -> rule.params().stream())
            .forEach(param -> assertThat(param.description()).as("description for " + param.key()).isNotEmpty());
    ;
  }

  @Test
  public void testPluginCompatibility() {
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
