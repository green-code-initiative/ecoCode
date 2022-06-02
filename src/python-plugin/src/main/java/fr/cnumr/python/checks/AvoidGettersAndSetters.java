package fr.cnumr.python.checks;

import java.util.List;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.tree.FunctionDef;
import org.sonar.plugins.python.api.tree.Statement;
import org.sonar.plugins.python.api.tree.StatementList;
import org.sonar.plugins.python.api.tree.Tree;

@Rule(
        key = AvoidGettersAndSetters.RULE_KEY,
        name = "Developpement",
        description = AvoidGettersAndSetters.DESCRIPTION,
        priority = Priority.MINOR,
        tags = { "bug" }
)

public class AvoidGettersAndSetters extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "D7";
    public static final String DESCRIPTION = "Avoid the use of getters and setters";
    private static final String REGEXRETURNSELF = "^self\\.\\S*$";

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.FUNCDEF, ctx -> {
            FunctionDef functionDef = (FunctionDef) ctx.syntaxNode();
            StatementList statementList = functionDef.body();
            List<Statement> statements = statementList.statements();
            if (functionDef.parent().getKind() == Tree.Kind.CLASSDEF) {
                Statement lastStatement = statements.get(statements.size() - 1);
                if (lastStatement.is(Tree.Kind.RETURN_STMT)) {
                    if (lastStatement.toString().matches(REGEXRETURNSELF)) {
                        ctx.addIssue(functionDef, AvoidGettersAndSetters.DESCRIPTION);
                    }
                }
            }
        });
    }
}