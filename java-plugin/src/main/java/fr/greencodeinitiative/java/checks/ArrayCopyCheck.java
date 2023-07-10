package fr.greencodeinitiative.java.checks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ArrayAccessExpressionTree;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.DoWhileStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

// TODO DDC : rule already existing natively in SonarQube 9.9 (see java:S3012) for a part of checks
// ==> analyse / add our tag to it (?)

/**
 * Array Copy Check
 *
 * @author Aubay
 * @formatter:off
 */
@Rule(key = "EC27")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "GRPS0027")
public class ArrayCopyCheck extends IssuableSubscriptionVisitor {

    //@formatter:on
    protected static final String MESSAGERULE = "Use System.arraycopy to copy arrays";

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.FOR_STATEMENT, Kind.WHILE_STATEMENT, Kind.DO_STATEMENT, Kind.FOR_EACH_STATEMENT);
    }

    /**
     * Check a node. Report issue when found.
     */
    @Override
    public void visitNode(final Tree tree) {
        // Determine blocks to be analyzed
        final List<Bloc> blocs = getBlocsOfCode(tree);

        // Analyze blocks
        for (final Bloc bloc : blocs) {
            if (bloc.isForeach()) {
                handleForEachAssignments(tree, bloc);
            }
            handleAssignments(tree, bloc);
        }
    }

    /**
     * Handle for-each assignments controls.
     *
     * @param tree
     * @param bloc
     */
    private void handleForEachAssignments(final Tree tree, final Bloc bloc) {
        for (final AssignmentExpressionTree assignment : extractAssignments(tree, bloc)) {
            final ExpressionTree destination = assignment.variable();
            final ExpressionTree source = assignment.expression();
            if (isArray(destination) && isVariable(source)) {
                final String destinationIdentifier = getArrayIdentifier(destination);
                final String sourceIdentifier = ((IdentifierTree) source).name();
                if (bloc.getValue().equals(sourceIdentifier) && !bloc.getIterable().equals(destinationIdentifier)) {
                    reportIssue(tree, MESSAGERULE);
                }
            }
        }
    }

    /**
     * Handle assignments controls.
     *
     * @param tree
     * @param bloc
     */
    private void handleAssignments(final Tree tree, final Bloc bloc) {
        for (final AssignmentExpressionTree assignment : extractAssignments(tree, bloc)) {
            final ExpressionTree destVariable = assignment.variable();
            final ExpressionTree srcEspression = assignment.expression();
            if (isArray(destVariable) && isArray(srcEspression)) {
                final String destArray = getArrayIdentifier(destVariable);
                final String srcArray = getArrayIdentifier(srcEspression);
                if (destArray != null && !destArray.equals(srcArray)) {
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
     * Verify if expression is an variable
     *
     * @param source
     * @return
     */
    private boolean isVariable(final ExpressionTree source) {
        return source instanceof IdentifierTree;
    }

    /**
     * Extract nested blocks of code
     *
     * @param tree
     * @return
     */
    private List<Bloc> getBlocsOfCode(final Tree tree) {
        final List<Bloc> blocs = new ArrayList<>();
        if (tree instanceof ForStatementTree) {
            ForStatementTree castedForTree = (ForStatementTree) tree;
            addBloc(blocs, castedForTree.statement());
        } else if (tree instanceof ForEachStatement) {
            ForEachStatement castedForEachTree = (ForEachStatement) tree;
            addForEachBloc(blocs, castedForEachTree.statement(), castedForEachTree.variable(),
                    castedForEachTree.expression());
        } else if (tree instanceof WhileStatementTree) {
            WhileStatementTree castedWhileTree = (WhileStatementTree) tree;
            addBloc(blocs, castedWhileTree.statement());
        } else if (tree instanceof DoWhileStatementTree) {
            DoWhileStatementTree castedDoWhileTree = (DoWhileStatementTree) tree;
            addBloc(blocs, castedDoWhileTree.statement());
        } else if (tree instanceof IfStatementTree) {
            IfStatementTree castedIfTree = (IfStatementTree) tree;
            addBloc(blocs, castedIfTree.thenStatement());
            addBloc(blocs, castedIfTree.elseStatement());
        } else if (tree instanceof TryStatementTree) {
            final TryStatementTree tryTree = (TryStatementTree) tree;
            addBloc(blocs, tryTree.block());
            addBloc(blocs, extractCatchBlocks(tryTree));
            addBloc(blocs, tryTree.finallyBlock());
        }
        return blocs;
    }

    /**
     * Assignments extraction from block of code.
     *
     * @param tree
     * @param block
     * @return
     */
    private List<AssignmentExpressionTree> extractAssignments(final Tree tree, final Bloc bloc) {
        final BlockTree block = bloc.getBlockTree();

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
            final List<Bloc> blocs = getBlocsOfCode(ifstatement);
            for (final Bloc b : blocs) {
                result.addAll(extractAssignments(tree, b));
            }
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
     * Add a Bloc in list after type checking
     *
     * @param blocs
     * @param statements
     */
    private void addBloc(final List<Bloc> blocs, final StatementTree... statements) {
        for (final StatementTree statement : statements) {
            if (statement instanceof BlockTree) {
                blocs.add(new Bloc((BlockTree) statement));
            }
        }
    }

    /**
     * Add a Bloc in list after type checking
     *
     * @param blocs
     * @param statement
     * @param variable
     * @param expression
     */
    private void addForEachBloc(final List<Bloc> blocs, final StatementTree statement, final VariableTree variable,
                                final ExpressionTree expression) {
        if (statement instanceof BlockTree && expression instanceof IdentifierTree) {
            blocs.add(new Bloc((BlockTree) statement, ((IdentifierTree) expression).identifierToken().text(),
                    variable.simpleName().identifierToken().text()));
        }
    }

    private static class Bloc {
        private final BlockTree blockTree;
        private String iterable;
        private String value;

        public Bloc(final BlockTree blockTree, final String iterable, final String value) {
            this.blockTree = blockTree;
            this.iterable = iterable;
            this.value = value;
        }

        public boolean isForeach() {
            return iterable != null && value != null;
        }

        public Bloc(final BlockTree blockTree) {
            this.blockTree = blockTree;
        }

        public BlockTree getBlockTree() {
            return blockTree;
        }

        public String getIterable() {
            return iterable;
        }

        public String getValue() {
            return value;
        }

    }
}
