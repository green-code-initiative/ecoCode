package fr.greencodeinitiative.python.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.StringLiteral;
import org.sonar.plugins.python.api.tree.Tree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Rule(key = "EC203")
public class DetectUnoptimizedImageFormat extends PythonSubscriptionCheck {

    protected static final String RULE_KEY = "EC203";
    protected static final String MESSAGERULE = "Detect unoptimized image format";
    protected static final String MESSAGEERROR = "If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.";
    protected static final Pattern IMGEXTENSION = Pattern.compile("\\.(bmp|ico|tiff|webp|png|jpg|jpeg|jfif|pjpeg|pjp|gif|avif|apng)");

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.STRING_LITERAL, this::visitNodeString);
    }

    public void visitNodeString(SubscriptionContext ctx) {
        if (ctx.syntaxNode().is(Tree.Kind.STRING_LITERAL)) {
            final  StringLiteral stringLiteral = (StringLiteral) ctx.syntaxNode();
            final String strValue = stringLiteral.trimmedQuotesValue();
            final Matcher matcher = IMGEXTENSION.matcher(strValue);
            if(matcher.find()) {
                ctx.addIssue(stringLiteral, MESSAGEERROR);
            }
        }
    }
}
