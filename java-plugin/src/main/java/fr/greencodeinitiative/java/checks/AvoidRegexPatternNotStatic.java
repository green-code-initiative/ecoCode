/*
 * ecoCode - Java language - Provides rules to reduce the environmental footprint of your Java programs
 * Copyright © 2023 Green Code Initiative (https://www.ecocode.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
