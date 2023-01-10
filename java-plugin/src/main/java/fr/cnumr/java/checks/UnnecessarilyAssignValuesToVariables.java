package fr.cnumr.java.checks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.ThrowStatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TypeCastTree;
import org.sonar.plugins.java.api.tree.UnaryExpressionTree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(key = "S63", name = "Developpement", description = "Do not unnecessarily assign values to variables", priority = Priority.MINOR, tags = {
		"bug" })
public class UnnecessarilyAssignValuesToVariables extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;
	private String lastTypeForMessage;
	private Map<String, VariableTree> variableList = new HashMap<>();
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
		Integer issueLine = tree.firstToken().line();

		if (!(linesWithIssuesByVariable.containsKey(name) && linesWithIssuesByVariable.get(name).contains(issueLine))) {
			if (!linesWithIssuesByVariable.containsKey(name)) {
				linesWithIssuesByVariable.put(name, new ArrayList<>());
			}

			linesWithIssuesByVariable.get(name).add(issueLine);

			context.reportIssue(this, tree, "The variable " + name + " is not assigned");
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
			variableList.remove(tree.expression().toString());
			super.visitReturnStatement(tree);
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
				if (StringUtils.equals(lastStatementIdentifier, identifier)) {
					context.reportIssue(this, variableTree.initializer(),
							"Immediately " + lastTypeForMessage
									+ " this expression instead of assigning it to the temporary variable \""
									+ identifier + "\".");
				}
			}
		}
	}

	@CheckForNull
	private String getReturnOrThrowIdentifier(StatementTree lastStatementOfBlock) {
		lastTypeForMessage = null;
		ExpressionTree expr = null;
		if (lastStatementOfBlock.is(Kind.THROW_STATEMENT)) {
			lastTypeForMessage = "throw";
			expr = ((ThrowStatementTree) lastStatementOfBlock).expression();
		} else if (lastStatementOfBlock.is(Kind.RETURN_STATEMENT)) {
			lastTypeForMessage = "return";
			expr = ((ReturnStatementTree) lastStatementOfBlock).expression();
		}
		if (expr != null && expr.is(Kind.IDENTIFIER)) {
			return ((IdentifierTree) expr).name();
		}
		return null;
	}

}