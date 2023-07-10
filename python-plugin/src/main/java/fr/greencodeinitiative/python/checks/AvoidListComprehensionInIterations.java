package fr.greencodeinitiative.python.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.symbols.Symbol;
import org.sonar.plugins.python.api.tree.Expression;
import org.sonar.plugins.python.api.tree.CallExpression;
import org.sonar.plugins.python.api.tree.ForStatement;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonar.plugins.python.api.tree.RegularArgument;

import java.util.Objects;

import static org.sonar.plugins.python.api.tree.Tree.Kind.CALL_EXPR;
import static org.sonar.plugins.python.api.tree.Tree.Kind.FOR_STMT;
import static org.sonar.plugins.python.api.tree.Tree.Kind.LIST_COMPREHENSION;
import static org.sonar.plugins.python.api.tree.Tree.Kind.REGULAR_ARGUMENT;

@Rule(key = "EC404")
public class AvoidListComprehensionInIterations extends PythonSubscriptionCheck {

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

    private void visitCallExpression(SubscriptionContext context, CallExpression callExpression){
        switch (getFunctionNameFromCallExpression(callExpression)) {
            case "zip":
            case "filter":
            case "enumerate":
                Objects.requireNonNull(callExpression.argumentList()).
                  arguments().forEach(e -> visitFunctionArgument(context, e));
                break;
            default:
                break;
        }
    }

    private void visitFunctionArgument(SubscriptionContext context, Tree argument) {
        if (argument.is(REGULAR_ARGUMENT)) {
            Expression expression = ((RegularArgument)argument).expression();
            if (expression.is(LIST_COMPREHENSION)) {
                context.addIssue(expression.firstToken(), DESCRIPTION);
            } else if (expression.is(CALL_EXPR)) {
                CallExpression callExpression = (CallExpression) expression;
                visitCallExpression(context, callExpression);
            }
        }
    }

    private String getFunctionNameFromCallExpression(CallExpression callExpression) {
        Symbol symbol = callExpression.calleeSymbol();
        return symbol != null && symbol.name() != null ? symbol.name() : "";
    }
}
