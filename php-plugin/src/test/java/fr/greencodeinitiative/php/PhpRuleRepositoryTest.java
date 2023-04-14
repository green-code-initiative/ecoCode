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
package fr.greencodeinitiative.php;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

import io.ecocode.rules.php.PhpRulesSpecificationsRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

import java.util.List;
import java.util.Optional;

public class PhpRuleRepositoryTest {

  private PhpRuleRepository phpRuleRepository;
  private RulesDefinition.Context context;

  @Before
  public void init() {
    phpRuleRepository = new PhpRuleRepository();
    context = new RulesDefinition.Context();
    phpRuleRepository.define(context);
  }

  @Test
  public void test() {
    assertThat(phpRuleRepository.repositoryKey()).isEqualTo(PhpRulesSpecificationsRepository.REPOSITORY_KEY);
    assertThat(context.repositories()).hasSize(1).extracting("key").containsExactly(phpRuleRepository.repositoryKey());
    assertThat(context.repositories().get(0).rules()).hasSize(9);
    assertThat(phpRuleRepository.checkClasses()).hasSize(9);
  }

  /**
   * Check all rule keys must be prefixed by 'EC'
   */
  @Test()
  public void testRuleKeyPrefix() {
    Optional<List<RulesDefinition.Rule>> rules = ofNullable(context.repository(PhpRulesSpecificationsRepository.REPOSITORY_KEY))
            .map(RulesDefinition.ExtendedRepository::rules);

    SoftAssertions assertions = new SoftAssertions();
    rules.orElseThrow().forEach(
            rule -> assertions.assertThat(rule.key()).startsWith("EC")
    );
    assertions.assertAll();
  }

  @Test()
  public void testRepositoryKey() {
    assertThat(phpRuleRepository.repositoryKey()).isEqualTo("ecocode-php");
  }

  @Test()
  public void testAllRuleParametersHaveDescription() {
    context.repositories().stream()
            .flatMap(repository -> repository.rules().stream())
            .flatMap(rule -> rule.params().stream())
            .forEach(param -> assertThat(param.description()).as("description for " + param.key()).isNotEmpty());
    ;
  }
}
