package fr.greencodeinitiative.java.checks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

/**
 * FUNCTIONAL DESCRIPTION : please see ASCIIDOC description file of this rule (inside `ecocode-rules-spcifications`)
 * TECHNICAL CHOICES :
 * - Kind.IF_STATEMENT, Kind.ELSE_STATEMENT, Kind.ELSEIF_STATEMENT not used because it isn't possible
 * to keep parent references to check later if variables already used or not in parent tree
 * - only one way to keep parent history : manually go throw the all tree and thus, start at method declaration
 * - an "ELSE" statement is considered as a second IF statement using the same variables used on previous
 * - IF and ELSEIF statements are considered as an IF statement
 */
@Rule(key = "EC2")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "AMIES")
public class AvoidMultipleIfElseStatement extends IssuableSubscriptionVisitor {

    public static final String ERROR_MESSAGE = "Use a switch statement instead of multiple if-else if possible";

    public static final int NB_MAX_VARIABLE_USAGE = 2;

    // data structure for following usage of variable inside all the AST tree
    private VariablesPerLevelDataStructure variablesStruct = new VariablesPerLevelDataStructure();

    // only visit each method to keep data of all conditional tree
    // with IF, ELSE or ELSEIF statements, we can't keep all data of conditional tree
    @Override
    public List<Kind> nodesToVisit() {
        return List.of(Kind.METHOD);
    }

    @Override
    public void visitNode(@SuppressWarnings("NullableProblems") Tree pTree) {

        MethodTree method = (MethodTree)pTree;

        // reinit data structure before each method analysis
        variablesStruct = new VariablesPerLevelDataStructure();

        // starting visit
        visitNodeContent(method.block().body(), 0);

    }

    /**
     * Visit all content of a node for one level (with its statements list)
     *
     * @param pLstStatements statements list of current node
     * @param pLevel level of current node
     */
    private void visitNodeContent(List<StatementTree> pLstStatements, int pLevel) {
        if (pLstStatements == null || pLstStatements.isEmpty()) {
            return;
        }

        for (StatementTree statement : pLstStatements) {
            if (statement.is(Kind.BLOCK)) {
                // the current node is a block : visit block content
                visitNodeContent(((BlockTree)statement).body(), pLevel);
            } else if (statement.is(Kind.IF_STATEMENT)) {
                visitIfNode((IfStatementTree) statement, pLevel);
            }
        }
    }

    /**
     * Visit an IF type node
     * @param pIfTree the current node (Tree type)
     * @param pLevel the level of node
     */
    private void visitIfNode(IfStatementTree pIfTree, int pLevel) {

        if (pIfTree == null) return;

        // init current if structure with cleaning child levels
        variablesStruct.reinitVariableUsageForLevel(pLevel + 1);
        // init current if structure with cleaning for ELSE process checking
        variablesStruct.reinitVariableUsageForLevelForCurrentIfStruct(pLevel);

        // analyze condition variables and raise error if needed
        computeIfVariables(pIfTree, pLevel);

        // visit the content of if block
        visitNodeContent(((BlockTree)pIfTree.thenStatement()).body(), pLevel + 1);

        // analyze ELSE clause et ELSE IF clauses
        if (pIfTree.elseStatement() != null) {
            if (pIfTree.elseStatement().is(Kind.BLOCK)) { // ELSE clause content
                visitElseNode((BlockTree) pIfTree.elseStatement(), pLevel);
            } else if (pIfTree.elseStatement().is(Kind.IF_STATEMENT)) { // ELSE IF clause
                visitIfNode((IfStatementTree) pIfTree.elseStatement(), pLevel);
            }
        }
    }

    /**
     * Analyze and compute variables usage for IF AST structure
     * @param pIfTree IF node
     * @param pLevel the level of IF node
     */
    private void computeIfVariables(IfStatementTree pIfTree, int pLevel) {

        if (pIfTree.condition() == null) return;

        // analysing content of conditions of IF node
        ExpressionTree expr = pIfTree.condition();
        if (expr instanceof BinaryExpressionTree) {
            computeConditionVariables((BinaryExpressionTree) expr, pLevel);
        }

    }

    /**
     * Analyze and compute variables usage for Expression structure
     * @param pBinExprTree binary expression to analyze
     * @param pLevel The level of binary expression
     */
    private void computeConditionVariables(BinaryExpressionTree pBinExprTree, int pLevel) {

        // if multiple conditions, continue with each part of complex expression
        if (pBinExprTree.is(Kind.CONDITIONAL_AND) || pBinExprTree.is(Kind.CONDITIONAL_OR)) {
            if (pBinExprTree.leftOperand() instanceof BinaryExpressionTree) {
                computeConditionVariables((BinaryExpressionTree) pBinExprTree.leftOperand(), pLevel);
            }
            if (pBinExprTree.rightOperand() instanceof BinaryExpressionTree) {
                computeConditionVariables((BinaryExpressionTree) pBinExprTree.rightOperand(), pLevel);
            }
        } else if (pBinExprTree.is(Kind.EQUAL_TO)
                || pBinExprTree.is(Kind.NOT_EQUAL_TO)
                || pBinExprTree.is(Kind.GREATER_THAN)
                || pBinExprTree.is(Kind.GREATER_THAN_OR_EQUAL_TO)
                || pBinExprTree.is(Kind.LESS_THAN_OR_EQUAL_TO)
                || pBinExprTree.is(Kind.LESS_THAN)
        ) {
            // continue analysis with variables if some key-words are found
            if (pBinExprTree.leftOperand().is(Kind.IDENTIFIER)) {
                computeVariables((IdentifierTree) pBinExprTree.leftOperand(), pLevel);
            }
            if (pBinExprTree.rightOperand().is(Kind.IDENTIFIER)) {
                computeVariables((IdentifierTree) pBinExprTree.rightOperand(), pLevel);
            }
        }
    }

