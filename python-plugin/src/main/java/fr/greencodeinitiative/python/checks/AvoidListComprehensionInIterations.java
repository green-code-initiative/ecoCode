package fr.greencodeinitiative.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.*;

import java.util.Objects;

import static org.sonar.plugins.python.api.tree.Tree.Kind.*;

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
            switch (callExpression.callee().firstToken().value()) {
                case "zip":
                case "filter":
                case "enumerate":
                    Objects.requireNonNull(callExpression.argumentList()).
                            arguments().forEach(e -> visitFunctionArguments(context, e.children().get(0)));
            }
        }
    }

    private void visitFunctionArguments(SubscriptionContext context, Tree argument) {
        if (argument.is(LIST_COMPREHENSION)) {
            context.addIssue(argument.firstToken(), DESCRIPTION);

        } else if (argument.is(CALL_EXPR)) {
            CallExpression callExpression = (CallExpression) argument;
            switch (callExpression.callee().firstToken().value()) {
                case "zip":
                case "filter":
                case "enumerate":
                    Objects.requireNonNull(callExpression.argumentList()).
                            arguments().forEach(e -> visitFunctionArguments(context, e.children().get(0)));
                    break;

            }
        }

    }

}
