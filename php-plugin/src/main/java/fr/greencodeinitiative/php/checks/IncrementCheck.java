package fr.greencodeinitiative.php.checks;

import java.util.Collections;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(
        key = IncrementCheck.RULE_KEY,
        name = IncrementCheck.ERROR_MESSAGE,
        description = IncrementCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"performance", "eco-design", "ecocode"})
@DeprecatedRuleKey(repositoryKey = "gci-php", ruleKey = "S67")
public class IncrementCheck extends PHPSubscriptionCheck {

    public static final String RULE_KEY = "EC67";
    public static final String ERROR_MESSAGE = "Remove the usage of $i++. prefer ++$i";

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.POSTFIX_INCREMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        context().newIssue(this, tree, ERROR_MESSAGE);
    }

}
