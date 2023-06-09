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

import fr.greencodeinitiative.python.checks.AvoidDoubleQuoteCheck;
import fr.greencodeinitiative.python.checks.AvoidFullSQLRequest;
import fr.greencodeinitiative.python.checks.AvoidGettersAndSetters;
import fr.greencodeinitiative.python.checks.AvoidGlobalVariableInFunctionCheck;
import fr.greencodeinitiative.python.checks.AvoidListComprehensionInIterations;
import fr.greencodeinitiative.python.checks.AvoidSQLRequestInLoop;
import fr.greencodeinitiative.python.checks.AvoidTryCatchFinallyCheck;
import fr.greencodeinitiative.python.checks.AvoidUnoptimizedVectorImagesCheck;
import fr.greencodeinitiative.python.checks.DetectUnoptimizedImageFormat;
import fr.greencodeinitiative.python.checks.NoFunctionCallWhenDeclaringForLoop;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.python.api.PythonCustomRuleRepository;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.Arrays;
import java.util.List;

import static io.ecocode.rules.Common.SONARQUBE_RUNTIME;
import static io.ecocode.rules.python.PythonRulesSpecificationsRepository.LANGUAGE;
import static io.ecocode.rules.python.PythonRulesSpecificationsRepository.NAME;
import static io.ecocode.rules.python.PythonRulesSpecificationsRepository.REPOSITORY_KEY;
import static io.ecocode.rules.python.PythonRulesSpecificationsRepository.RESOURCE_BASE_PATH;

public class PythonRuleRepository implements RulesDefinition, PythonCustomRuleRepository {

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPOSITORY_KEY, LANGUAGE).setName(NAME);
    RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, SONARQUBE_RUNTIME);
    ruleMetadataLoader.addRulesByAnnotatedClass(repository, (List) checkClasses());
    repository.done();
  }

  @Override
  public String repositoryKey() {
    return REPOSITORY_KEY;
  }

  @Override
  public List<Class> checkClasses() {
    return Arrays.asList(
            AvoidDoubleQuoteCheck.class,
            AvoidGettersAndSetters.class,
            AvoidGlobalVariableInFunctionCheck.class,
            AvoidSQLRequestInLoop.class,
            AvoidTryCatchFinallyCheck.class,
            AvoidUnoptimizedVectorImagesCheck.class,
            NoFunctionCallWhenDeclaringForLoop.class,
            AvoidFullSQLRequest.class,
            AvoidListComprehensionInIterations.class,
            DetectUnoptimizedImageFormat.class
    );
  }
}
