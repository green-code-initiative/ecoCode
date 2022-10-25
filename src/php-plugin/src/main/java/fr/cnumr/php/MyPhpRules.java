/*
 * SonarQube PHP Custom Rules Example
 * Copyright (C) 2016-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
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
package fr.cnumr.php;


import com.google.common.collect.ImmutableList;
import fr.cnumr.php.checks.*;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.php.api.visitors.PHPCustomRuleRepository;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Extension point to define a PHP rule repository.
 */
public class MyPhpRules implements RulesDefinition, PHPCustomRuleRepository {

  public static final String LANGUAGE = "php";
  public static final String NAME = "Collectif Conception Numérique Responsable";
  public static final String RESOURCE_BASE_PATH = "fr/cnumr/l10n/php/rules/custom";
  public static final String REPOSITORY_KEY = "cnumr-php";
  private static final Set<String> RULE_TEMPLATES_KEY = Collections.emptySet();

  /**
   * Provide the repository key
   */
  @Override
  public String repositoryKey() {
    return REPOSITORY_KEY;
  }

  /**
   * Provide the list of checks class that implements rules
   * to be part of the rule repository
   */
  @Override
  public ImmutableList<Class> checkClasses() {
    
    return ImmutableList.of(AvoidUsingFinallyInTryCatchCheck.class, IncrementCheck.class, AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements.class, AvoidDoubleQuoteCheck.class,
            AvoidFullSQLRequestCheck.class, AvoidSQLRequestInLoopCheck.class, NoFunctionCallWhenDeclaringForLoop.class);

  }

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPOSITORY_KEY, LANGUAGE).setName(NAME);

    RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH);

    ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(checkClasses()));

    setTemplates(repository);

    repository.done();
  }

  private static void setTemplates(NewRepository repository) {
    RULE_TEMPLATES_KEY.stream()
            .map(repository::rule)
            .filter(Objects::nonNull)
            .forEach(rule -> rule.setTemplate(true));
  }}
