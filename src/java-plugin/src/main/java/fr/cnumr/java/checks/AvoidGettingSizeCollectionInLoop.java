package fr.cnumr.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(key = "GSCIL",
        name = "Developpement",
        description = AvoidGettingSizeCollectionInLoop.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug" })
public class AvoidGettingSizeCollectionInLoop extends IssuableSubscriptionVisitor {
    protected static final String MESSAGERULE = "Avoid getting the size of the collection in the loop";
    private static final MethodMatchers SIZE_METHOD = MethodMatchers.or(
            MethodMatchers.create()
                    .ofAnyType()
                    .names("size", "length")
                    .withAnyParameters()
                    .build()
    );
    private final AvoidGettingSizeCollectionInLoop.AvoidGettingSizeCollectionInLoopVisitor visitorInFile = new AvoidGettingSizeCollectionInLoop.AvoidGettingSizeCollectionInLoopVisitor();

    @Override
    public List<Kind> nodesToVisit() {
        return  Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT, Tree.Kind.FOR_STATEMENT, Tree.Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
            ForStatementTree forStatementTree = (ForStatementTree) tree;
            BinaryExpressionTree expressionTree = (BinaryExpressionTree) forStatementTree.condition();
            expressionTree.accept(visitorInFile);
    }

    private class AvoidGettingSizeCollectionInLoopVisitor extends BaseTreeVisitor {
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            if (SIZE_METHOD.matches(tree.symbol())) {
                reportIssue(tree, MESSAGERULE);
            } else {
                super.visitMethodInvocation(tree);
            }
        }
    }
}
