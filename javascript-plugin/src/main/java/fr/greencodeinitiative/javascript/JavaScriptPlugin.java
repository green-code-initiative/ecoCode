package fr.greencodeinitiative.javascript;

import org.sonar.api.Plugin;

public class JavaScriptPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtension(JavaScriptRulesDefinition.class);
    }

}
