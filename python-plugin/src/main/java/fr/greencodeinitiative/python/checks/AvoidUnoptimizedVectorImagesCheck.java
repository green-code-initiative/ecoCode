package fr.greencodeinitiative.python.checks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.*;

@Rule(key = "EC10")
public class AvoidUnoptimizedVectorImagesCheck extends PythonSubscriptionCheck {

    public static final String DESCRIPTION = "Avoid using unoptimized vector images";
    private static final Pattern LAYERS_PATTERN = Pattern.compile("</g>");

    @Override
    public void initialize(Context ctx) {
        ctx.registerSyntaxNodeConsumer(Tree.Kind.STRING_ELEMENT, this::checkSVG);
    }

    private void checkSVG(SubscriptionContext ctx) {
        StringElement stringLiteral = (StringElement) ctx.syntaxNode();
        checkComments(stringLiteral, ctx);
        checkLayers(stringLiteral, ctx);
        checkNamespaces(stringLiteral, ctx);
        checkMetadata(stringLiteral, ctx);
    }

    private void checkComments(StringElement str, SubscriptionContext ctx) {
        if (isSvgTagNotDetected(str))
            return;

        if (str.value().contains("<!--") || str.value().contains("-->")) {
            ctx.addIssue(str, DESCRIPTION);
        }
    }

    private void checkLayers(StringElement str, SubscriptionContext ctx) {
        if (isSvgTagNotDetected(str))
            return;

        Matcher matcher = LAYERS_PATTERN.matcher(str.value());
        int matches = 0;
        while (matcher.find()) matches++;
        if (matches > 1) { // if at least 2 finds, create an issue
            ctx.addIssue(str, DESCRIPTION);
        }
    }

    private void checkNamespaces(StringElement str, SubscriptionContext ctx) {
        if (isSvgTagNotDetected(str))
            return;

        if (str.value().contains("xmlns:") && !str.value().contains("xmlns:svg=")) {
            ctx.addIssue(str, DESCRIPTION);
        }
    }

    private void checkMetadata(StringElement str, SubscriptionContext ctx) {
        if (isSvgTagNotDetected(str))
            return;

        if (str.value().contains("</metadata>")) {
            ctx.addIssue(str, DESCRIPTION);
        }
    }

    private boolean isSvgTagNotDetected(StringElement str) {
        return !str.value().contains("</svg>");
    }
}
