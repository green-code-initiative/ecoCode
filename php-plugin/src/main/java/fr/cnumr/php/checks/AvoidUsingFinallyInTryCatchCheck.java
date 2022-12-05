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
import org.sonar.plugins.php.api.tree.statement.TryStatementTree;
import org.sonar.plugins.php.api.visitors.PHPVisitorCheck;

@Rule(
        key = "D1",
        priority = Priority.MAJOR,
        name = "Avoid using finally in try/catch",
        tags = {"bug"})
public class AvoidUsingFinallyInTryCatchCheck extends PHPVisitorCheck  {

    private static final String ERROR_MESSAGE = "Avoid using finally in try/catch";


    @Override
    public void visitTryStatement(TryStatementTree tree) {
        if(tree.finallyToken() != null){
            StringBuilder sb =  new StringBuilder();
            sb.append(tree.finallyToken().toString());
            sb.append(tree.finallyBlock().toString());
            context().newIssue(this, tree.finallyToken(), String.format(ERROR_MESSAGE, sb));

        }
    }




}

