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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.JavaVersion;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;


@Rule(key = "EC79")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S79")
public class FreeResourcesOfAutoCloseableInterface extends IssuableSubscriptionVisitor {
    private final Deque<TryStatementTree> withinTry = new LinkedList<>();
    private final Deque<List<Tree>> toReport = new LinkedList<>();

    private static final String JAVA_LANG_AUTOCLOSEABLE = "java.lang.AutoCloseable";
    protected static final String MESSAGE_RULE = "try-with-resources Statement needs to be implemented for any object that implements the AutoClosable interface.";

    @Override
    @ParametersAreNonnullByDefault
    public void leaveFile(JavaFileScannerContext context) {
        withinTry.clear();
        toReport.clear();
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.TRY_STATEMENT, Tree.Kind.NEW_CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.TRY_STATEMENT)) {
            withinTry.push((TryStatementTree) tree);
            if (withinTry.size() != toReport.size()) {
                toReport.push(new ArrayList<>());
            }
        }
        if (tree.is(Tree.Kind.NEW_CLASS) && ((NewClassTree) tree).symbolType().isSubtypeOf(JAVA_LANG_AUTOCLOSEABLE) && withinStandardTryWithFinally()) {
            assert toReport.peek() != null;
            toReport.peek().add(tree);
        }
    }

    @Override
    public void leaveNode(Tree tree) {
        if (tree.is(Tree.Kind.TRY_STATEMENT)) {
            List<Tree> secondaryTrees = toReport.pop();
            if (!secondaryTrees.isEmpty()) {
                reportIssue(tree, MESSAGE_RULE);
            }
        }
    }

    private boolean withinStandardTryWithFinally() {
        if (withinTry.isEmpty() || !withinTry.peek().resourceList().isEmpty()) return false;
        assert withinTry.peek() != null;
        return withinTry.peek().finallyBlock() != null;
    }

    public boolean isCompatibleWithJavaVersion(JavaVersion version) {
        return version.isJava7Compatible();
    }
}
