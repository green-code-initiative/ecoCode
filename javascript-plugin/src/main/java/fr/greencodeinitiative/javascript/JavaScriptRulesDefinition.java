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
package fr.greencodeinitiative.javascript;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.ExternalRuleLoader;

public class JavaScriptRulesDefinition implements RulesDefinition {

    private static final String LANGUAGE = "js";

    /*
     As we integrate our rules using SonarJS, these values should remain to "eslint_repo" and "ESLint" in order to import them correctly
     see: https://github.com/green-code-initiative/ecoCode/pull/79#discussion_r1137797583
     */
    private static final String LINTER_KEY = "eslint_repo";
    private static final String LINTER_NAME = "ESLint";
    private static final String METADATA_PATH = "fr/greencodeinitiative/l10n/javascript/rules.json";

    @Override
    public void define(Context context) {
        new ExternalRuleLoader(LINTER_KEY, LINTER_NAME, METADATA_PATH, LANGUAGE)
                .createExternalRuleRepository(context);
    }

}
