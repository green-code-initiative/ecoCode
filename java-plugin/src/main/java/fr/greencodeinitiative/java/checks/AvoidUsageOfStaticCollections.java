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
import java.util.Map;

import javax.annotation.Nonnull;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC76")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S76")
public class AvoidUsageOfStaticCollections extends IssuableSubscriptionVisitor {

    protected static final String MESSAGE_RULE = "Avoid usage of static collections.";

    private final AvoidUsageOfStaticCollectionsVisitor visitor = new AvoidUsageOfStaticCollectionsVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(
                Tree.Kind.VARIABLE
        );
    }

    @Override
    public void visitNode(@Nonnull Tree tree) {
        tree.accept(visitor);
    }

    private class AvoidUsageOfStaticCollectionsVisitor extends BaseTreeVisitor {

        @Override
        public void visitVariable(@Nonnull VariableTree tree) {
            if (tree.symbol().isStatic() &&
                    (tree.type().symbolType().isSubtypeOf(Iterable.class.getName()) ||
                            tree.type().symbolType().is(Map.class.getName()))
            ) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitVariable(tree);
            }
        }

    }

}
