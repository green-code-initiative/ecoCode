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
import org.sonar.plugins.java.api.tree.*;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC1")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "GRC1")
public class AvoidSpringRepositoryCallInLoopOrStreamCheck extends IssuableSubscriptionVisitor {

    protected static final String RULE_MESSAGE = "Avoid Spring repository call in loop or stream";

    private static final String BASE_STREAM = "java.util.stream.BaseStream";
    private static final String SPRING_REPOSITORY = "org.springframework.data.repository.Repository";

    private static final MethodMatchers SPRING_REPOSITORY_METHOD =
            MethodMatchers
                .create()
                .ofSubTypes(SPRING_REPOSITORY)
                .anyName()
                .withAnyParameters()
                .build();

    private static final MethodMatchers STREAM_FOREACH_METHOD =
            MethodMatchers
                .create()
                .ofSubTypes(BASE_STREAM)
                .names("forEach", "forEachOrdered", "map", "peek")
                .withAnyParameters()
                .build();

    private final AvoidSpringRepositoryCallInLoopCheckVisitor visitorInFile = new AvoidSpringRepositoryCallInLoopCheckVisitor();
    private final StreamVisitor streamVisitor = new StreamVisitor();

    private final AncestorMethodVisitor ancestorMethodVisitor = new AncestorMethodVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(
                Tree.Kind.FOR_EACH_STATEMENT, // loop
                Tree.Kind.FOR_STATEMENT, // loop
                Tree.Kind.WHILE_STATEMENT, // loop
                Tree.Kind.DO_STATEMENT, // loop
                Tree.Kind.METHOD_INVOCATION // stream
        );
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.METHOD_INVOCATION)) { // stream process
            MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
            if (STREAM_FOREACH_METHOD.matches(methodInvocationTree)) {
                tree.accept(streamVisitor);
            }
        } else { // loop process
            tree.accept(visitorInFile);
        }
    }

    private class AvoidSpringRepositoryCallInLoopCheckVisitor extends BaseTreeVisitor {
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            if (SPRING_REPOSITORY_METHOD.matches(tree)) {
                reportIssue(tree, RULE_MESSAGE);
            } else {
                super.visitMethodInvocation(tree);
            }
        }

    }

    private class StreamVisitor extends BaseTreeVisitor {

        @Override
        public void visitLambdaExpression(LambdaExpressionTree tree) {
            tree.accept(ancestorMethodVisitor);
        }

    }

    private class AncestorMethodVisitor extends BaseTreeVisitor {

        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            // if the method is a spring repository method, report an issue
            if (SPRING_REPOSITORY_METHOD.matches(tree)) {
                reportIssue(tree, RULE_MESSAGE);
            } else { // else, check if the method is a method invocation and check recursively
                if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
                    MemberSelectExpressionTree memberSelectTree = (MemberSelectExpressionTree) tree.methodSelect();
                    if ( memberSelectTree.expression().is(Tree.Kind.METHOD_INVOCATION)) {
                        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) memberSelectTree.expression();
                        methodInvocationTree.accept(ancestorMethodVisitor);
                    }
                }
            }
        }

    }

}
