package fr.cnumr.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
        key = "S72",
        name = "Developpement",
        description = "Avoid SQL request in loop",
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidSQLRequestInLoop extends IssuableSubscriptionVisitor {

	private final AvoidSQLRequestInLoopVisitor visitorInFile = new AvoidSQLRequestInLoopVisitor();

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT, Tree.Kind.FOR_STATEMENT, Tree.Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
    	tree.accept(visitorInFile);
    }
    
	private class AvoidSQLRequestInLoopVisitor extends BaseTreeVisitor {
    	@Override
		public void visitMethodInvocation(MethodInvocationTree tree) {
			if ("executeQuery".equals(tree.symbol().name())) {
				reportIssue(tree, "Avoid SQL request in loop");
			} else 	 {
				super.visitMethodInvocation(tree);
			}
		}
    }
}