package fr.cnumr.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
        key = "S74",
        name = "Developpement",
        description = "Don't use the query SELECT * FROM",
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidFullSQLRequest extends IssuableSubscriptionVisitor {

	private static final String regExpSelectFrom = "(?i).*select.*\\*.*from.*";
	
    @Override
    public List<Kind> nodesToVisit() {
        return  Arrays.asList(Tree.Kind.STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {
    	boolean isSelectFrom = false;
    	
    	if (tree.is(Kind.STRING_LITERAL,Kind.TEXT_BLOCK)) {
    		LiteralTree literal = (LiteralTree) tree;
    		isSelectFrom = literal.value().matches(regExpSelectFrom);
    	}
    	
    	if (isSelectFrom) {
    		reportIssue(tree, "Don't use the query SELECT * FROM");
    	}
    }
}