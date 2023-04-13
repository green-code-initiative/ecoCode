/*
 * SonarQube PHP Custom Rules Example
 * Copyright (C) 2016-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package fr.greencodeinitiative.php.checks;

import java.util.Collections;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(
        key = IncrementCheck.RULE_KEY,
        name = IncrementCheck.ERROR_MESSAGE,
        description = IncrementCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug", "eco-design", "ecocode"})
@DeprecatedRuleKey(repositoryKey = "gci-php", ruleKey = "S67")
public class IncrementCheck extends PHPSubscriptionCheck {

    public static final String RULE_KEY = "EC67";
    public static final String ERROR_MESSAGE = "Remove the usage of $i++. prefer ++$i";

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.POSTFIX_INCREMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        context().newIssue(this, tree, ERROR_MESSAGE);
    }

}
