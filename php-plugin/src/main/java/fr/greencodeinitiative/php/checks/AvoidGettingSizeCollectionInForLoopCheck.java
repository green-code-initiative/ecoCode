package fr.greencodeinitiative.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.SeparatedList;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.BinaryExpressionTree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.tree.expression.ParenthesisedExpressionTree;
import org.sonar.plugins.php.api.tree.statement.*;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Rule(
        key = AvoidGettingSizeCollectionInForLoopCheck.RULE_KEY,
        name = AvoidGettingSizeCollectionInForLoopCheck.ERROR_MESSAGE,
        description = AvoidGettingSizeCollectionInForLoopCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug", "eco-design", "ecocode"})
public class AvoidGettingSizeCollectionInForLoopCheck extends PHPSubscriptionCheck {

    public static final String RULE_KEY = "EC3";
    public static final String ERROR_MESSAGE = "Avoid getting the size of the collection in the loop";
    private static final Pattern PATTERN = Pattern.compile("\\b(?:count|sizeof)\\b");

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.FOR_STATEMENT, Tree.Kind.WHILE_STATEMENT, Tree.Kind.DO_WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.FOR_STATEMENT)) {
            ForStatementTree conditionTree = (ForStatementTree) tree;
            SeparatedList<ExpressionTree> conditions = conditionTree.condition();

            for (ExpressionTree condition : conditions) {
                if (condition instanceof BinaryExpressionTree) {
                    verifyIfThereIsAError(((BinaryExpressionTree) condition).rightOperand().toString(), conditionTree);
                }
            }
        }

        if (tree.is(Tree.Kind.WHILE_STATEMENT)) {
            WhileStatementTree whileTree = (WhileStatementTree) tree;
            ParenthesisedExpressionTree condition = whileTree.condition();
            verifyIfThereIsAError(condition.expression().toString(), whileTree);
        }

        if (tree.is(Tree.Kind.DO_WHILE_STATEMENT)) {
            DoWhileStatementTree doWhileTree = (DoWhileStatementTree) tree;
            ParenthesisedExpressionTree condition = doWhileTree.condition();
            verifyIfThereIsAError(condition.expression().toString(), doWhileTree);
        }
    }

    private void verifyIfThereIsAError(String condition, StatementTree conditionTree) {
          if (PATTERN.matcher(condition).find()) {
              context().newIssue(this, conditionTree, ERROR_MESSAGE);
          }
    }
}
