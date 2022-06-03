package io.ecocode.java.checks.sobriety;

import com.google.common.collect.ImmutableList;
import io.ecocode.java.checks.helpers.CheckArgumentComplexType;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

@Rule(key = "ESOB014", name = "ecoCodeHighFrameRate")
public class HighFrameRateRule extends IssuableSubscriptionVisitor {

    private static final float FRAME_RATE_60 = 60.0f;

    private final MethodMatchers surfaceListenerMethodMatcher = MethodMatchers.create().
            ofTypes("android.view.Surface").names("setFrameRate").withAnyParameters().build();

    public HighFrameRateRule() {
        super();
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
            if (surfaceListenerMethodMatcher.matches(methodInvocationTree) &&
                    isRefreshSixtyOrHigher(methodInvocationTree.arguments())) {
                reportIssue(methodInvocationTree, "A regular app displays 60 frames per second (60Hz). In order to optimize content refreshes and hence saving energy, this frequency should not be raised to 90Hz or 120Hz.");
            }
        }
    }

    /**
     * Checking if method arguments are complying to the rule
     * @param arguments Arguments of the method called
     * @return true if argument is a float under or equal 60
     */
    private boolean isRefreshSixtyOrHigher(Arguments arguments) {
        ExpressionTree firstArg = arguments.get(0);
        if (firstArg.is(Tree.Kind.IDENTIFIER)) {
            IdentifierTree expressionTree = (IdentifierTree) firstArg;
            Object argValue = expressionTree.asConstant().get();
            if (argValue instanceof Float) {
                return ((Float) argValue).floatValue() > FRAME_RATE_60;
            }
        } else if (firstArg.is(Tree.Kind.FLOAT_LITERAL)) {
            firstArg = (ExpressionTree) CheckArgumentComplexType.getChildExpression(firstArg);
            LiteralTree lit = (LiteralTree) firstArg;
            return Float.valueOf(lit.value()) > FRAME_RATE_60;
        }
        // not compliant case
        return false;
    }
}