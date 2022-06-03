package io.ecocode.java.checks.release;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * Check if a method deleteRecursively is called from android.io.File package.
 */

@Rule(key = "EREL007", name = "ClearCacheRule")
public class ClearCacheRule extends IssuableSubscriptionVisitor {
    //Context.cacheDir.deleteRecursively()
    private final MethodMatchers methodMatcher = MethodMatchers.create().ofTypes("android.io.File").names("deleteRecursively").withAnyParameters().addParametersMatcher().build();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            if (methodMatcher.matches(mit)) {
                //
            }
        }
    }
}
