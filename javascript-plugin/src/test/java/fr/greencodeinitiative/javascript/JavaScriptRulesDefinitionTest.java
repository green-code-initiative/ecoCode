package fr.greencodeinitiative.javascript;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaScriptRulesDefinitionTest {

    @Test
    public void createExternalRepository() {
        RulesDefinition.Context context = new RulesDefinition.Context();
        new JavaScriptRulesDefinition().define(context);
        assertThat(context.repositories()).hasSize(1);

        RulesDefinition.Repository repository = context.repositories().get(0);
        assertThat(repository.isExternal()).isTrue();
        assertThat(repository.language()).isEqualTo("js");
        assertThat(repository.key()).isEqualTo("external_eslint_repo");
    }

}
