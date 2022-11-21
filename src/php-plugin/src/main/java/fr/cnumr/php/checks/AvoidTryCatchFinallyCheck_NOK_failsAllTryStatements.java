package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "S34",
        name = "Developpement",
        description = AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements extends PHPSubscriptionCheck {

    public static final String ERROR_MESSAGE = "Avoid using try-catch-finally";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.TRY_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        context().newIssue(this, tree, ERROR_MESSAGE);
    }

}