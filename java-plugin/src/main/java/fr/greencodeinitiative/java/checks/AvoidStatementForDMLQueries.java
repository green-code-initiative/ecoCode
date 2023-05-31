package fr.greencodeinitiative.java.checks;

import java.util.Collections;
import java.util.List;

import com.google.re2j.Pattern;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.Arguments;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC5")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "SDMLQ1")
public class AvoidStatementForDMLQueries extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "You must not use Statement for a DML query";

    private static final Pattern PATTERN = Pattern.compile("(SELECT|INSERT INTO|UPDATE|DELETE FROM)\\s?.*", Pattern.CASE_INSENSITIVE);

    private final MethodMatchers EXECUTE_METHOD = MethodMatchers.or(
            MethodMatchers.create().ofSubTypes("java.sql.Statement").names("executeUpdate")
                    .withAnyParameters().build());

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
        if (!EXECUTE_METHOD.matches(methodInvocationTree))
            return;
        Arguments arguments = methodInvocationTree.arguments();
        if (arguments.size() < 1)
            return;
        ExpressionTree first = arguments.get(0);
        if (first.is(Tree.Kind.STRING_LITERAL)) {
            LiteralTree literalTree = (LiteralTree) first;
            String str = literalTree.value();
            if (PATTERN.matcher(str).find())
                reportIssue(literalTree, MESSAGERULE);
        }
    }
}
