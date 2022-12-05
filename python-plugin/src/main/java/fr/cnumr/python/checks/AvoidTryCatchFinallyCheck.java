package fr.cnumr.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonar.plugins.python.api.tree.TryStatement;

@Rule(
        key = AvoidTryCatchFinallyCheck.RULE_KEY,
        name = "Developpement",
        description = AvoidTryCatchFinallyCheck.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidTryCatchFinallyCheck extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "S34";
    public static final String DESCRIPTION = "Avoid the use of try-catch-finally";

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.TRY_STMT, this::visitNode);
    }

    public void visitNode(SubscriptionContext ctx) {
        TryStatement tryStatement = (TryStatement) ctx.syntaxNode();
        ctx.addIssue(tryStatement.tryKeyword(), DESCRIPTION);

    }

}