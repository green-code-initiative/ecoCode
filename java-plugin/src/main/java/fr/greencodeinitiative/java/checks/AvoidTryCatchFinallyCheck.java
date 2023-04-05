package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.SyntaxToken;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import java.util.Arrays;
import java.util.List;

@Rule(
        key = "EC34",
        name = "Developpement",
        description = AvoidTryCatchFinallyCheck.MESSAGE,
        priority = Priority.MINOR,
        tags = {"ecocode", "eco-design"})
public class AvoidTryCatchFinallyCheck extends IssuableSubscriptionVisitor {

    public static final String MESSAGE = "Avoid using try-catch-finally";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.TRY_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        TryStatementTree tryStatement = (TryStatementTree) tree;
        SyntaxToken finallyKeyword= tryStatement.finallyKeyword();
        if(finallyKeyword!=null){
            reportIssue(finallyKeyword, MESSAGE);
        }
    }
}


