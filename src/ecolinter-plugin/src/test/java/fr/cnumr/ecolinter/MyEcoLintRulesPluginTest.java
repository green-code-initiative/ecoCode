package fr.cnumr.ecolinter;

import org.junit.jupiter.api.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

class MyEcoLintRulesPluginTest {
    @Test
    void should_create_external_repo() {
        MyEslintRulesDefinition eslintRulesDefinition = new MyEslintRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        eslintRulesDefinition.define(context);
        assertThat(context.repositories()).hasSize(1);

        RulesDefinition.Repository eslintRepo = context.repository("external_eslint_repo");
        assertThat(eslintRepo.isExternal()).isTrue();
        assertThat(eslintRepo.name()).isEqualTo("ESLint");
        assertThat(eslintRepo.language()).isEqualTo("js");
        assertThat(eslintRepo.rules()).hasSize(9);
    }
}
