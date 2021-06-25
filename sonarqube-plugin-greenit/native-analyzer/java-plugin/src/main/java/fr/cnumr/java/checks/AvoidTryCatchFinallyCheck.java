package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "S34",
        name = "Developpement",
        description = "Eviter d'utiliser try-catch-finally",
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidTryCatchFinallyCheck extends IssuableSubscriptionVisitor {
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.TRY_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        TryStatementTree tryStatementTree = (TryStatementTree) tree;
            reportIssue(tree, "Eviter d'utiliser try-catch-finally");

    }
}
