package fr.cnumr.ecolinter;

import org.junit.jupiter.api.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

class MyStylelintRulesDefinitionTest {
    @Test
    void should_create_external_repo() {
        MyStylelintRulesDefinition stylelintRulesDefinition = new MyStylelintRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        stylelintRulesDefinition.define(context);
        assertThat(context.repositories()).hasSize(1);

        RulesDefinition.Repository stylelintRepo = context.repository("external_stylelint");
        assertThat(stylelintRepo.isExternal()).isTrue();
        assertThat(stylelintRepo.name()).isEqualTo("STYLELINT");
        assertThat(stylelintRepo.language()).isEqualTo("css");
        assertThat(stylelintRepo.rules()).hasSize(1);
    }
}
