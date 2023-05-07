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
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(
        key = AvoidFullSQLRequestCheck.RULE_KEY,
        name = AvoidFullSQLRequestCheck.ERROR_MESSAGE,
        description = AvoidFullSQLRequestCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug", "eco-design", "ecocode"})
@DeprecatedRuleKey(repositoryKey = "gci-php", ruleKey = "S74")
public class AvoidFullSQLRequestCheck extends PHPSubscriptionCheck {

    public static final String RULE_KEY = "EC74";

    public static final String ERROR_MESSAGE = "Don't use the query SELECT * FROM";

    private static final Pattern PATTERN = Pattern.compile("(?i).*select.*\\*.*from.*");

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.REGULAR_STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {

        LiteralTree literal = (LiteralTree) tree;
        if (PATTERN.matcher(literal.value()).matches()) {
            context().newIssue(this, tree, ERROR_MESSAGE);
        }
    }
}
