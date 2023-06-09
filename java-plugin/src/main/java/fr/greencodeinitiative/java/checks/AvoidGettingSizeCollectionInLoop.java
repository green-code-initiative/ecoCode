package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.WhileStatementTree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC3")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "GSCIL")
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

    private static final Logger LOGGER = Loggers.get(AvoidGettingSizeCollectionInLoop.class);

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.FOR_STATEMENT, Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        LOGGER.debug("--------------------_____-----_____----- AvoidGettingSizeCollectionInLoop.visitNode METHOD - BEGIN");
        if (tree.is(Kind.FOR_STATEMENT)) {
            LOGGER.debug("ForStatement found");
            ForStatementTree forStatementTree = (ForStatementTree) tree;

            LOGGER.debug("Check if condition is a BinaryExpressionTree");
            if (forStatementTree.condition() instanceof BinaryExpressionTree) {

                LOGGER.debug("Casting condition to BinaryExpressionTree");
                BinaryExpressionTree expressionTree = (BinaryExpressionTree) forStatementTree.condition();
                LOGGER.debug("Checking BinaryExpressionTree content");
                expressionTree.accept(visitorInFile);

            } else {
                LOGGER.debug("Condition isn't a BinaryExpressionTree (real type : {}) => no issue launched", forStatementTree.condition());
            }
        } else if (tree.is(Kind.WHILE_STATEMENT)) {
            LOGGER.debug("WhileStatement found");
            WhileStatementTree whileStatementTree = (WhileStatementTree) tree;

            LOGGER.debug("Check if condition is a BinaryExpressionTree");
            if (whileStatementTree.condition() instanceof BinaryExpressionTree) {

                LOGGER.debug("Casting condition to BinaryExpressionTree");
                BinaryExpressionTree expressionTree = (BinaryExpressionTree) whileStatementTree.condition();
                LOGGER.debug("Checking BinaryExpressionTree content");
                expressionTree.accept(visitorInFile);

            } else {
                LOGGER.debug("Condition isn't a BinaryExpressionTree (real type : {}) => no issue launched");
            }
        } else {
            throw new UnsupportedOperationException("Kind of statement NOT supported - real kind : " + tree.kind().getAssociatedInterface());
        }
        LOGGER.debug("--------------------_____-----_____----- AvoidGettingSizeCollectionInLoop.visitNode METHOD - END");
    }

    private class AvoidGettingSizeCollectionInLoopVisitor extends BaseTreeVisitor {
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            if (SIZE_METHOD.matches(tree.symbol())) {
                LOGGER.debug("sizeMethod found => launching ISSUE !!!");
                reportIssue(tree, MESSAGERULE);
            } else {
                LOGGER.debug("sizeMethod NOT found : bypass and go next");
                super.visitMethodInvocation(tree);
            }
        }
    }
}
