package fr.greencodeinitiative.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.Expression;
import org.sonar.plugins.python.api.tree.ForStatement;
import org.sonar.plugins.python.api.tree.Tree;

@Rule(
        key = "demo_999",
        name = "DEMO - NAME - Use generator expressions instead of list comprehensions",
        description = "DEMO - DESCRIPTION - Wrong use of lists - Please use generator expressions instead of list comprehensions, if you can !!!",
        priority = Priority.MINOR,
        tags = {"eco-design", "ecocode", "performance", "ecocode-demo"})
public class StarWarsCheck extends PythonSubscriptionCheck {

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.FOR_STMT, this::visitIteration);
    }

    private void visitIteration(SubscriptionContext context) {
        ForStatement forStatement = (ForStatement) context.syntaxNode();

        Expression forTestExpression = forStatement.testExpressions().get(0);
        if (forTestExpression.is(Tree.Kind.LIST_COMPREHENSION)) {
            context.addIssue(forTestExpression.firstToken(), "Please use generator expressions instead of list comprehensions, if you can !!!");
        }
    }
}
