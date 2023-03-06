package fr.greencodeinitiative.php.checks;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.expression.LiteralTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

@Rule(
        key = "S74",
        name = "Developpement",
        description = AvoidFullSQLRequestCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"}
)

public class AvoidFullSQLRequestCheck extends PHPSubscriptionCheck {

    public static final String ERROR_MESSAGE = "Don't use the query SELECT * FROM";

    private static final Pattern REGEXPSELECTFROM = Pattern.compile("(?i).*select.*\\*.*from.*");

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.REGULAR_STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {

        LiteralTree literal = (LiteralTree) tree;
        if (REGEXPSELECTFROM.matcher(literal.value()).matches()) {
            context().newIssue(this, tree, ERROR_MESSAGE);
        }
    }
}
