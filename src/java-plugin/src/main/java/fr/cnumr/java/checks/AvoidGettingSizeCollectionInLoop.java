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


    private final AvoidGettingSizeCollectionInLoop.AvoidGettingSizeCollectionInLoopVisitor visitorInFile = new AvoidGettingSizeCollectionInLoop.AvoidGettingSizeCollectionInLoopVisitor();


    @Override
    public List<Kind> nodesToVisit() {
        return  Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT, Tree.Kind.FOR_STATEMENT, Tree.Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree instanceof ForStatementTree) {
            ForStatementTree forStatementTree = (ForStatementTree) tree;
            ExpressionTree expressionTree = forStatementTree.condition();
            System.out.println("OK");
        }
        tree.accept(visitorInFile);
    }

    private class AvoidGettingSizeCollectionInLoopVisitor extends BaseTreeVisitor {
        @Override
        public void visitForStatement(ForStatementTree tree) {
            ExpressionTree expression = tree.condition();


            super.visitForStatement(tree);
        }


    }
}
