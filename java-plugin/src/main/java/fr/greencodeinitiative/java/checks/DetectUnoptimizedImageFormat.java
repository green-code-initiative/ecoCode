package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Rule(
        key = "EC204",
        name = "Developpement",
        description = IncrementCheck.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class DetectUnoptimizedImageFormat extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Detect unoptimized image format";
    protected static final String MESSAGEERROR = "If possible, the utilisation of svg image format (or <svg/> html tag) is recommended over other image format.";
    protected static final Pattern IMGEXTENSION = Pattern.compile("\\.(bmp|ico|tiff|webp|png|jpg|jpeg|jfif|pjpeg|pjp|gif|avif|apng)");

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.STRING_LITERAL, Tree.Kind.TEXT_BLOCK);
    }

    @Override
    public void visitNode(Tree tree) {

        if (tree.is(Tree.Kind.STRING_LITERAL, Tree.Kind.TEXT_BLOCK)) {
            final String strValue = ((LiteralTree) tree).value();
            final Matcher matcher = IMGEXTENSION.matcher(strValue);
            if(matcher.find()) {
                reportIssue(tree, MESSAGEERROR);
            }
        }
    }
}
