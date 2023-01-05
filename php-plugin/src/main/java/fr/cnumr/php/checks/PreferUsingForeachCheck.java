package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.FunctionCallTree;
import org.sonar.plugins.php.api.tree.expression.ParenthesisedExpressionTree;
import org.sonar.plugins.php.api.tree.statement.ForStatementTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.*;

@Rule(
        key = "GRSP0007",
        name = "Developpement",
        description = PreferUsingForeachCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})

public class PreferUsingForeachCheck extends PHPSubscriptionCheck {

    public static final String ERROR_MESSAGE = "Prefer using foreach";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.FOR_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        ForStatementTree method = (ForStatementTree) tree;
        checkIssue(method);
    }

    public void checkIssue(ForStatementTree forStatementTree) {
        if(forStatementTree.forToken() != null){
            repport(forStatementTree);
            return;
        }
    }

    private void repport(ForStatementTree forStatementTree) {
        context().newIssue(this, forStatementTree, ERROR_MESSAGE);
    }

}
