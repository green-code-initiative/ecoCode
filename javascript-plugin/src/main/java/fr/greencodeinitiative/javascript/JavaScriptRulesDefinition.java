package fr.greencodeinitiative.javascript;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.ExternalRuleLoader;

public class JavaScriptRulesDefinition implements RulesDefinition {

    private static final String LANGUAGE = "js";
    private static final String LINTER_KEY = "eslint_repo";
    private static final String LINTER_NAME = "ESLint";
    private static final String METADATA_PATH = "fr/greencodeinitiative/l10n/javascript/rules.json";

    @Override
    public void define(Context context) {
        new ExternalRuleLoader(LINTER_KEY, LINTER_NAME, METADATA_PATH, LANGUAGE)
                .createExternalRuleRepository(context);
    }

}
