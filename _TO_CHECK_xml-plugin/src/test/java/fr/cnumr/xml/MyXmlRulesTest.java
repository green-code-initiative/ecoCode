package fr.cnumr.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

public class MyXmlRulesTest {

	private int NumberOfRuleInRepository = 6;

	@Test
	public void rules() {
		MyXmlRules rulesDefinition = new MyXmlRules();
		RulesDefinition.Context context = new RulesDefinition.Context();
		rulesDefinition.define(context);
		RulesDefinition.Repository repository = context.repository(MyXmlRules.REPOSITORY_KEY);
		assertEquals(NumberOfRuleInRepository, repository.rules().size());
	}
}
