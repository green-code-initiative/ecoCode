package fr.cnumr.python.checks;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.StringElement;
import org.sonar.plugins.python.api.tree.StringLiteral;
import org.sonar.plugins.python.api.tree.Tree;

@Rule(
        key = "S74",
        name = "Developpement",
        description = AvoidFullSQLRequest.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidFullSQLRequest extends PythonSubscriptionCheck {

	protected static final String MESSAGERULE = "Don't use the query SELECT * FROM";
	private static final String REGEXPSELECTFROM = "(?i).*select.*\\*.*from.*";
    private static final Map<String, Collection<Integer>> linesWithIssuesByFile = new HashMap<>();

 

	@Override
	public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.STRING_LITERAL, this::visitNodeString);
    }

    public void visitNodeString(SubscriptionContext ctx) {
        StringLiteral stringLiteral = (StringLiteral) ctx.syntaxNode();
        stringLiteral.stringElements().forEach(stringElement -> checkIssue(stringElement, ctx));
    }

    public void checkIssue(StringElement stringElement, SubscriptionContext ctx) {
        if (lineAlreadyHasThisIssue(stringElement, ctx)) return;
        if (stringElement.value().matches(REGEXPSELECTFROM)) {
            repport(stringElement, ctx);
        }
    }

    private void repport(StringElement stringElement, SubscriptionContext ctx) {
        if (stringElement.firstToken() != null) {
            final String classname = ctx.pythonFile().fileName();
            final int line = stringElement.firstToken().line();
            linesWithIssuesByFile.computeIfAbsent(classname, k -> new ArrayList<>());
            linesWithIssuesByFile.get(classname).add(line);
        }
        ctx.addIssue(stringElement, MESSAGERULE);
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