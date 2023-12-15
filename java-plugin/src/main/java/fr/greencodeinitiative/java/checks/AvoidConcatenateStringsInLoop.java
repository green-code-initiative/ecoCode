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
import javax.annotation.Nonnull;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

/**
 * @deprecated because not useless since JDK 8
 */
@Deprecated(forRemoval = true)
@Rule(key = "EC75")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S75")
public class AvoidConcatenateStringsInLoop extends IssuableSubscriptionVisitor {

    public static final String MESSAGE_RULE = "Don't concatenate Strings in loop, use StringBuilder instead.";
    private static final String STRING_CLASS = String.class.getName();

    private final StringConcatenationVisitor VISITOR = new StringConcatenationVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(
                Tree.Kind.FOR_STATEMENT,
                Tree.Kind.FOR_EACH_STATEMENT,
                Tree.Kind.WHILE_STATEMENT
        );
    }

    @Override
    public void visitNode(@Nonnull Tree tree) {
        tree.accept(VISITOR);
    }

    private class StringConcatenationVisitor extends BaseTreeVisitor {
        @Override
        public void visitBinaryExpression(BinaryExpressionTree tree) {
            if (tree.is(Tree.Kind.PLUS) && isStringType(tree.leftOperand())) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitBinaryExpression(tree);
            }
        }

        @Override
        public void visitAssignmentExpression(AssignmentExpressionTree tree) {
            if (tree.is(Tree.Kind.PLUS_ASSIGNMENT) && isStringType(tree.variable())) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitAssignmentExpression(tree);
            }
        }
    }

    private boolean isStringType(ExpressionTree expressionTree) {
        return expressionTree.symbolType().is(STRING_CLASS);
    }

}
