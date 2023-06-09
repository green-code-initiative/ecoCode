package fr.greencodeinitiative.java.checks;

import java.util.Collections;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC67")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S67")
public class IncrementCheck extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Use ++i instead of i++";

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.POSTFIX_INCREMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        reportIssue(tree, MESSAGERULE);
    }
}
