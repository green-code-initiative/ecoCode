package fr.cnumr.python.checks;

import java.util.List;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.tree.AnyParameter;
import org.sonar.plugins.python.api.tree.AssignmentStatement;
import org.sonar.plugins.python.api.tree.FunctionDef;
import org.sonar.plugins.python.api.tree.ParameterList;
import org.sonar.plugins.python.api.tree.QualifiedExpression;
import org.sonar.plugins.python.api.tree.ReturnStatement;
import org.sonar.plugins.python.api.tree.Statement;
import org.sonar.plugins.python.api.tree.StatementList;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonar.plugins.python.api.SubscriptionContext;

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
            if (functionDef.parent().parent().is(Tree.Kind.CLASSDEF)) {
                checkAllGetters(statements,functionDef,ctx);
                checkAllSetters(statements,functionDef,ctx);
            }
        });
    }
    public void checkAllSetters(List<Statement> statements,FunctionDef functionDef,SubscriptionContext ctx){
        if(statements.size() == 1 && statements.get(0).is(Tree.Kind.ASSIGNMENT_STMT)){
            AssignmentStatement assignmentStatement = (AssignmentStatement) statements.get(0);
            if(checkIfStatementIsQualifiedExpressionAndStartsWithSelfDot((QualifiedExpression) assignmentStatement.children().get(0).children().get(0))){
                // Check if assignedValue is a parameter of the function
                    ParameterList parameters =  functionDef.parameters();
                    if(parameters != null && !parameters.all().stream().filter(p -> checkAssignementFromParameter(assignmentStatement, p)).collect(Collectors.toList()).isEmpty()){
                        ctx.addIssue(functionDef.defKeyword(), AvoidGettersAndSetters.DESCRIPTION);
                }
            }   
        }
    }
    public void checkAllGetters(List<Statement> statements,FunctionDef functionDef,SubscriptionContext ctx){
        Statement lastStatement = statements.get(statements.size() - 1);
        if (lastStatement.is(Tree.Kind.RETURN_STMT)) {
            List<Tree> returnStatementChildren = ((ReturnStatement) lastStatement).children();
            if (returnStatementChildren.get(1).is(Tree.Kind.QUALIFIED_EXPR) &&
                    checkIfStatementIsQualifiedExpressionAndStartsWithSelfDot((QualifiedExpression) returnStatementChildren.get(1))){
                ctx.addIssue(functionDef.defKeyword(), AvoidGettersAndSetters.DESCRIPTION);
            }
        }
    }
    public boolean checkAssignementFromParameter(AssignmentStatement assignmentStatement, AnyParameter parameter){
        String parameterToString = parameter.firstToken().value();
        return assignmentStatement.assignedValue().firstToken().value().equalsIgnoreCase(parameterToString);
    }

    public boolean checkIfStatementIsQualifiedExpressionAndStartsWithSelfDot(QualifiedExpression qualifiedExpression){
        List<Tree> qualifedExpressionChildren = qualifiedExpression.children();
        return qualifedExpressionChildren.size() == 3 &&
            qualifedExpressionChildren.get(0).firstToken().value().equalsIgnoreCase("self") &&
            qualifedExpressionChildren.get(1).firstToken().value().equalsIgnoreCase(".");
    }
}
