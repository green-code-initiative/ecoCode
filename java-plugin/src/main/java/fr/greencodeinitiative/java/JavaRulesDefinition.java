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

import fr.greencodeinitiative.java.checks.ArrayCopyCheck;
import fr.greencodeinitiative.java.checks.AvoidConcatenateStringsInLoop;
import fr.greencodeinitiative.java.checks.AvoidFullSQLRequest;
import fr.greencodeinitiative.java.checks.AvoidGettingSizeCollectionInLoop;
import fr.greencodeinitiative.java.checks.AvoidMultipleIfElseStatement;
import fr.greencodeinitiative.java.checks.AvoidRegexPatternNotStatic;
import fr.greencodeinitiative.java.checks.AvoidSQLRequestInLoop;
import fr.greencodeinitiative.java.checks.AvoidSetConstantInBatchUpdate;
import fr.greencodeinitiative.java.checks.AvoidSpringRepositoryCallInLoopCheck;
import fr.greencodeinitiative.java.checks.AvoidStatementForDMLQueries;
import fr.greencodeinitiative.java.checks.AvoidUsageOfStaticCollections;
import fr.greencodeinitiative.java.checks.AvoidUsingGlobalVariablesCheck;
import fr.greencodeinitiative.java.checks.FreeResourcesOfAutoCloseableInterface;
import fr.greencodeinitiative.java.checks.IncrementCheck;
import fr.greencodeinitiative.java.checks.InitializeBufferWithAppropriateSize;
import fr.greencodeinitiative.java.checks.NoFunctionCallWhenDeclaringForLoop;
import fr.greencodeinitiative.java.checks.OptimizeReadFileExceptions;
import fr.greencodeinitiative.java.checks.UnnecessarilyAssignValuesToVariables;
import fr.greencodeinitiative.java.checks.UseCorrectForLoop;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.utils.Version;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.ecocode.rules.java.JavaRulesSpecificationsRepository.LANGUAGE;
import static io.ecocode.rules.java.JavaRulesSpecificationsRepository.NAME;
import static io.ecocode.rules.java.JavaRulesSpecificationsRepository.REPOSITORY_KEY;
import static io.ecocode.rules.java.JavaRulesSpecificationsRepository.RESOURCE_BASE_PATH;

/**
 * Declare rule metadata in server repository of rules.
 * That allows to list the rules in the page "Rules".
 */
public class JavaRulesDefinition implements RulesDefinition {
  static final List<Class<? extends JavaCheck>> ANNOTATED_RULE_CLASSES = List.of(
          ArrayCopyCheck.class,
          IncrementCheck.class,
          AvoidConcatenateStringsInLoop.class,
          AvoidUsageOfStaticCollections.class,
          AvoidGettingSizeCollectionInLoop.class,
          AvoidRegexPatternNotStatic.class,
          NoFunctionCallWhenDeclaringForLoop.class,
          AvoidStatementForDMLQueries.class,
          AvoidSpringRepositoryCallInLoopCheck.class,
          AvoidSQLRequestInLoop.class,
          AvoidFullSQLRequest.class,
          UseCorrectForLoop.class,
          UnnecessarilyAssignValuesToVariables.class,
          OptimizeReadFileExceptions.class,
          InitializeBufferWithAppropriateSize.class,
          AvoidUsingGlobalVariablesCheck.class,
          AvoidSetConstantInBatchUpdate.class,
          FreeResourcesOfAutoCloseableInterface.class,
          AvoidMultipleIfElseStatement.class
  );

  static final List<Class<? extends JavaCheck>> ANNOTATED_RULE_TEST_CLASSES = Collections.emptyList();
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
    ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(ANNOTATED_RULE_CLASSES));
    repository.done();
  }
}
