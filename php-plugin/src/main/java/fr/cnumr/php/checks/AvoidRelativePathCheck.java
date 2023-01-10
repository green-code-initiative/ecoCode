package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.tree.expression.FunctionCallTree;
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
    private static final String REG_EXP_INClUDE_REQUIRE_TYPES = ".*include.*|.*require.*|.*require_once.*|.*include_once.*";
    private static final String REG_EXP_FILTER = ".*__DIR__.*|.*__FILE__.*|.* . .*";

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
        if(functionCallTree.callee().toString().matches(REG_EXP_INClUDE_REQUIRE_TYPES)){

                ExpressionTree expressionTree =  functionCallTree.callArguments().get(0).value();
                 if(expressionTree.toString().indexOf('(') != -1){
                     parenthesisedExpressionTreeCheck(expressionTree,functionCallTree);
                }else{
                     expressionTreeCheck(expressionTree,functionCallTree);
                }
        }
    }
    public void parenthesisedExpressionTreeCheck(ExpressionTree expressionTree,FunctionCallTree functionCallTree){
        ParenthesisedExpressionTree parenthesisedExpressionTree = (ParenthesisedExpressionTree) expressionTree;
        if(!parenthesisedExpressionTree.expression().toString().matches(REG_EXP_FILTER) && !parenthesisedExpressionTree.expression().toString().startsWith("\"/")){
            report(functionCallTree);
        }
    }
    public void expressionTreeCheck(ExpressionTree expressionTree,FunctionCallTree functionCallTree){
        if(!expressionTree.toString().matches(REG_EXP_FILTER) && !expressionTree.toString().startsWith("\"/")){
            report(functionCallTree);
        }
    }

    private void report(FunctionCallTree functionCallTree) {
        context().newIssue(this, functionCallTree, ERROR_MESSAGE);
    }
    }
