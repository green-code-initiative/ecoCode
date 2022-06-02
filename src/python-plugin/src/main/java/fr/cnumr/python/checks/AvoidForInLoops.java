package fr.cnumr.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.ForStatement;
import org.sonar.plugins.python.api.tree.StatementList;
import org.sonar.plugins.python.api.tree.Tree;

@Rule(
        key = "D6",
        name = "Developpement",
        description = AvoidForInLoops.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"smell"})

public class AvoidForInLoops extends PythonSubscriptionCheck {

	protected static final String MESSAGE_RULE = "Don't use for in a loop";
    protected static final int DEFAULT_MAX_DEPTH = 3;
    @RuleProperty(
        key = "maxDepth",
        description = "The maximum authorized depth.",
        defaultValue = "" + DEFAULT_MAX_DEPTH)
      public int maxDepth = DEFAULT_MAX_DEPTH;

	@Override
	public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.FOR_STMT, this::check);
        context.registerSyntaxNodeConsumer(Tree.Kind.WHILE_STMT, this::check);
    }

    public void check(SubscriptionContext ctx) {
        Tree tree = ctx.syntaxNode();
        if(getNumberOfLoopParent(tree) >= maxDepth){
            ctx.addIssue(tree, AvoidForInLoops.MESSAGE_RULE);
        };
    }

    private int getNumberOfLoopParent(Tree tree) {
        int numberOfLoopParent = 1;
        for (Tree parent = tree.parent(); parent != null; parent = parent.parent()) {
            Tree.Kind kind = parent.getKind();
            if (kind == Tree.Kind.FOR_STMT || kind == Tree.Kind.WHILE_STMT) {
                numberOfLoopParent++;
            }
        }
        return numberOfLoopParent;
    }
}