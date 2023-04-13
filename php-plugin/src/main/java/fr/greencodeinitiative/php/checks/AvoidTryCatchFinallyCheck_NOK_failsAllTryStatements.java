package fr.greencodeinitiative.php.checks;

import java.util.Collections;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(
        key = AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements.RULE_KEY,
        name = AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements.ERROR_MESSAGE,
        description = AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug", "eco-design", "ecocode"})
@DeprecatedRuleKey(repositoryKey = "gci-php", ruleKey = "S34")
public class AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements extends PHPSubscriptionCheck {

    public static final String RULE_KEY = "EC34";
    public static final String ERROR_MESSAGE = "Avoid using try-catch";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.TRY_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        context().newIssue(this, tree, ERROR_MESSAGE);
    }

}
