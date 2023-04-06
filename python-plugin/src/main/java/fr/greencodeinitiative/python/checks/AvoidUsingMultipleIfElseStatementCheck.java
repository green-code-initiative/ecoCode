package fr.greencodeinitiative.python.checks;

import com.sun.source.tree.BlockTree;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.*;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Rule(
        key = AvoidUsingMultipleIfElseStatementCheck.RULE_KEY,
        name = "Avoid using try-catch-finally statement",
        description = AvoidUsingMultipleIfElseStatementCheck.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"eco-design", "ecocode"})
public class AvoidUsingMultipleIfElseStatementCheck extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "EC2";
    public static final String DESCRIPTION = "Use a match-case statement instead of multiple if-else if possible";

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.IF_STMT, this::visitNode);
        context.registerSyntaxNodeConsumer(Tree.Kind.FUNCDEF, this::visitFuncDef);
    }
    void visitFuncDef(SubscriptionContext ctx) {
        FunctionDef functionDef = (FunctionDef) ctx.syntaxNode();

        List<Statement> list = functionDef.body().statements();
        int count=0;
        for(int i=0;i<list.size();i++){
            if (list.get(i).is(Tree.Kind.IF_STMT)){
                count++;
                if (count>=2){
                    ctx.addIssue(list.get(i), DESCRIPTION);
                }
            }
        }

    }
    public void visitNode(SubscriptionContext ctx) {
        int count =0;
        IfStatement ifStatement = (IfStatement) ctx.syntaxNode();
        if(ifStatement.elifBranches().size()>=2) {
            ctx.addIssue(ifStatement.firstToken(), DESCRIPTION);
        }
        if(ctx.syntaxNode().is(Tree.Kind.IF_STMT)){
            count++;
        }

    }



}
