package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@Rule(
        key = "S75",
        name = "Developpement",
        description = AvoidConcatenateStringsInLoop.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"bug"})
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
