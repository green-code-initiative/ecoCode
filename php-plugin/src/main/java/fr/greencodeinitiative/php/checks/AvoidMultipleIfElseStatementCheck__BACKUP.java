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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.statement.BlockTree;
import org.sonar.plugins.php.api.tree.statement.IfStatementTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.List;

@Rule(
        key = AvoidMultipleIfElseStatementCheck__BACKUP.RULE_KEY,
        name = AvoidMultipleIfElseStatementCheck__BACKUP.ERROR_MESSAGE,
        description = AvoidMultipleIfElseStatementCheck__BACKUP.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"eco-design", "ecocode", "performance"})
public class AvoidMultipleIfElseStatementCheck__BACKUP extends PHPSubscriptionCheck {

    public static final String RULE_KEY = "EC2";
    public static final String ERROR_MESSAGE = "Use a switch statement instead of multiple if-else if possible";
    public static final int INDEX_NOT_FOUND = -1;

    @Override
    public List<Kind> nodesToVisit() {
        return List.of(Kind.IF_STATEMENT);
//        return List.of(Kind.IF_STATEMENT, Kind.ELSEIF_CLAUSE);
    }

    @Override
    public void visitNode(Tree tree) {
        checkIfStatementAtTheSameLevel(tree);
        checkElseIfStatement(tree);
    }

    private void checkIfStatementAtTheSameLevel(Tree tree) {
        int countIfStatement = 0;

        Tree parentNode = tree.getParent();
        if (!(parentNode instanceof BlockTree)) {
            return;
        }

        // getting parent bloc to count if several IF at the same level
        BlockTree node = (BlockTree) parentNode;
        int sizeBody = node.statements().size();
        for(int i=0; i<sizeBody;++i){
            if (node.statements().get(i) instanceof IfStatementTree){
                ++countIfStatement;
            }
        }
        if (countIfStatement > 1){
            context().newIssue(this, tree, ERROR_MESSAGE);
        }
    }

    private void checkElseIfStatement(Tree tree) {
        String ifTree = tree.toString();
        String findStr = "elseif";
        int count = countMatches(ifTree, findStr);
        if (count >= 2) {
           context().newIssue(this, tree, ERROR_MESSAGE);
        }
    }

    public static int countMatches(String str, String sub) {
        if (isBlankString(str) || isBlankString(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    public static boolean isBlankString(String str) {
        return str == null || str.length() == 0 || str.isBlank();
    }

}
