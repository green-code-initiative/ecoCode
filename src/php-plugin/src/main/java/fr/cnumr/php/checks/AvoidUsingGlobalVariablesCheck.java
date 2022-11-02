package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.declaration.FunctionDeclarationTree;
import org.sonar.plugins.php.api.visitors.PHPVisitorCheck;

import java.util.regex.Pattern;

@Rule(
        key = "D4",
        name = "Developpement",
        description = AvoidUsingGlobalVariablesCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})

public class AvoidUsingGlobalVariablesCheck extends PHPVisitorCheck {

    public static final String ERROR_MESSAGE = "Prefer local variables to globals";
    public static final String GLOBALS_PATTERN = "^.*(global \\$|\\$GLOBALS).*$";
    private Pattern pattern;
    String format = GLOBALS_PATTERN;
    @Override
    public void init() {
        pattern = Pattern.compile(format,Pattern.CASE_INSENSITIVE);
    }
    @Override
    public void visitFunctionDeclaration(FunctionDeclarationTree tree) {
        if(pattern.matcher(tree.body().toString()).matches()){
            context().newIssue(this, tree, String.format(ERROR_MESSAGE, tree.body().toString()));
        }
    }
}
