package fr.cnumr.java.checks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ArrayAccessExpressionTree;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.DoWhileStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;

@Rule(key = "GRPS0027", name = "Developpement", description = ArrayCopyCheck.MESSAGERULE, priority = Priority.MINOR, tags = {
		"CODE_SMELL" })
public class ArrayCopyCheck extends IssuableSubscriptionVisitor {

	protected static final String MESSAGERULE = "Utiliser System.arraycopy pour copier des arrays";

	@Override
	public List<Kind> nodesToVisit() {
		return Arrays.asList(Kind.FOR_STATEMENT, Kind.WHILE_STATEMENT, Kind.DO_STATEMENT);
	}

	/**
	 * Check a node. Report issue when found.
	 */
	@Override
	public void visitNode(final Tree tree) {
		final List<AssignmentExpressionTree> assignments = extractAssignments(tree);
		for (final AssignmentExpressionTree assignment : assignments) {
			final ExpressionTree destVariable = assignment.variable();
			final ExpressionTree srcEspression = assignment.expression();
			if (isArray(destVariable) && isArray(srcEspression)) {
				final String destArray = getArrayIdentifier(destVariable);
				final String srcArray = getArrayIdentifier(srcEspression);
				if (!destArray.equals(srcArray)) {
					reportIssue(tree, MESSAGERULE);
				}
			}
		}
	}

	/**
	 * Extract variable's name of array
	 *
	 * @param expression of Array
	 * @return Array's name
	 */
	private String getArrayIdentifier(final ExpressionTree expression) {
		if (expression instanceof ArrayAccessExpressionTree) {
			final ExpressionTree identifier = ((ArrayAccessExpressionTree) expression).expression();
			if (identifier instanceof IdentifierTree) {
				return ((IdentifierTree) identifier).identifierToken().text();
			}
		}
		return null;
	}

	/**
	 * Verify if expression is an Array
	 *
	 * @param expression
	 * @return true if instance of ArrayAccessExpressionTree, false else
	 */
	private boolean isArray(final ExpressionTree expression) {
		return expression instanceof ArrayAccessExpressionTree;
	}

	/**
	 * Extract all assignments
	 *
	 * @param tree
	 * @return
	 */
	private List<AssignmentExpressionTree> extractAssignments(final Tree tree) {

		// Determine blocks to be analyzed
		final List<BlockTree> blocks = getBlocksOfCode(tree);

		// Analyze blocks
		final List<AssignmentExpressionTree> result = new ArrayList<>();
		for (final BlockTree block : blocks) {
			result.addAll(extractAssignments(block));
		}
		return result;
	}

	/**
	 * Extract nested blocks of code
	 *
	 * @param tree
	 * @return
	 */
	private List<BlockTree> getBlocksOfCode(final Tree tree) {
		final List<BlockTree> blocks = new ArrayList<>();
		if (tree instanceof ForStatementTree) {
			addBlock(blocks, ((ForStatementTree) tree).statement());
		} else if (tree instanceof WhileStatementTree) {
			addBlock(blocks, ((WhileStatementTree) tree).statement());
		} else if (tree instanceof DoWhileStatementTree) {
			addBlock(blocks, ((DoWhileStatementTree) tree).statement());
		} else if (tree instanceof IfStatementTree) {
			addBlock(blocks, ((IfStatementTree) tree).thenStatement());
			addBlock(blocks, ((IfStatementTree) tree).elseStatement());
		} else if (tree instanceof TryStatementTree) {
			final TryStatementTree tryTree = (TryStatementTree) tree;
			addBlock(blocks, tryTree.block());
			addBlock(blocks, extractCatchBlocks(tryTree));
			addBlock(blocks, tryTree.finallyBlock());
		}
		return blocks;
	}

	/**
	 * Assignments extraction from block of code.
	 *
	 * @param block
	 * @return
	 */
	private List<AssignmentExpressionTree> extractAssignments(final BlockTree block) {
		// Prepare useful predicates
		final Predicate<StatementTree> blocksPredicate = statement -> statement.is(Kind.IF_STATEMENT)
				|| statement.is(Kind.TRY_STATEMENT);
		final Predicate<StatementTree> assignmentPredicate = statement -> statement.is(Kind.EXPRESSION_STATEMENT)
				&& ((ExpressionStatementTree) statement).expression().is(Kind.ASSIGNMENT);

		// Filter expressions to find assignments
		final List<AssignmentExpressionTree> result = block.body().stream().filter(assignmentPredicate)
				.map(assign -> (AssignmentExpressionTree) ((ExpressionStatementTree) assign).expression())
				.collect(Collectors.toList());

		// Recursive loop for nested blocks, add nested assignments to results
		final List<StatementTree> ifStatements = block.body().stream().filter(blocksPredicate)
				.collect(Collectors.toList());
		for (final StatementTree ifstatement : ifStatements) {
			result.addAll(extractAssignments(ifstatement));
		}
		return result;
	}

	/**
	 * Extract all blocks of code from try/catch statement
	 *
	 * @param tryTree
	 * @return Array of StatementTree
	 */
	private BlockTree[] extractCatchBlocks(final TryStatementTree tryTree) {
		final List<CatchTree> catches = tryTree.catches();
		return catches.stream().map(CatchTree::block).collect(Collectors.toList()).toArray(new BlockTree[0]);
	}

	/**
	 * Add a BlockTree in list after type checking
	 *
	 * @param blocks
	 * @param statements
	 */
	private void addBlock(final List<BlockTree> blocks, final StatementTree... statements) {
		for (final StatementTree statement : statements) {
			if (statement instanceof BlockTree) {
				blocks.add((BlockTree) statement);
			}
		}
	}
}
