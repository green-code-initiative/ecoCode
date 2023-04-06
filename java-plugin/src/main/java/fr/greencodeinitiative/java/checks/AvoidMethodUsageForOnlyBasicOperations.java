package fr.greencodeinitiative.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static fr.greencodeinitiative.java.checks.AvoidMethodUsageForOnlyBasicOperations.RULE_MESSAGE;
import static org.sonar.check.Priority.MINOR;

/**
 * @author Mohamed Oussama A.
 * Created on 05/04/2023
 * @version 1.0
 */

@Rule(key = "EC22",
        name = RULE_MESSAGE,
        description = RULE_MESSAGE,
        priority = MINOR,
        tags = {"smell", "ecocode", "eco-design", "memory", "performance"})
public class AvoidMethodUsageForOnlyBasicOperations extends IssuableSubscriptionVisitor {

    protected static final String RULE_MESSAGE = "Avoid Method Usage For Only Basic Operations";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD);
    }


    @Override
    public void visitNode(Tree tree) {
        Optional.ofNullable(((MethodTree) tree).block())
                .map(BlockTree::body)
                .filter(list -> list.size() == 1)
                .flatMap(list -> list.stream().findFirst())
                .filter(ReturnStatementTree.class::isInstance)
                .map(statementTree -> ((ReturnStatementTree) statementTree).expression())
                .filter(this.isBasicOperation)
                .ifPresent(expressionTree -> reportIssue(expressionTree, RULE_MESSAGE));
    }


    private Predicate<ExpressionTree> isBasicOperation = expression -> expression instanceof BinaryExpressionTree
            || expression instanceof UnaryExpressionTree
            || expression.is(Tree.Kind.CONDITIONAL_EXPRESSION);
}
