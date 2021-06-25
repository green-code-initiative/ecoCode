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
package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.expression.UnaryExpressionTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;
import org.sonar.plugins.php.api.visitors.PHPVisitorCheck;

import java.util.Collections;
import java.util.List;

/**
 * Example of implementation of a check by extending {@link PHPVisitorCheck}.
 * PHPVisitorCheck provides methods to visit nodes of the Abstract Syntax Tree
 * that represents the source code.
 * <p>
 * Those methods can be overridden to process information
 * related to node and issue can be created via the context that can be
 * accessed through {@link PHPVisitorCheck#context()}.
 */
@Rule(
        key = IncrementCheck.KEY,
        name = "Developpement",
        description = "Remplacer les $i++ par ++$i",
        priority = Priority.MINOR,
        tags = {"bug"})
public class IncrementCheck extends PHPSubscriptionCheck {

    public static final String KEY = "S67";

    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.POSTFIX_INCREMENT);
    }

    // POSTFIX_INCREMENT
    @Override
    public void visitNode(Tree tree) {
        UnaryExpressionTree method = (UnaryExpressionTree) tree;
        /*
                if (returnType.is(firstParameterType.fullyQualifiedName())) {
            reportIssue(method.simpleName(), "Never do that!");
        }
         */
        context().newIssue(this, method, "Remove the usage of this forbidden function.");

    }

}
