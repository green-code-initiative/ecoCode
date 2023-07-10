package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

import com.google.re2j.Pattern;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC4")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "D4")
public class AvoidUsingGlobalVariablesCheck extends IssuableSubscriptionVisitor {

    private static final String ERROR_MESSAGE = "Avoid using global variables";
    private static final Pattern PATTERN = Pattern.compile("^.*(static).*$", Pattern.CASE_INSENSITIVE);

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.STATIC_INITIALIZER, Kind.VARIABLE, Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {

        if (tree.is(Kind.STATIC_INITIALIZER)) {
            reportIssue(tree, String.format(ERROR_MESSAGE, tree));
        }
        if (tree.is(Kind.VARIABLE)) {
            VariableTree variableTree = (VariableTree) tree;
            int modifiersSize = (variableTree).modifiers().modifiers().size();
            for (int i = 0; i < modifiersSize; i++) {
                String modifier = ((VariableTree) tree).modifiers().modifiers().get(i).modifier().toString();
                if (PATTERN.matcher(modifier).matches()) {
                    reportIssue(tree, String.format(ERROR_MESSAGE, modifier));
                }
            }
        }
    }
}
