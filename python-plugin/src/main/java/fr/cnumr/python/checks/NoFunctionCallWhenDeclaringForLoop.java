package fr.cnumr.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.tree.CallExpression;
import org.sonar.plugins.python.api.tree.Tree;

@Rule(
        key = NoFunctionCallWhenDeclaringForLoop.RULE_KEY,
        priority = Priority.MINOR,
        name = NoFunctionCallWhenDeclaringForLoop.DESCRIPTION,
        description = "")
public class NoFunctionCallWhenDeclaringForLoop extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "S69";
    public static final String DESCRIPTION = "Do not call a function when declaring a for-type loop";

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.CALL_EXPR, ctx -> {
            CallExpression callExpression = (CallExpression) ctx.syntaxNode();
            if (callExpression.parent().getKind() == Tree.Kind.FOR_STMT) {
                ctx.addIssue(callExpression, NoFunctionCallWhenDeclaringForLoop.DESCRIPTION);
            }
        });
    }
}