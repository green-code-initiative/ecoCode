package fr.greencodeinitiative.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonar.plugins.python.api.tree.ForStatement;
import org.sonar.plugins.python.api.tree.Expression;
import org.sonar.plugins.python.api.tree.CallExpression;

import java.util.Objects;

import static org.sonar.plugins.python.api.tree.Tree.Kind.LIST_COMPREHENSION;
import static org.sonar.plugins.python.api.tree.Tree.Kind.CALL_EXPR;
import static org.sonar.plugins.python.api.tree.Tree.Kind.FOR_STMT;

@Rule(
        key = AvoidListComprehensionInIterations.RULE_KEY,
        name = AvoidListComprehensionInIterations.DESCRIPTION,
        description = AvoidListComprehensionInIterations.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"bug", "eco-design", "ecocode"})
public class AvoidListComprehensionInIterations extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "EC404";
    public static final String DESCRIPTION = "Use generator comprehension instead of list comprehension in for loop declaration";

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(FOR_STMT, this::visitIteration);

    }

    private void visitIteration(SubscriptionContext context) {
        ForStatement forStatement = (ForStatement) context.syntaxNode();

        Expression forTestExpression = forStatement.testExpressions().get(0);
        if (forTestExpression.is(LIST_COMPREHENSION)) {

            context.addIssue(forTestExpression.firstToken(), DESCRIPTION);

        } else if (forTestExpression.is(CALL_EXPR)) {
            CallExpression callExpression = (CallExpression) forTestExpression;
            visitCallExpression(context, callExpression);
        }
    }

    private void visitFunctionArgument(SubscriptionContext context, Tree argument) {
        if (argument.is(LIST_COMPREHENSION)) {
            context.addIssue(argument.firstToken(), DESCRIPTION);

        } else if (argument.is(CALL_EXPR)) {
            CallExpression callExpression = (CallExpression) argument;
            visitCallExpression(context, callExpression);
        }
    }

    private void visitCallExpression(SubscriptionContext context, CallExpression callExpression){
        switch (callExpression.callee().firstToken().value()) {
            case "zip":
            case "filter":
            case "enumerate":
                Objects.requireNonNull(callExpression.argumentList()).
                  arguments().forEach(e -> visitFunctionArgument(context, e.children().get(0)));
                break;
            default:
                break;
        }
    }

}
