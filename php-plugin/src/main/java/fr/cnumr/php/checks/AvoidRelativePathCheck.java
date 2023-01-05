package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.FunctionCallTree;
import org.sonar.plugins.php.api.tree.expression.ParenthesisedExpressionTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.*;

@Rule(
        key = "GRSP0008",
        name = "Developpement",
        description = AvoidRelativePathCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})

public class AvoidRelativePathCheck extends PHPSubscriptionCheck {

    public static final String ERROR_MESSAGE = "Avoid using relative path, prefer using absolute path";
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.FUNCTION_CALL);
    }

    @Override
    public void visitNode(Tree tree) {
        FunctionCallTree method = (FunctionCallTree) tree;
        checkIssue(method);
    }

    public void checkIssue(FunctionCallTree functionCallTree) {
        if(functionCallTree.callee().toString().equals("include") || functionCallTree.callee().toString().equals("require")){
            ParenthesisedExpressionTree expressionTree = (ParenthesisedExpressionTree) functionCallTree.
                    callArguments().get(0).value();
            if(!expressionTree.expression().toString().startsWith("\"/")){
                context().newIssue(this, functionCallTree, ERROR_MESSAGE);
            }
        }
    }
}
