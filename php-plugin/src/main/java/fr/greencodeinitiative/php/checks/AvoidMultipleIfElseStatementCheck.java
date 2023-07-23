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

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.declaration.MethodDeclarationTree;
import org.sonar.plugins.php.api.tree.expression.BinaryExpressionTree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.tree.expression.VariableIdentifierTree;
import org.sonar.plugins.php.api.tree.statement.*;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * functional description : please see HTML description file of this rule (resources directory)
 * technical choices :
 * - Kind.IF_STATEMENT, Kind.ELSE_STATEMENT, Kind.ELSEIF_STATEMENT not used because it isn't possible
 * to keep parent references to check later if variables already used or not in parent tree
 * - only one way to keep parent history : manually go throw the all tree and thus, start at method declaration
 * - an "ELSE" statement is considered as a second IF statement using the same variables tests
 *
 */
@Rule(
        key = AvoidMultipleIfElseStatementCheck.RULE_KEY,
        name = AvoidMultipleIfElseStatementCheck.ERROR_MESSAGE,
        description = AvoidMultipleIfElseStatementCheck.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"eco-design", "ecocode", "performance"})
public class AvoidMultipleIfElseStatementCheck extends PHPSubscriptionCheck {

    public static final String RULE_KEY = "EC2";
    public static final String ERROR_MESSAGE = "Use a switch statement instead of multiple if-else if possible";

    private static final Logger LOGGER = Loggers.get(AvoidMultipleIfElseStatementCheck.class);

    private static VariablesPerLevelDataStructure variablesStruct = new VariablesPerLevelDataStructure();

    @Override
    public List<Kind> nodesToVisit() {
        return List.of(Kind.METHOD_DECLARATION);
    }

    @Override
    public void visitNode(Tree tree) {

        MethodDeclarationTree method = (MethodDeclarationTree)tree;

        if (!method.body().is(Kind.BLOCK)) {
            return;
        }

        // reinit data structure before each method analysis
        variablesStruct = new VariablesPerLevelDataStructure();

        visitNodeContent(((BlockTree) method.body()).statements(), 0);

    }

    private void visitNodeContent(List<StatementTree> lstStatements, int pLevel) {
        if (lstStatements == null || lstStatements.isEmpty()) {
            return;
        }

        for (StatementTree statement : lstStatements) {
            if (statement.is(Kind.BLOCK)) {
                LOGGER.debug("BLOCK node - go to child nodes : {}", statement.toString());
                visitNodeContent(((BlockTree)statement).statements(), pLevel); // Block Statement is not a new LEVEL
            } else if (statement.is(Kind.IF_STATEMENT)) {
                LOGGER.debug("Visiting IF_STATEMENT node : {}", statement.toString());
                visitIfNode((IfStatementTree)statement, pLevel);
            } else {
                LOGGER.debug("NO node visit because of incompatibility : {}", statement.toString());
            }
        }
    }

    private void visitIfNode(IfStatementTree pIfTree, int pLevel) {

        if (pIfTree == null) return;

        // analyze condition variables and raise error if needed
        computeConditionVariables(pIfTree, pLevel);

        // analyze ELSEIF clauses
        if (pIfTree.elseifClauses() != null && !pIfTree.elseifClauses().isEmpty()) {
            for (ElseifClauseTree elseifClause : pIfTree.elseifClauses()) {
                visitElseIfNode(elseifClause, pLevel);
            }
        }

        // analyze ELSE clause
        visitElseNode(pIfTree.elseClause(), pLevel);

        // go to next child level of if statement
        visitNodeContent(pIfTree.statements(), pLevel + 1);
    }

    private void computeConditionVariables(IfStatementTree pIfTree, int pLevel) {

        if (pIfTree.condition() == null) return;

        ExpressionTree expr = pIfTree.condition().expression();
        if (expr instanceof BinaryExpressionTree) {
            computeConditionVariables((BinaryExpressionTree) expr, pLevel);
        }

    }

