package fr.cnumr.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Collections;
import java.util.List;

@Rule(key = "SDMLQ1")
public class AvoidStatementForDMLQueries extends IssuableSubscriptionVisitor {

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
        if (first.is(Tree.Kind.STRING_LITERAL))
        {
            LiteralTree literalTree = (LiteralTree) first;
            String str = literalTree.value();
            String regex = "(SELECT|INSERT INTO|UPDATE|DELETE FROM)\\s?.*";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find())
                reportIssue(literalTree, "You must not use Statement for a DML query");
        }
    }
}
