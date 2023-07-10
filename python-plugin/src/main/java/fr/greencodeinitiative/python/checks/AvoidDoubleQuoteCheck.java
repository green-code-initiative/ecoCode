package fr.greencodeinitiative.python.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.StringLiteral;
import org.sonar.plugins.python.api.tree.Tree;

@Rule(key = "EC66")
public class AvoidDoubleQuoteCheck extends PythonSubscriptionCheck {
    public static final String MESSAGE_RULE = "Avoid using quotation mark (\"), prefer using simple quote (')";
    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.STRING_LITERAL, this::visitNodeString);
    }

    private void visitNodeString(SubscriptionContext subscriptionContext) {
        StringLiteral stringLiteral = (StringLiteral) subscriptionContext.syntaxNode();

        if (!stringLiteral.stringElements().isEmpty() && stringLiteral.stringElements().get(0).value().startsWith("\"")){
            subscriptionContext.addIssue(stringLiteral, MESSAGE_RULE);
        }
    }
}
