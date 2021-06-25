package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.ArrayAssignmentPatternTree;
import org.sonar.plugins.php.api.tree.expression.LiteralTree;
import org.sonar.plugins.php.api.tree.statement.TryStatementTree;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.*;

@Rule(
        key = AvoidDoubleQuoteCheck.RULE_KEY,
        name = "Developpement",
        description = AvoidDoubleQuoteCheck.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidDoubleQuoteCheck extends PHPSubscriptionCheck {
    public static final String RULE_KEY = "S66";
    public static final String DESCRIPTION = "Utiliser la simple côte (') au lieu du guillemet (\")";
    private static final Map<String, Collection<Integer>> linesWithIssuesByFile = new HashMap<>();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        // Tree.Kind.EXPANDABLE_STRING_LITERAL)

        return Collections.singletonList(Tree.Kind.REGULAR_STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {
        LiteralTree method = (LiteralTree) tree;
        checkIssue(method);
    }

    public void checkIssue(LiteralTree literalTree) {
        if (lineAlreadyHasThisIssue(literalTree)) return;
        if (literalTree.value().indexOf("\"") == 0 && literalTree.value().lastIndexOf("\"") == literalTree.value().length() - 1) {
            repport(literalTree);
            return;
        }
    }

    private void repport(LiteralTree literalTree) {
        if (literalTree.token() != null) {

            final String classname = context().getPhpFile().toString();
            final int line = literalTree.token().line();
            if (!linesWithIssuesByFile.containsKey(classname)) {
                linesWithIssuesByFile.put(classname, new ArrayList<>());
            }
            linesWithIssuesByFile.get(classname).add(line);
        }
        context().newIssue(this, literalTree, DESCRIPTION);

    }

    private boolean lineAlreadyHasThisIssue(LiteralTree literalTree) {
        if (literalTree.token() != null) {
            final String filename = context().getPhpFile().toString();
            final int line = literalTree.token().line();

            return linesWithIssuesByFile.containsKey(filename)
                    && linesWithIssuesByFile.get(filename).contains(line);
        }

        return false;
    }

}
