package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

@Rule(
        key = OptimizeDatabaseQueries.RULE_KEY,
        name = OptimizeDatabaseQueries.MESSAGE_RULE,
        description = OptimizeDatabaseQueries.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"eco-design", "ecocode", "bad-practice"})
public class OptimizeDatabaseQueries extends IssuableSubscriptionVisitor{

    public static final String RULE_KEY = "OptimizeDatabaseQueries";
    public static final String MESSAGE_RULE = "Optimize Database Queries (Clause LIMIT)";
    private static final Predicate<String> LIMIT_REGEXP =
            compile("limit", CASE_INSENSITIVE).asPredicate(); //simple regexp, more precision

    private static final Predicate<String> SELECT_REGEXP =
            compile("select", CASE_INSENSITIVE).asPredicate(); //simple regexp, more precision

    private static final Predicate<String> FROM_REGEXP =
            compile("from", CASE_INSENSITIVE).asPredicate(); //simple regexp, more precision

    @Override
    public List<Kind> nodesToVisit() {
        return singletonList(Kind.STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {
        String value = ((LiteralTree) tree).value();
        if (!LIMIT_REGEXP.test(value) && SELECT_REGEXP.test(value) && FROM_REGEXP.test(value)) {
            reportIssue(tree, MESSAGE_RULE);
        }
    }
}