package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC75")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S75")
public class AvoidConcatenateStringsInLoop extends IssuableSubscriptionVisitor {

    public static final String MESSAGE_RULE = "Don't concatenate Strings in loop, use StringBuilder instead.";
    private static final String STRING_CLASS = String.class.getName();

    private final StringConcatenationVisitor VISITOR = new StringConcatenationVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(
                Tree.Kind.FOR_STATEMENT,
                Tree.Kind.FOR_EACH_STATEMENT,
                Tree.Kind.WHILE_STATEMENT
        );
    }

    @Override
    public void visitNode(@Nonnull Tree tree) {
        tree.accept(VISITOR);
    }

    private class StringConcatenationVisitor extends BaseTreeVisitor {
        @Override
        public void visitBinaryExpression(BinaryExpressionTree tree) {
            if (tree.is(Tree.Kind.PLUS) && isStringType(tree.leftOperand())) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitBinaryExpression(tree);
            }
        }

        @Override
        public void visitAssignmentExpression(AssignmentExpressionTree tree) {
            if (tree.is(Tree.Kind.PLUS_ASSIGNMENT) && isStringType(tree.variable())) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitAssignmentExpression(tree);
            }
        }
    }

    private boolean isStringType(ExpressionTree expressionTree) {
        return expressionTree.symbolType().is(STRING_CLASS);
    }

}
