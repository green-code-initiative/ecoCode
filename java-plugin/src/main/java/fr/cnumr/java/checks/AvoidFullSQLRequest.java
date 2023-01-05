package fr.cnumr.java.checks;

import static java.util.Collections.singletonList;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.List;
import java.util.function.Predicate;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
        key = "S74",
        name = "Developpement",
        description = AvoidFullSQLRequest.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidFullSQLRequest extends IssuableSubscriptionVisitor {

	protected static final String MESSAGERULE = "Don't use the query SELECT * FROM";
	private static final Predicate<String> SELECT_FROM_REGEXP = 
			compile("select\\s*\\*\\s*from", CASE_INSENSITIVE).asPredicate(); //simple regexp, more precision
	
    @Override
    public List<Kind> nodesToVisit() {
        return singletonList(Tree.Kind.STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {
    	String value = ((LiteralTree) tree).value();
    	if (SELECT_FROM_REGEXP.test(value)) {
    		reportIssue(tree, MESSAGERULE);
    	}
    }
}