    /**
     * Analyze and compute variables usage for Variable AST structure
     * @param pVarIdTree The Variable AST structure
     * @param pLevel the level of structure
     */
    private void computeVariables(IdentifierTree pVarIdTree, int pLevel) {
        if (pVarIdTree.is(Kind.IDENTIFIER)) {
            // increment the variable counter to list of all variables
            int nbUsed = variablesStruct.incrementVariableUsageForLevel(pVarIdTree.name(), pLevel);

            // increment variable counter to list of variables already declared for current if or elseif struture
            variablesStruct.incrementVariableUsageForLevelForCurrentIfStruct(pVarIdTree.name(), pLevel);

            // raise an error if maximum
            if (nbUsed > NB_MAX_VARIABLE_USAGE) {
                reportIssue(pVarIdTree, ERROR_MESSAGE);
            }
        }
    }

    /**
     * Analyze and compute variables usage for ELSE AST structure
     * @param pElseTree ELSE node
     * @param pLevel the level of ELSE node
     */
    private void visitElseNode(BlockTree pElseTree, int pLevel) {

        if (pElseTree == null) { return; }

        // analyze variables and raise error if needed
        computeElseVariables(pElseTree, pLevel);

        // go to next child level
        visitNodeContent(pElseTree.body(), pLevel + 1);
    }

    /**
     * Analyze and compute variables usage for ELSE AST structure
     * @param pElseTree ELSE node
     * @param pLevel the level of ELSE node
     */
    private void computeElseVariables(StatementTree pElseTree, int pLevel) {

        for (Map.Entry<String, Integer> entry : variablesStruct.getVariablesForCurrentIfStruct(pLevel).entrySet()) {
            String variableName = entry.getKey();

            // increment usage of all variables in the same level of ELSE staetement
            int nbUsed = variablesStruct.incrementVariableUsageForLevel(variableName, pLevel);

            // increment variable counter to list of variables already declared for current if or elseif struture
            variablesStruct.incrementVariableUsageForLevelForCurrentIfStruct(variableName, pLevel);

            // raise an error if maximum
            if (nbUsed > NB_MAX_VARIABLE_USAGE) {
                reportIssue(pElseTree, ERROR_MESSAGE);
            }
        }
    }

    /**
     * Complex data structure representing variables counters per AST level (cumulative counts with parent levels)
     *  Map<Integer, Map<String, Integer>> ==>
     *  - Key : index of Level (0 = first level)
     *  - Value : Map<String, Integer>
     *      - Key : name of variable in the current or parent level
     *      - Value : number of usage of this variable in an IF statement in current level or one of parent levels
     *
     */
    private static class VariablesPerLevelDataStructure {

        // global map variable counters per level
        private final Map<Integer, Map<String, Integer>> mapVariablesPerLevel;

        // map variable counters per level for current If / ElseIf structure
        // purpose : used by compute variables Else process (because Else structure is particular :
        // we don't know previous variables and we need previous If / ElseIf structure to know variables)
        private final Map<Integer, Map<String, Integer>> mapVariablesPerLevelForCurrentIfStruct;

        public VariablesPerLevelDataStructure() {
            mapVariablesPerLevel = new HashMap<>(10);
            mapVariablesPerLevelForCurrentIfStruct = new HashMap<>(10);
        }

        /**
         * increment variable counters on global map
         */
        public int incrementVariableUsageForLevel(String variableName, int pLevel) {
            return internalIncrementVariableUsage(mapVariablesPerLevel, variableName, pLevel);
        }

        /**
         * increment variable counters on input map
         */
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

        /**
         * get usage of a variable in top tree (nearest top parent)
         */
        private Integer internalGetVariableUsageOfNearestParent(Map<Integer, Map<String, Integer>> pDataMap, String variableName, int pLevel) {

            Integer nbParentUsed = null;
            for (int i = pLevel; i >= 0 && nbParentUsed == null; i--) {
                Map<String, Integer> variablesParentLevelMap = pDataMap.get(i);
                nbParentUsed = variablesParentLevelMap.get(variableName);
            }

            return nbParentUsed;
        }

        /**
         * reinitialization of variable usages for input level and global map
         */
        public void reinitVariableUsageForLevel(int pLevel) {
            internalReinitVariableUsageForLevelForCurrentIfStruct(mapVariablesPerLevel, pLevel);
        }

        /**
         * reinitialization of variable usages in input level in input map
         */
        private void internalReinitVariableUsageForLevelForCurrentIfStruct(Map<Integer, Map<String, Integer>> pDataMap, int pLevel) {
            if (pDataMap.get(pLevel) == null) { return; }

            // cleaning of current If Structure beginning at level specified
            for (int i = pLevel; i < pDataMap.size(); i++) {
                pDataMap.remove(i);
            }

        }

        /**
         * reinitialization of variable usages for input level on if/elseif map
         */
        public void reinitVariableUsageForLevelForCurrentIfStruct(int pLevel) {
            internalReinitVariableUsageForLevelForCurrentIfStruct(mapVariablesPerLevelForCurrentIfStruct, pLevel);
        }

        /**
         * increment variable counters on if/elseif map
         */
        public void incrementVariableUsageForLevelForCurrentIfStruct(String variableName, int pLevel) {
            internalIncrementVariableUsage(mapVariablesPerLevelForCurrentIfStruct, variableName, pLevel);
        }

        /**
         * get usage of a variable in a level on if/elseif map
         */
        public Map<String, Integer> getVariablesForCurrentIfStruct(int pLevel) {
            return mapVariablesPerLevelForCurrentIfStruct.get(pLevel);
        }

    }

}
