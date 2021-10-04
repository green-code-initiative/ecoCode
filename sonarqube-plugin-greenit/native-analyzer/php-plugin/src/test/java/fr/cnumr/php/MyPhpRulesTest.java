package fr.cnumr.php;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.junit.Assert.assertEquals;

public class MyPhpRulesTest {

  private int NumberOfRuleInRepository = 7;

  @Test
  public void rules() {
    MyPhpRules rulesDefinition = new MyPhpRules();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository(MyPhpRules.REPOSITORY_KEY);
    assertEquals(NumberOfRuleInRepository, repository.rules().size());
  }
}
