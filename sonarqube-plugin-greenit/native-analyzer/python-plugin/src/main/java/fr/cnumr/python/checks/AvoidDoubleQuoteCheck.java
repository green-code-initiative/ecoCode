package fr.cnumr.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.StringElement;
import org.sonar.plugins.python.api.tree.StringLiteral;
import org.sonar.plugins.python.api.tree.Tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Rule(
        key = AvoidDoubleQuoteCheck.RULE_KEY,
        name = "Developpement",
        description = AvoidDoubleQuoteCheck.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidDoubleQuoteCheck extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "S66";
    public static final String DESCRIPTION = "Utiliser la simple côte (') au lieu du guillemet (\")";
    private static final Map<String, Collection<Integer>> linesWithIssuesByFile = new HashMap<>();

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.STRING_LITERAL, this::visitNodeString);
    }

    public void visitNodeString(SubscriptionContext ctx) {
        StringLiteral stringLiteral = (StringLiteral) ctx.syntaxNode();
        stringLiteral.stringElements().forEach(stringElement -> {
            checkIssue(stringElement, ctx);
        });
    }

    public void checkIssue(StringElement stringElement, SubscriptionContext ctx) {
        if (lineAlreadyHasThisIssue(stringElement, ctx)) return;
        if (stringElement.value().indexOf("\"") == 0 && stringElement.value().lastIndexOf("\"") == stringElement.value().length() - 1) {
            repport(stringElement, ctx);
            return;
        }
    }

    private void repport(StringElement stringElement, SubscriptionContext ctx) {
        if (stringElement.firstToken() != null) {
            final String classname = ctx.pythonFile().fileName();
            final int line = stringElement.firstToken().line();
            if (!linesWithIssuesByFile.containsKey(classname)) {
                linesWithIssuesByFile.put(classname, new ArrayList<>());
            }
            linesWithIssuesByFile.get(classname).add(line);
        }
        ctx.addIssue(stringElement, DESCRIPTION);
    }

    private boolean lineAlreadyHasThisIssue(StringElement stringElement, SubscriptionContext ctx) {
        if (stringElement.firstToken() != null) {
            final String filename = ctx.pythonFile().fileName();
            final int line = stringElement.firstToken().line();

            return linesWithIssuesByFile.containsKey(filename)
                    && linesWithIssuesByFile.get(filename).contains(line);
        }

        return false;
    }


}