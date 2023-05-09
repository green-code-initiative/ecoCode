package fr.greencodeinitiative.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.tree.CallExpression;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(
        key = NoFunctionCallWhenDeclaringForLoop.RULE_KEY,
        name = NoFunctionCallWhenDeclaringForLoop.DESCRIPTION,
        description = NoFunctionCallWhenDeclaringForLoop.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"bug", "eco-design", "ecocode"})
@DeprecatedRuleKey(repositoryKey = "gci-python", ruleKey = "S69")
public class NoFunctionCallWhenDeclaringForLoop extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "EC69";
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
