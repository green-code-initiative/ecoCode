package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Arrays;
import java.util.List;

@Rule(
        key = "S53",
        name = "Developpement",
        description = "Avoid the use of Foreach with Arrays",
        priority = Priority.MINOR,
        tags = {"bug"})
public class UseCorrectForLoop extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
    	
    	ForEachStatement forEachTree = (ForEachStatement) tree;
    	if (forEachTree.expression().symbolType().isArray()) {
            reportIssue(tree, "Avoid the use of Foreach with an Array");
        }
    }
}