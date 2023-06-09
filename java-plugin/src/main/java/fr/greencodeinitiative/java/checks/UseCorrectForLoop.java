package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC53")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S53")
public class UseCorrectForLoop extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Avoid the use of Foreach with Arrays";

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {

        ForEachStatement forEachTree = (ForEachStatement) tree;
        if (forEachTree.expression().symbolType().isArray()) {
            reportIssue(tree, MESSAGERULE);
        }
    }
}
