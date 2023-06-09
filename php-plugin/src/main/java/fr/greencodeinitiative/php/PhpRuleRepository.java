/*
 * SonarQube PHP Plugin
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

import fr.greencodeinitiative.php.checks.AvoidDoubleQuoteCheck;
import fr.greencodeinitiative.php.checks.AvoidFullSQLRequestCheck;
import fr.greencodeinitiative.php.checks.AvoidGettingSizeCollectionInLoopCheck;
import fr.greencodeinitiative.php.checks.AvoidSQLRequestInLoopCheck;
import fr.greencodeinitiative.php.checks.AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements;
import fr.greencodeinitiative.php.checks.AvoidUsingGlobalVariablesCheck;
import fr.greencodeinitiative.php.checks.IncrementCheck;
import fr.greencodeinitiative.php.checks.NoFunctionCallWhenDeclaringForLoop;
import fr.greencodeinitiative.php.checks.UseOfMethodsForBasicOperations;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.php.api.visitors.PHPCustomRuleRepository;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.List;

import static io.ecocode.rules.php.PhpRulesSpecificationsRepository.LANGUAGE;
import static io.ecocode.rules.php.PhpRulesSpecificationsRepository.NAME;
import static io.ecocode.rules.php.PhpRulesSpecificationsRepository.REPOSITORY_KEY;
import static io.ecocode.rules.php.PhpRulesSpecificationsRepository.RESOURCE_BASE_PATH;

public class PhpRuleRepository implements RulesDefinition, PHPCustomRuleRepository {
  private static final Version SONARQUBE_RUNTIME_VERSION = Version.create(9, 8);
  static final SonarRuntime SONARQUBE_RUNTIME = new SonarRuntime() {
    @Override
    public Version getApiVersion() {
      return SONARQUBE_RUNTIME_VERSION;
    }

    @Override
    public SonarProduct getProduct() {
      return SonarProduct.SONARQUBE;
    }

    @Override
    public SonarQubeSide getSonarQubeSide() {
      return SonarQubeSide.SCANNER;
    }

    @Override
    public SonarEdition getEdition() {
      return SonarEdition.COMMUNITY;
    }
  };

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPOSITORY_KEY, LANGUAGE).setName(NAME);
    RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, SONARQUBE_RUNTIME);
    ruleMetadataLoader.addRulesByAnnotatedClass(repository, checkClasses());
    repository.done();
  }

  @Override
  public String repositoryKey() {
    return REPOSITORY_KEY;
  }

  @Override
  public List<Class<?>> checkClasses() {
    return List.of(
            AvoidGettingSizeCollectionInLoopCheck.class,
            AvoidDoubleQuoteCheck.class,
            AvoidFullSQLRequestCheck.class,
            AvoidSQLRequestInLoopCheck.class,
            AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements.class,
            AvoidUsingGlobalVariablesCheck.class,
            IncrementCheck.class,
            NoFunctionCallWhenDeclaringForLoop.class,
            UseOfMethodsForBasicOperations.class
    );
  }
}
