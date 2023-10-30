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

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC1")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "GRC1")
public class AvoidSpringRepositoryCallInLoopCheck extends IssuableSubscriptionVisitor {

    protected static final String RULE_MESSAGE = "Avoid Spring repository call in loop";

    private static final String SPRING_REPOSITORY = "org.springframework.data.repository.Repository";

    private static final MethodMatchers REPOSITORY_METHOD =
            MethodMatchers.create().ofSubTypes(SPRING_REPOSITORY).anyName().withAnyParameters()
                    .build();

    private final AvoidSpringRepositoryCallInLoopCheckVisitor visitorInFile = new AvoidSpringRepositoryCallInLoopCheckVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT, Tree.Kind.FOR_STATEMENT, Tree.Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        tree.accept(visitorInFile);
    }

    private class AvoidSpringRepositoryCallInLoopCheckVisitor extends BaseTreeVisitor {
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            if (REPOSITORY_METHOD.matches(tree)) {
                reportIssue(tree, RULE_MESSAGE);
            } else {
                super.visitMethodInvocation(tree);
            }
        }

    }
}
