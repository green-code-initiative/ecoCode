package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.UnaryExpressionTree;
import org.sonar.plugins.php.api.tree.statement.TryStatementTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.Collections;
import java.util.List;

@Rule(
        key = AvoidTryCatchFinallyCheck.KEY,
        name = "Developpement",
        description = "Eviter d'utiliser try-catch-finally",
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidTryCatchFinallyCheck extends PHPSubscriptionCheck {

    public static final String KEY = "S34";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.TRY_STATEMENT);
    }

    // POSTFIX_INCREMENT
    @Override
    public void visitNode(Tree tree) {
        TryStatementTree method = (TryStatementTree) tree;
        /*
                if (returnType.is(firstParameterType.fullyQualifiedName())) {
            reportIssue(method.simpleName(), "Never do that!");
        }
         */
        context().newIssue(this, method, "Eviter d'utiliser try-catch-finally");

    }

}