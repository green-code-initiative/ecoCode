package fr.greencodeinitiative.python.checks;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.StringElement;
import org.sonar.plugins.python.api.tree.StringLiteral;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC74")
@DeprecatedRuleKey(repositoryKey = "gci-python", ruleKey = "S74")
public class AvoidFullSQLRequest extends PythonSubscriptionCheck {

    protected static final String MESSAGERULE = "Don't use the query SELECT * FROM";

    // TODO DDC : create support to add in deployment th dependency com.google.re2j:re2j
    // and replace "import java.util.regex.Pattern" by "import com.google.re2j.Pattern"
    private static final Pattern PATTERN = Pattern.compile("(?i).*select.*\\*.*from.*");
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
        if (PATTERN.matcher(stringElement.value()).matches()) {
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
