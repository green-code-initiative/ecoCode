package fr.cnumr.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.tree.CallExpression;
import org.sonar.plugins.python.api.tree.ForStatement;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonar.python.tree.ForStatementImpl;

import java.util.List;

@Rule(
        key = NoFunctionCallWhenDeclaringForLoop.RULE_KEY,
        priority = Priority.MINOR,
        name = "Python subscription visitor check",
        description = "desc")
public class NoFunctionCallWhenDeclaringForLoop extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "S69";

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.CALL_EXPR, ctx -> {
            CallExpression callExpression = (CallExpression) ctx.syntaxNode();
            if (callExpression.parent().getKind() == Tree.Kind.FOR_STMT) {
                ctx.addIssue(callExpression, "Ne pas appeler de fonction dans la déclaration d’une boucle de type for");
            }
        });
    }
}