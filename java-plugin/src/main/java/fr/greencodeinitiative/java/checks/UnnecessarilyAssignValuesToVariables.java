package fr.greencodeinitiative.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

import javax.annotation.CheckForNull;
import java.util.*;

@Rule(key = "EC63")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "S63")
public class UnnecessarilyAssignValuesToVariables extends BaseTreeVisitor implements JavaFileScanner {

    protected static final String MESSAGERULE1 = "The variable is declared but not really used";
    protected static final String MESSAGERULE2 = "Immediately throw this expression instead of assigning it to the temporary variable";
    protected static final String MESSAGERULE3 = "Immediately return this expression instead of assigning it to the temporary variable";
    private JavaFileScannerContext context;
    private String errorMessage;
    private final Map<String, VariableTree> variableList = new HashMap<>();
    private static final Map<String, Collection<Integer>> linesWithIssuesByVariable = new HashMap<>();

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitBlock(BlockTree tree) {
        GetVariableVisitor getVariableVisitor = new GetVariableVisitor();
        CheckUseVariableVisitor checkVariable = new CheckUseVariableVisitor();
        super.visitBlock(tree);
        checkImmediatelyReturnedVariable(tree);
        tree.accept(getVariableVisitor);
        tree.accept(checkVariable);

        variableList.forEach(this::reportIfUnknow);
        variableList.clear();
    }

    private void reportIfUnknow(String name, Tree tree) {
        Integer issueLine = tree.firstToken().range().start().line();

        if (!(linesWithIssuesByVariable.containsKey(name) && linesWithIssuesByVariable.get(name).contains(issueLine))) {
            if (!linesWithIssuesByVariable.containsKey(name)) {
                linesWithIssuesByVariable.put(name, new ArrayList<>());
            }

            linesWithIssuesByVariable.get(name).add(issueLine);

            context.reportIssue(this, tree, MESSAGERULE1);
        }
    }

    private class GetVariableVisitor extends BaseTreeVisitor {
        @Override
        public void visitVariable(VariableTree tree) {
            if (!tree.parent().is(Kind.METHOD)) {
                variableList.put(tree.simpleName().name(), tree);
            }
            super.visitVariable(tree);
        }
    }

    private class CheckUseVariableVisitor extends BaseTreeVisitor {

        @Override
        public void visitIfStatement(IfStatementTree tree) {
            variableList.remove(tree.condition().toString());
            super.visitIfStatement(tree);
        }

        @Override
        public void visitUnaryExpression(UnaryExpressionTree tree) {
            variableList.remove(tree.expression().toString());
            super.visitUnaryExpression(tree);
        }

        @Override
        public void visitForEachStatement(ForEachStatement tree) {
            variableList.remove(tree.expression().toString());
            super.visitForEachStatement(tree);
        }

        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            tree.arguments().forEach(e -> {
                if (variableList.containsKey(e.toString())) {
                    variableList.remove(e.toString());
                }
            });
            super.visitMethodInvocation(tree);
        }

        @Override
        public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
            variableList.remove(tree.expression().toString());
            super.visitMemberSelectExpression(tree);
        }

        @Override
        public void visitTypeCast(TypeCastTree tree) {
            variableList.remove(tree.expression().toString());
            super.visitTypeCast(tree);
        }

        @Override
        public void visitBinaryExpression(BinaryExpressionTree tree) {
            if (!tree.operatorToken().is(Kind.ASSIGNMENT)) {
                variableList.remove(tree.leftOperand().toString());
            }
            variableList.remove(tree.rightOperand().toString());
            super.visitBinaryExpression(tree);
        }

        @Override
        public void visitNewClass(NewClassTree tree) {
            tree.arguments().forEach(e -> {
                if (variableList.containsKey(e.toString())) {
                    variableList.remove(e.toString());
                }
            });
            super.visitNewClass(tree);
        }

        @Override
        public void visitReturnStatement(ReturnStatementTree tree) {
            if (tree != null) {
                if (tree.expression() != null) {
                    variableList.remove(tree.expression().toString());
                }
                super.visitReturnStatement(tree);
            }
        }

        @Override
        public void visitThrowStatement(ThrowStatementTree tree) {
            variableList.remove(tree.expression().toString());
            super.visitThrowStatement(tree);
        }

        @Override
        public void visitAssignmentExpression(AssignmentExpressionTree tree) {
            variableList.remove(tree.expression().toString());
            super.visitAssignmentExpression(tree);
        }

    }

    private void checkImmediatelyReturnedVariable(BlockTree tree) {
        List<StatementTree> statements = tree.body();
        int size = statements.size();
        if (size < 2) {
            return;
        }
        StatementTree butLastStatement = statements.get(size - 2);
        if (butLastStatement.is(Kind.VARIABLE)) {
            VariableTree variableTree = (VariableTree) butLastStatement;
            if (!variableTree.modifiers().annotations().isEmpty()) {
                return;
            }
            StatementTree lastStatement = statements.get(size - 1);
            String lastStatementIdentifier = getReturnOrThrowIdentifier(lastStatement);
            if (lastStatementIdentifier != null) {
                String identifier = variableTree.simpleName().name();
                if (lastStatementIdentifier.equals(identifier)) {
                    context.reportIssue(this, variableTree.initializer(), errorMessage);
                }
            }
        }
    }

    @CheckForNull
    private String getReturnOrThrowIdentifier(StatementTree lastStatementOfBlock) {
        errorMessage = null;
        ExpressionTree expr = null;
        if (lastStatementOfBlock.is(Kind.THROW_STATEMENT)) {
            errorMessage = MESSAGERULE2;
            expr = ((ThrowStatementTree) lastStatementOfBlock).expression();
        } else if (lastStatementOfBlock.is(Kind.RETURN_STATEMENT)) {
            errorMessage = MESSAGERULE3;
            expr = ((ReturnStatementTree) lastStatementOfBlock).expression();
        }
        if (expr != null && expr.is(Kind.IDENTIFIER)) {
            return ((IdentifierTree) expr).name();
        }
        return null;
    }

}
