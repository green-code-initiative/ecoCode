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

import com.google.re2j.Pattern;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

import java.util.Arrays;
import java.util.List;

/**
 * Check to avoid using global variables.
 *
 * @deprecated because not applicable to Java language, to be removed soon
 */
@Deprecated(forRemoval = true)
@Rule(key = "EC4")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "D4")
public class AvoidUsingGlobalVariablesCheck extends IssuableSubscriptionVisitor {

    private static final String ERROR_MESSAGE = "Avoid using global variables";
    private static final Pattern PATTERN = Pattern.compile("^.*(static).*$", Pattern.CASE_INSENSITIVE);

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.STATIC_INITIALIZER, Kind.VARIABLE, Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Kind.STATIC_INITIALIZER)) {
            reportIssue(tree, String.format(ERROR_MESSAGE, tree));
        }
        if (tree.is(Kind.VARIABLE)) {
            VariableTree variableTree = (VariableTree) tree;
            int modifiersSize = (variableTree).modifiers().modifiers().size();
            for (int i = 0; i < modifiersSize; i++) {
                String modifier = ((VariableTree) tree).modifiers().modifiers().get(i).modifier().toString();
                if (PATTERN.matcher(modifier).matches()) {
                    reportIssue(tree, String.format(ERROR_MESSAGE, modifier));
                }
            }
        }
    }

}
