package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.SeparatedList;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.BinaryExpressionTree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.tree.statement.ForStatementTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.Collections;
import java.util.List;

@Rule(
        key = NoFunctionCallWhenDeclaringForLoop.KEY,
        name = "Developpement",
        description = "Remplacer les $i++ par ++$i",
        priority = Priority.MINOR,
        tags = {"bug"})
public class NoFunctionCallWhenDeclaringForLoop extends PHPSubscriptionCheck {

    public static final String KEY = "S69";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.FOR_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        ForStatementTree method = (ForStatementTree) tree;

        checkExpressionsTree(method.condition());
        checkExpressionsTree(method.update());
        checkExpressionsTree(method.init());
   

    }

    public void checkExpressionsTree(SeparatedList<ExpressionTree> treeSeparatedList) {
        treeSeparatedList.forEach(expressionTree -> {
            //less than or qua to
            checkBothSideExpression(expressionTree);

        });
    }

    public void isFunctionCall(ExpressionTree expressionTree) {
        if (expressionTree.getKind() == Tree.Kind.FUNCTION_CALL) {
            context().newIssue(this, expressionTree,
                    "Ne pas appeler de fonction dans la déclaration d’une boucle de type for");
        }
    }

    public void checkBothSideExpression(ExpressionTree expressionTree) {

        if (expressionTree.getKind().getAssociatedInterface() == BinaryExpressionTree.class) {
            BinaryExpressionTree binaryExpressionTree = (BinaryExpressionTree) expressionTree;
            isFunctionCall(binaryExpressionTree.leftOperand());
            isFunctionCall(binaryExpressionTree.rightOperand());
        } else {
            isFunctionCall(expressionTree);
        }


    }
}
