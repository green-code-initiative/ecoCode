package fr.greencodeinitiative.java.checks;

import java.util.Collections;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC32")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "GRSP0032")
public class InitializeBufferWithAppropriateSize extends IssuableSubscriptionVisitor {

    protected static final String RULE_MESSAGE = "Initialize StringBuilder or StringBuffer with appropriate size";

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.NEW_CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        NewClassTree newClassTree = (NewClassTree) tree;
        if ((newClassTree.symbolType().is("java.lang.StringBuffer")
                || newClassTree.symbolType().is("java.lang.StringBuilder"))
                && newClassTree.arguments().isEmpty()) {
            reportIssue(tree, RULE_MESSAGE);
        }
    }
}
