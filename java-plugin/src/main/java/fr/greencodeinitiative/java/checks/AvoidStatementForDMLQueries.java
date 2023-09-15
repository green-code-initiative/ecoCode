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

import com.google.re2j.Pattern;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.Arguments;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC5")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "SDMLQ1")
public class AvoidStatementForDMLQueries extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "You must not use Statement for a DML query";

    private static final Pattern PATTERN = Pattern.compile("(SELECT|INSERT INTO|UPDATE|DELETE FROM)\\s?.*", Pattern.CASE_INSENSITIVE);

    private final MethodMatchers EXECUTE_METHOD = MethodMatchers.or(
            MethodMatchers.create().ofSubTypes("java.sql.Statement").names("executeUpdate")
                    .withAnyParameters().build());

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
        if (!EXECUTE_METHOD.matches(methodInvocationTree))
            return;
        Arguments arguments = methodInvocationTree.arguments();
        if (arguments.size() < 1)
            return;
        ExpressionTree first = arguments.get(0);
        if (first.is(Tree.Kind.STRING_LITERAL)) {
            LiteralTree literalTree = (LiteralTree) first;
            String str = literalTree.value();
            if (PATTERN.matcher(str).find())
                reportIssue(literalTree, MESSAGERULE);
        }
    }
}
