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
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

/**
 * @deprecated there aren't enough good arguments and not enough green measures / benchmarks
 * (check discussion on https://github.com/green-code-initiative/ecoCode/issues/240)
 */
@Deprecated(forRemoval = true)
@Rule(key = "EC53")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S53")
public class UseCorrectForLoop extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Avoid the use of Foreach with Arrays";

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {

        ForEachStatement forEachTree = (ForEachStatement) tree;
        if (forEachTree.expression().symbolType().isArray()) {
            reportIssue(tree, MESSAGERULE);
        }
    }
}
