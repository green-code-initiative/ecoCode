package fr.cnumr.python.checks;

import java.util.List;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.tree.FunctionDef;
import org.sonar.plugins.python.api.tree.QualifiedExpression;
import org.sonar.plugins.python.api.tree.ReturnStatement;
import org.sonar.plugins.python.api.tree.Statement;
import org.sonar.plugins.python.api.tree.StatementList;
import org.sonar.plugins.python.api.tree.Tree;

@Rule(
        key = AvoidGettersAndSetters.RULE_KEY,
        name = "Developpement",
        description = AvoidGettersAndSetters.DESCRIPTION,
        priority = Priority.MINOR,
        tags = { "bug" })
public class AvoidGettersAndSetters extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "D7";
    public static final String DESCRIPTION = "Avoid the use of getters and setters";

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.FUNCDEF, ctx -> {
            FunctionDef functionDef = (FunctionDef) ctx.syntaxNode();
            StatementList statementList = functionDef.body();
            List<Statement> statements = statementList.statements();
            if (functionDef.parent().parent().getKind() == Tree.Kind.CLASSDEF) {
                Statement lastStatement = statements.get(statements.size() - 1);
                if (lastStatement.is(Tree.Kind.RETURN_STMT)) {
                    List<Tree> returnStatementChildren = ((ReturnStatement) lastStatement).children();
                    if (returnStatementChildren.get(1).getKind() == Tree.Kind.QUALIFIED_EXPR){
                        List<Tree> qualifedExpressionChildren = ((QualifiedExpression) returnStatementChildren.get(1)).children();
                        if (qualifedExpressionChildren.size() == 3 &&
                            qualifedExpressionChildren.get(0).firstToken().value().equalsIgnoreCase("self") &&
                            qualifedExpressionChildren.get(1).firstToken().value().equalsIgnoreCase(".")){
                                ctx.addIssue(functionDef.defKeyword(), AvoidGettersAndSetters.DESCRIPTION);
                        }
                    }
                }
            }
        });
    }
}