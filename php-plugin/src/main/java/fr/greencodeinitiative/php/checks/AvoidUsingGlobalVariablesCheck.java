package fr.greencodeinitiative.php.checks;

import java.util.regex.Pattern;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.declaration.FunctionDeclarationTree;
import org.sonar.plugins.php.api.visitors.PHPVisitorCheck;

@Rule(
        key = AvoidUsingGlobalVariablesCheck.RULE_KEY,
        name = AvoidUsingGlobalVariablesCheck.ERROR_MESSAGE,
        description = AvoidUsingGlobalVariablesCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug", "eco-design", "ecocode"})
public class AvoidUsingGlobalVariablesCheck extends PHPVisitorCheck {

    public static final String RULE_KEY = "EC4";
    public static final String ERROR_MESSAGE = "Prefer local variables to globals";

    private static final Pattern PATTERN = Pattern.compile("^.*(global \\$|\\$GLOBALS).*$", Pattern.CASE_INSENSITIVE);

    @Override
    public void visitFunctionDeclaration(FunctionDeclarationTree tree) {
        if (PATTERN.matcher(tree.body().toString()).matches()) {
            context().newIssue(this, tree, String.format(ERROR_MESSAGE, tree.body().toString()));
        }
    }
}