    private void computeConditionVariables(BinaryExpressionTree pBinExprTree, int pLevel) {
        if (pBinExprTree.is(Kind.CONDITIONAL_AND) || pBinExprTree.is(Kind.CONDITIONAL_OR)) {
            if (pBinExprTree.leftOperand() instanceof BinaryExpressionTree) {
                computeConditionVariables((BinaryExpressionTree) pBinExprTree.leftOperand(), pLevel);
            }
            if (pBinExprTree.rightOperand() instanceof BinaryExpressionTree) {
                computeConditionVariables((BinaryExpressionTree) pBinExprTree.rightOperand(), pLevel);
            }
        } else if (pBinExprTree.is(Kind.EQUAL_TO)
                || pBinExprTree.is(Kind.NOT_EQUAL_TO)
                || pBinExprTree.is(Kind.GREATER_THAN_OR_EQUAL_TO)
                || pBinExprTree.is(Kind.LESS_THAN_OR_EQUAL_TO)) {
            if (pBinExprTree.leftOperand().is(Kind.VARIABLE_IDENTIFIER)) {
                computeVariables((VariableIdentifierTree) pBinExprTree.leftOperand(), pLevel);
            }
            if (pBinExprTree.rightOperand().is(Kind.VARIABLE_IDENTIFIER)) {
                computeVariables((VariableIdentifierTree) pBinExprTree.rightOperand(), pLevel);
            }
        }
    }

    private void computeVariables(VariableIdentifierTree pVarIdTree, int pLevel) {
        if (pVarIdTree.variableExpression().is(Kind.VARIABLE_IDENTIFIER)) {
            int nbUsed = variablesStruct.incrementVariableUsageForLevel(pVarIdTree.text(), pLevel);

            // raise an error if maximum
            if (nbUsed > 2) {
                context().newIssue(this, pVarIdTree, ERROR_MESSAGE);
            }
        }
    }

    private void visitElseIfNode(ElseifClauseTree pElseIfTree, int pLevel) {

        if (pElseIfTree == null) { return; }

        // analyze variables and raise error if needed
        computeConditionVariables(pElseIfTree, pLevel);

        // go to next child level
        visitNodeContent(pElseIfTree.statements(), pLevel + 1);
    }

    private void computeConditionVariables(ElseifClauseTree pElseIfTree, int pLevel) {

        if (pElseIfTree.condition() == null) return;

        ExpressionTree expr = pElseIfTree.condition().expression();
        if (expr instanceof BinaryExpressionTree) {
            computeConditionVariables((BinaryExpressionTree) expr, pLevel);
        }

    }

    private void visitElseNode(ElseClauseTree pElseTree, int pLevel) {

        if (pElseTree == null) { return; }

        // analyze variables and raise error if needed
        computeVariables(pElseTree, pLevel);

        // go to next child level
        visitNodeContent(pElseTree.statements(), pLevel + 1);
    }

    private void computeVariables(ElseClauseTree pElseTree, int pLevel) {

        for (Map.Entry<String, Integer> entry : variablesStruct.getVariables(pLevel).entrySet()) {
            String variableName = entry.getKey();

            // increment usage of all varibales in the same level of ELSE staetement
            int nbUsed = variablesStruct.incrementVariableUsageForLevel(variableName, pLevel);

            // raise an error if maximum
            if (nbUsed > 2) {
                context().newIssue(this, pElseTree, ERROR_MESSAGE);
            }
        }
    }

//    private void checkIfStatementAtTheSameLevel(Tree tree) {
//        int countIfStatement = 0;
//
//        Tree parentNode = tree.getParent();
//        if (!(parentNode instanceof BlockTree)) {
//            return;
//        }
//
//        // getting parent bloc to count if several IF at the same level
//        BlockTree node = (BlockTree) parentNode;
//        int sizeBody = node.statements().size();
//        for (int i = 0; i < sizeBody; ++i) {
//            if (node.statements().get(i) instanceof IfStatementTree) {
//                ++countIfStatement;
//            }
//        }
//        if (countIfStatement > 1) {
//            context().newIssue(this, tree, ERROR_MESSAGE);
//        }
//    }
//
//    private void checkElseIfStatement(Tree tree) {
//        String ifTree = tree.toString();
//        String findStr = "elseif";
//        int count = countMatches(ifTree, findStr);
//        if (count >= 2) {
//            context().newIssue(this, tree, ERROR_MESSAGE);
//        }
//    }
//
//    public static int countMatches(String str, String sub) {
//        if (isBlankString(str) || isBlankString(sub)) {
//            return 0;
//        }
//        int count = 0;
//        int idx = 0;
//        while ((idx = str.indexOf(sub, idx)) != INDEX_NOT_FOUND) {
//            count++;
//            idx += sub.length();
//        }
//        return count;
//    }
//
//    public static boolean isBlankString(String str) {
//        return str == null || str.isBlank();
//    }

