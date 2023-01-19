package fr.cnumr.php;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

public class PhpRulesDefinitionTest {

    private final int NumberOfRuleInRepository = 6;

    @Test
    public void rules() {
        PhpRulesDefinition rulesDefinition = new PhpRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        RulesDefinition.Repository repository = context.repository(PhpRulesDefinition.REPOSITORY_KEY);
        assertEquals(NumberOfRuleInRepository, repository.rules().size());
    }
}
