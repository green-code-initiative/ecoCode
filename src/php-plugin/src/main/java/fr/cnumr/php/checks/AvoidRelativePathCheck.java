package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.SeparatedList;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.tree.expression.FunctionCallTree;
import org.sonar.plugins.php.api.tree.expression.LiteralTree;
import org.sonar.plugins.php.api.tree.expression.ParenthesisedExpressionTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.*;

@Rule(
        key = "GRSP0008",
        name = "Developpement",
        description = AvoidRelativePathCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})

public class AvoidRelativePathCheck extends PHPSubscriptionCheck {

    public static final String ERROR_MESSAGE = "Avoid using relative path, prefer using absolute path";
    private static final Map<String, Collection<Integer>> linesWithIssuesByFile = new HashMap<>();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.FUNCTION_CALL);
    }

    @Override
    public void visitNode(Tree tree) {
        FunctionCallTree method = (FunctionCallTree) tree;
        checkIssue(method);
    }

    public void checkIssue(FunctionCallTree functionCallTree) {
        if(functionCallTree.callee().toString().equals("include")){
            ParenthesisedExpressionTree expressionTree = (ParenthesisedExpressionTree) functionCallTree.arguments().get(0);
            if(!expressionTree.expression().toString().startsWith("\"/")){
                repport(functionCallTree);
            return;
            }

        }

    }

    private void repport(FunctionCallTree functionCallTree) {
//        if (literalTree.token() != null) {
//
//            final String classname = context().getPhpFile().toString();
//            final int line = literalTree.token().line();
//            if (!linesWithIssuesByFile.containsKey(classname)) {
//                linesWithIssuesByFile.put(classname, new ArrayList<>());
//            }
//            linesWithIssuesByFile.get(classname).add(line);
//        }
        context().newIssue(this, functionCallTree, ERROR_MESSAGE);

    }

//    private boolean lineAlreadyHasThisIssue(LiteralTree literalTree) {
//        if (literalTree.token() != null) {
//            final String filename = context().getPhpFile().toString();
//            final int line = literalTree.token().line();
//
//            return linesWithIssuesByFile.containsKey(filename)
//                    && linesWithIssuesByFile.get(filename).contains(line);
//        }
//
//        return false;
//    }

}
