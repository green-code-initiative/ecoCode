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
 * Technical choices :
 * - Kind.IF_STATEMENT, Kind.ELSE_STATEMENT, Kind.ELSEIF_STATEMENT not used because it isn't possible
 * to keep parent references to check later if variables already used or not in parent tree
 * - only one way to keep parent history : manually go throw the all tree and thus, start at method declaration
 * - an "ELSE" statement is considered as a second IF statement using the same variables used on previous
 * IF and ELSEIF statements
 *
 */
@Rule(key = "EC2")
public class AvoidMultipleIfElseStatementCheck extends PHPSubscriptionCheck {

    public static final String ERROR_MESSAGE = "Use a switch statement instead of multiple if-else if possible";

    private static final Logger LOGGER = Loggers.get(AvoidMultipleIfElseStatementCheck.class);

    private VariablesPerLevelDataStructure variablesStruct = new VariablesPerLevelDataStructure();

    // only visit each method to keep data of all conditional tree
    // with IF, ELSE or ELSEID statements, we can't keep all data of conditionzl tree
    @Override
    public List<Kind> nodesToVisit() {
        return List.of(Kind.METHOD_DECLARATION);
    }

    @Override
    public void visitNode(@SuppressWarnings("NullableProblems") Tree tree) {

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

        // init current if structure with cleaning child levels
        variablesStruct.reinitVariableUsageForLevel(pLevel + 1);
        // init current if structure with cleaning for else verification
        variablesStruct.reinitVariableUsageForLevelForCurrentIfStruct(pLevel);

        // analyze condition variables and raise error if needed
        computeIfVariables(pIfTree, pLevel);

        // visit the content of if block
        visitNodeContent(pIfTree.statements(), pLevel + 1);

        // analyze ELSEIF clauses
        if (pIfTree.elseifClauses() != null && !pIfTree.elseifClauses().isEmpty()) {
            for (ElseifClauseTree elseifClause : pIfTree.elseifClauses()) {
                visitElseIfNode(elseifClause, pLevel);
            }
        }

        // analyze ELSE clause
        visitElseNode(pIfTree.elseClause(), pLevel);

    }

