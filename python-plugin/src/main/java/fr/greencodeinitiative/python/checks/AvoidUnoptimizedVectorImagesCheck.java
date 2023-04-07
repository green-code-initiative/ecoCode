package fr.greencodeinitiative.python.checks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.*;

@Rule(
        key = AvoidUnoptimizedVectorImagesCheck.RULE_KEY,
        name = AvoidUnoptimizedVectorImagesCheck.DESCRIPTION,
        description = AvoidUnoptimizedVectorImagesCheck.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"eco-design", "ecocode"})
public class AvoidUnoptimizedVectorImagesCheck extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "EC10";
    public static final String DESCRIPTION = "Avoid using unoptimized vector images";
    private static final Pattern COMMENT_PATTERN = Pattern.compile("(<!--|-->)");
    private static final Pattern LAYERS_PATTERN = Pattern.compile("</g>");
    private static final Pattern NAMESPACE_PATTERN = Pattern.compile("xmlns:(?!svg)[a-z0-9]+");
    private static final String STRING_TAG_TO_DETECT = "</svg>"

    @Override
    public void initialize(Context ctx) {
        ctx.registerSyntaxNodeConsumer(Tree.Kind.STRING_ELEMENT, this::checkSVG);
    }

    public void checkSVG(SubscriptionContext ctx) {
        StringElement stringLiteral = (StringElement) ctx.syntaxNode();
        checkComments(stringLiteral, ctx);
        checkLayers(stringLiteral, ctx);
        checkNamespaces(stringLiteral, ctx);
        checkMetadata(stringLiteral, ctx);
    }

    private void checkComments(StringElement str, SubscriptionContext ctx) {
        if (str.value().contains(AvoidUnoptimizedVectorImagesCheck.STRING_TAG_TO_DETECT) && COMMENT_PATTERN.matcher(str.value()).find()) {
            ctx.addIssue(str, DESCRIPTION);
        }
    }

    private void checkLayers(StringElement str, SubscriptionContext ctx) {
        Matcher matcher = LAYERS_PATTERN.matcher(str.value());
        int matches = 0;
        while (matcher.find()) matches++;
        if (str.value().contains(AvoidUnoptimizedVectorImagesCheck.STRING_TAG_TO_DETECT) && matches > 1) {
            ctx.addIssue(str, DESCRIPTION);
        }
    }

    private void checkNamespaces(StringElement str, SubscriptionContext ctx) {
        if (str.value().contains(AvoidUnoptimizedVectorImagesCheck.STRING_TAG_TO_DETECT) && NAMESPACE_PATTERN.matcher(str.value()).find()) {
            ctx.addIssue(str, DESCRIPTION);
        }
    }

    private void checkMetadata(StringElement str, SubscriptionContext ctx) {
        if (str.value().contains(AvoidUnoptimizedVectorImagesCheck.STRING_TAG_TO_DETECT) && str.value().contains("</metadata>")) {
            ctx.addIssue(str, DESCRIPTION);
        }
    }
}
