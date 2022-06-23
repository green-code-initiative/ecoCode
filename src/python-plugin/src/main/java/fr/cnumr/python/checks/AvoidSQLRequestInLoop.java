package fr.cnumr.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.*;

import java.util.*;
import java.lang.*;

@Rule(
    key = "S64",
    name = "Developpement",
    description = AvoidSQLRequestInLoop.MESSAGERULE,
    priority = Priority.MINOR,
    tags = {"bug"}
)

public class AvoidSQLRequestInLoop extends PythonSubscriptionCheck {

    public static final String MESSAGERULE = "Avoid perform an SQL query inside a loop";
    private static final Map<String, Collection<Integer>> linesWithIssuesByFile = new HashMap<>();

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.FOR_STMT, ctx -> {
            System.out.println("FOR_STMT");
            visitLoopNode(((ForStatement) ctx.syntaxNode()).body(), ctx);
        });
        context.registerSyntaxNodeConsumer(Tree.Kind.WHILE_STMT, ctx -> {
            System.out.println("WHILE_STMT");
            visitLoopNode(((WhileStatement) ctx.syntaxNode()).body(), ctx);
        });
    }

    private void visitLoopNode(StatementList list, SubscriptionContext ctx) {
        for (Statement a: list.statements()) {
            if (a.getKind().equals(Tree.Kind.EXPRESSION_STMT)) {
                ExpressionStatement expression = (ExpressionStatement) a;
                for (Expression i: expression.expressions()) {
                    if (i.is(Tree.Kind.CALL_EXPR))
                        visitCallExpressionNode((CallExpression) i, ctx);
                }
            }
        }
    }

    private void visitCallExpressionNode(CallExpression ce, SubscriptionContext ctx) {
        for (Tree ele : ce.callee().children()) {
            if (ele.getKind().equals(Tree.Kind.NAME)) {
                Name name = (Name) ele;
                if (name.name().equals("execute")) {    
                    for (Argument a: ce.arguments()) {
                        if (checkLitteralInTree((Tree) a))
                            ctx.addIssue(ce, MESSAGERULE);
                    }
                }
            }
        }
    }

    private boolean checkLitteralInTree(Tree t) {
        for (Tree tc : t.children()) {
            if (tc.is(Tree.Kind.STRING_LITERAL)) {
                if (((StringLiteral) tc).trimmedQuotesValue().toUpperCase().contains("SELECT"))
                    return true;
            }
            if (checkLitteralInTree(tc))
                    return true;
        }
        return false;
    }

}
