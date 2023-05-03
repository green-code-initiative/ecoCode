package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC2",
        name = "Developpement",
        description = AvoidMultipleIfElseStatement.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "AMIES")
public class AvoidMultipleIfElseStatement extends IssuableSubscriptionVisitor {
    protected static final String RULE_MESSAGE = "Using a switch statement instead of multiple if-else if possible";

    private void checkIfStatement(Tree tree) {
        int sizeBody = 0;
        int idx = 0;
        int countIfStatement = 0;

        Tree parentNode = tree.parent();

        if (!(parentNode instanceof BlockTree))
            return;
        BlockTree node = (BlockTree) parentNode;
        sizeBody = node.body().toArray().length;
        while (idx < sizeBody) {
            if (node.body().get(idx) instanceof IfStatementTree)
                ++countIfStatement;
            ++idx;
        }
        if (countIfStatement > 1)
            reportIssue(tree, RULE_MESSAGE);
    }

    private void checkElseIfStatement(Tree tree) {
        IfStatementTree node = (IfStatementTree) tree;
        int count = 0;
        StatementTree statementTree;

        while (true) {
            if (count >= 2)
                reportIssue(tree, RULE_MESSAGE);
            statementTree = node.elseStatement();
            if (statementTree instanceof IfStatementTree) {
                ++count;
                node = (IfStatementTree) statementTree;
            } else {
                break;
            }
        }
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.IF_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        checkIfStatement(tree);
        checkElseIfStatement(tree);
    }
}
