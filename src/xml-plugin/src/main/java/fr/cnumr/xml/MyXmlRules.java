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
package fr.cnumr.xml;

import java.util.Collections;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import fr.cnumr.xml.checks.CheckList;

/**
 * Extension point to define a PHP rule repository.
 */
public class MyXmlRules implements RulesDefinition {

	public static final String LANGUAGE = "xml";
	public static final String NAME = "MyCompany Custom Repository";
	public static final String RESOURCE_BASE_PATH = "fr/cnumr/l10n/xml/rules/custom";
	public static final String REPOSITORY_KEY = "cnumr-xml";
	private static final Set<String> RULE_TEMPLATES_KEY = Collections.emptySet();

	@Override
	public void define(Context context) {
		NewRepository repository = context.createRepository(REPOSITORY_KEY, LANGUAGE).setName(NAME);

		RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH);

		// add the new checks
		ruleMetadataLoader.addRulesByAnnotatedClass(repository, CheckList.getCheckClasses());

//		repository.rule("XPathCheck").setTemplate(true);
//		repository.rule("S3417").setTemplate(true);
		repository.done();
	}

}
