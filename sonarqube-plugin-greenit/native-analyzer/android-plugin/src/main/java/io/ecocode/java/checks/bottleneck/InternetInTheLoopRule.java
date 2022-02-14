package io.ecocode.java.checks.bottleneck;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * Check if the method openConnection of the Url class is called inside a loop.
 * Not thrown if openConnection() call is deported into an other method.
 */
@Rule(key = "EBOT001", name = "ecocodeInternetInTheLoop")
public class InternetInTheLoopRule extends IssuableSubscriptionVisitor {

    private static final String ERROR_MESSAGE = "Internet connection should not be opened in loops to preserve the battery.";
    private static final String METHOD_NAME = "openConnection";
    private static final String METHOD_OWNER_TYPE = "java.net.URL";
    private static final MethodMatchers METHOD_MATCHER = MethodMatchers.create().ofTypes(METHOD_OWNER_TYPE).names(METHOD_NAME).withAnyParameters().build();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        if (METHOD_MATCHER.matches(mit)) {
            reportIssueIfInLoop(mit, mit);
        }
    }

    private void reportIssueIfInLoop(Tree treeToCheck, Tree issueTree) {
        if (treeToCheck.is(Tree.Kind.FOR_STATEMENT)
                || treeToCheck.is(Tree.Kind.WHILE_STATEMENT)
                || treeToCheck.is(Tree.Kind.DO_STATEMENT)
                || treeToCheck.is(Tree.Kind.FOR_EACH_STATEMENT)) {
            // Tree is in a loop, report issue
            reportIssue(issueTree, ERROR_MESSAGE);
        } else if (treeToCheck.is(Tree.Kind.COMPILATION_UNIT)) {
            // Top of the tree, exit
        } else {
            // parent is not a loop, continue on next parent
            reportIssueIfInLoop(treeToCheck.parent(), issueTree);
        }
    }
}