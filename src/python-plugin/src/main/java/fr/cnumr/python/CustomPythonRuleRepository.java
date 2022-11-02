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
package fr.cnumr.python;



import fr.cnumr.python.checks.*;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.python.api.PythonCustomRuleRepository;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.*;

public class CustomPythonRuleRepository implements RulesDefinition, PythonCustomRuleRepository {
    public static final String LANGUAGE = "py";
    public static final String NAME = "Collectif Conception Numérique Responsable";
    public static final String RESOURCE_BASE_PATH = "fr/cnumr/l10n/python/rules/python";
    public static final String REPOSITORY_KEY = "cnumr-python";
    private static final Set<String> RULE_TEMPLATES_KEY = Collections.emptySet();

    private static void setTemplates(NewRepository repository) {
        RULE_TEMPLATES_KEY.stream()
                .map(repository::rule)
                .filter(Objects::nonNull)
                .forEach(rule -> rule.setTemplate(true));
    }

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, LANGUAGE).setName(NAME);

        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH);

        ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(checkClasses()));

        setTemplates(repository);

        repository.done();
    }

    @Override
    public String repositoryKey() {
        return REPOSITORY_KEY;
    }

    @Override
    public List<Class> checkClasses() {
        return Arrays.asList(
                AvoidGlobalVariableInFunctionCheck.class,
                AvoidFullSQLRequest.class,
                AvoidSQLRequestInLoop.class,
                AvoidTryCatchFinallyCheck.class,
                NoFunctionCallWhenDeclaringForLoop.class,
                AvoidGettersAndSetters.class
        );
    }
}