    /**
     * Complex data structure representing variables count per AST level (cumulative count with parent levels)
     *  Map<Integer, Map<String, Integer>> ==>
     *  - Key : index of Level (0 = first level)
     *  - Value : Map<String, Integer>
     *      - Key : name of variable in the current or parent level
     *      - Value : number of usage of this variable in a IF statement in current level or one of parent levels
     *
     */
    private static class VariablesPerLevelDataStructure {

        private final Map<Integer, Map<String, Integer>> mapVariablesPerLevel;

        public VariablesPerLevelDataStructure() {
            mapVariablesPerLevel = new HashMap<>(10);
        }

        public VariablesPerLevelDataStructure(Map<Integer, Map<String, Integer>> pParentLevelMap) {
            mapVariablesPerLevel = Map.copyOf(pParentLevelMap);
        }

        public int incrementVariableUsageForLevel(String variableName, int pLevel) {

            // get variable usage map for current level
            Map<String, Integer> variablesMap = mapVariablesPerLevel.get(pLevel);
            if (variablesMap == null) {
                variablesMap = new HashMap<>(5);
                mapVariablesPerLevel.put(pLevel, variablesMap);
            }

            // get usage from parent if needed
            Integer nbUsed = variablesMap.get(variableName);
            if (nbUsed == null) {
                Integer nbParentUsed = getVariableUsageOfNearestParent(variableName, pLevel - 1);
                nbUsed = nbParentUsed == null ? 0 : nbParentUsed;
            }

            // increment usage for current level
            nbUsed++;
            variablesMap.put(variableName, nbUsed);

            return nbUsed;
        }

        private Integer getVariableUsageOfNearestParent(String variableName, int pLevel) {

            Integer nbParentUsed = null;
            for (int i = pLevel; i >= 0 && nbParentUsed == null; i--) {
                Map<String, Integer> variablesParentLevelMap = mapVariablesPerLevel.get(i);
                nbParentUsed = variablesParentLevelMap.get(variableName);
            }

            return nbParentUsed;
        }

        public Map<String, Integer> getVariables(int pLevel) {
            return mapVariablesPerLevel.get(pLevel);
        }

//        private Map<String, Integer> initializeAndOrGetVariablesMap(int pLevel) {
//
//            Map<String, Integer> variablesMap = mapVariablesPerLevel.get(pLevel);
//            if (variablesMap == null) {
//                // getting variables map from parent level to copy to current level if initialization needed
//                Map<String, Integer> variablesParentLevelMap = mapVariablesPerLevel.get(pLevel - 1);
//            }
//
//
//            // getting variables map from parent level to copy to current level if initialization needed
//            Map<String, Integer> variablesParentLevelMap = mapVariablesPerLevel.get(pLevel - 1);
//
//            Map<String, Integer> variablesMap = mapVariablesPerLevel.computeIfAbsent(pLevel, k -> new HashMap<>(5));
//
//            // variables map initialization : create empty HashMap if needed
//            if (variablesParentLevelMap != null && !variablesParentLevelMap.isEmpty() && !variablesMap.isEmpty()) {
//                for (Map.Entry<String, Integer> entry : variablesParentLevelMap.entrySet()) {
//                    variablesMap.putIfAbsent(entry.getKey(), entry.getValue());
//                }
//            }
//
//            return variablesMap;
//        }
//
//        private void incrementVariableUsageForExistingChildLevels(String variableName, int level) {
//
//            // variables map initilization if absent
//            Map<String, Integer> mapVariables = mapVariablesPerLevel.computeIfAbsent(level, k -> new HashMap<>(5));
//
//            Integer nbUsed = mapVariables.get(variableName);
//            if (nbUsed == null) {
//                nbUsed = 0;
//            }
//            nbUsed++;
//            mapVariables.put(variableName, nbUsed);
//        }
    }

}
