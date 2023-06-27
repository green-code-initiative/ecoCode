package fr.greencodeinitiative.java.checks;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC77")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S77")
public class AvoidRegexPatternNotStatic extends IssuableSubscriptionVisitor {

    public static final String MESSAGE_RULE = "Avoid using Pattern.compile() in a non-static context.";

    private static final MethodMatchers PATTERN_COMPILE = MethodMatchers.create()
            .ofTypes(Pattern.class.getName())
            .names("compile")
            .withAnyParameters()
            .build();

    private final AvoidRegexPatternNotStaticVisitor visitor = new AvoidRegexPatternNotStaticVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD);
    }

    @Override
    public void visitNode(@Nonnull Tree tree) {
        if (tree instanceof MethodTree) {
            final MethodTree methodTree = (MethodTree) tree;

            if (!methodTree.is(Tree.Kind.CONSTRUCTOR)) {
                methodTree.accept(visitor);
            }
        }
    }

    private class AvoidRegexPatternNotStaticVisitor extends BaseTreeVisitor {

        @Override
        public void visitMethodInvocation(@Nonnull MethodInvocationTree tree) {
            if (PATTERN_COMPILE.matches(tree)) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitMethodInvocation(tree);
            }
        }

    }
}