    private void computeIfVariables(IfStatementTree pIfTree, int pLevel) {

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
            // add 1 variable count to list of all variables
            int nbUsed = variablesStruct.incrementVariableUsageForLevel(pVarIdTree.text(), pLevel);

            // add 1 variable count to list of variables already declared for current if or elseif struture
            variablesStruct.incrementVariableUsageForLevelForCurrentIfStruct(pVarIdTree.text(), pLevel);

            // raise an error if maximum
            if (nbUsed > 2) {
                context().newIssue(this, pVarIdTree, ERROR_MESSAGE);
            }
        }
    }

    private void visitElseIfNode(ElseifClauseTree pElseIfTree, int pLevel) {

        if (pElseIfTree == null) { return; }

        // init current if structure with cleaning child levels
        variablesStruct.reinitVariableUsageForLevel(pLevel + 1);
        // init current if structure with cleaning for else verification
        variablesStruct.reinitVariableUsageForLevelForCurrentIfStruct(pLevel);

        // analyze variables and raise error if needed
        computeElseIfVariables(pElseIfTree, pLevel);

        // go to next child level
        visitNodeContent(pElseIfTree.statements(), pLevel + 1);
    }

    private void computeElseIfVariables(ElseifClauseTree pElseIfTree, int pLevel) {

        if (pElseIfTree.condition() == null) return;

        ExpressionTree expr = pElseIfTree.condition().expression();
        if (expr instanceof BinaryExpressionTree) {
            computeConditionVariables((BinaryExpressionTree) expr, pLevel);
        }

    }

    private void visitElseNode(ElseClauseTree pElseTree, int pLevel) {

        if (pElseTree == null) { return; }

        // analyze variables and raise error if needed
        computeElseVariables(pElseTree, pLevel);

        // go to next child level
        visitNodeContent(pElseTree.statements(), pLevel + 1);
    }

    private void computeElseVariables(ElseClauseTree pElseTree, int pLevel) {

        for (Map.Entry<String, Integer> entry : variablesStruct.getVariablesForCurrentIfStruct(pLevel).entrySet()) {
            String variableName = entry.getKey();

            // increment usage of all variables in the same level of ELSE staetement
            int nbUsed = variablesStruct.incrementVariableUsageForLevel(variableName, pLevel);

            variablesStruct.incrementVariableUsageForLevelForCurrentIfStruct(variableName, pLevel);

            // raise an error if maximum
            if (nbUsed > 2) {
                context().newIssue(this, pElseTree, ERROR_MESSAGE);
            }
        }
    }

    /**
     * Complex data structure representing variables count per AST level (cumulative count with parent levels)
     *  Map<Integer, Map<String, Integer>> ==>
     *  - Key : index of Level (0 = first level)
     *  - Value : Map<String, Integer>
     *      - Key : name of variable in the current or parent level
     *      - Value : number of usage of this variable in an IF statement in current level or one of parent levels
     *
     */
    private static class VariablesPerLevelDataStructure {

        private final Map<Integer, Map<String, Integer>> mapVariablesPerLevel;

        private final Map<Integer, Map<String, Integer>> mapVariablesPerLevelForCurrentIfStruct;

        public VariablesPerLevelDataStructure() {
            mapVariablesPerLevel = new HashMap<>(10);
            mapVariablesPerLevelForCurrentIfStruct = new HashMap<>(10);
        }

        public int incrementVariableUsageForLevel(String variableName, int pLevel) {
            return internalIncrementVariableUsage(mapVariablesPerLevel, variableName, pLevel);
        }

        private int internalIncrementVariableUsage(Map<Integer, Map<String, Integer>> pDataMap, String variableName, int pLevel) {

            // get variable usage map for current level and init if null
            Map<String, Integer> variablesMap = pDataMap.computeIfAbsent(pLevel, k -> new HashMap<>(5));

            // get usage from parent if needed
            Integer nbUsed = variablesMap.get(variableName);
            if (nbUsed == null) {
                Integer nbParentUsed = internalGetVariableUsageOfNearestParent(pDataMap, variableName, pLevel - 1);
                nbUsed = nbParentUsed == null ? 0 : nbParentUsed;
            }

            // increment usage for current level
            nbUsed++;
            variablesMap.put(variableName, nbUsed);

            return nbUsed;
        }

        private Integer internalGetVariableUsageOfNearestParent(Map<Integer, Map<String, Integer>> pDataMap, String variableName, int pLevel) {

            Integer nbParentUsed = null;
            for (int i = pLevel; i >= 0 && nbParentUsed == null; i--) {
                Map<String, Integer> variablesParentLevelMap = pDataMap.get(i);
                nbParentUsed = variablesParentLevelMap.get(variableName);
            }

            return nbParentUsed;
        }

        public void reinitVariableUsageForLevel(int pLevel) {
            internalReinitVariableUsageForLevelForCurrentIfStruct(mapVariablesPerLevel, pLevel);
        }

        private void internalReinitVariableUsageForLevelForCurrentIfStruct(Map<Integer, Map<String, Integer>> pDataMap, int pLevel) {
            if (pDataMap.get(pLevel) == null) { return; }

            // cleaning of current If Structure beginning at level specified
            for (int i = pLevel; i < pDataMap.size(); i++) {
                pDataMap.remove(i);
            }

        }

        public void reinitVariableUsageForLevelForCurrentIfStruct(int pLevel) {
            internalReinitVariableUsageForLevelForCurrentIfStruct(mapVariablesPerLevelForCurrentIfStruct, pLevel);
        }

        public void incrementVariableUsageForLevelForCurrentIfStruct(String variableName, int pLevel) {
            internalIncrementVariableUsage(mapVariablesPerLevelForCurrentIfStruct, variableName, pLevel);
        }

        public Map<String, Integer> getVariablesForCurrentIfStruct(int pLevel) {
            return mapVariablesPerLevelForCurrentIfStruct.get(pLevel);
        }

    }

}
