package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.expression.LiteralTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.Arrays;
import java.util.List;

@Rule(
		key = "S74",
		name = "Developpement",
		description = "Don't use the query SELECT * FROM",
		priority = Priority.MINOR,
		tags = {"bug"}
)

public class AvoidFullSQLRequestCheck extends PHPSubscriptionCheck {

	private static final String RegExpSelectFrom = "(?i).*select.*\\*.*from.*";
	private static final String ErrorMessage = "Don't use the query SELECT * FROM";

	@Override
	public List<Kind> nodesToVisit() {
		return Arrays.asList(Kind.REGULAR_STRING_LITERAL);
	}

	@Override
	public void visitNode(Tree tree) {

		LiteralTree literal = (LiteralTree) tree;
		if(literal.value().matches(RegExpSelectFrom))
			context().newIssue(this, tree, ErrorMessage);

	}
}
