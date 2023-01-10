package fr.cnumr.ecolinter;

import org.junit.jupiter.api.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

class MyEslintRulesDefinitionTest {
    @Test
    void should_create_external_repo() {
        MyEslintRulesDefinition eslintRulesDefinition = new MyEslintRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        eslintRulesDefinition.define(context);
        assertThat(context.repositories()).hasSize(1);

        RulesDefinition.Repository stylelintRepo = context.repository("external_eslint_repo");
        assertThat(stylelintRepo.isExternal()).isTrue();
        assertThat(stylelintRepo.name()).isEqualTo("ESLint");
        assertThat(stylelintRepo.language()).isEqualTo("js");
        assertThat(stylelintRepo.rules()).hasSize(9);
    }
}
