package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@Rule(
        key = "S90",
        name = "Developpement",
        description = AvoidUseOfStaticInInterface.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidUseOfStaticInInterface extends IssuableSubscriptionVisitor {

    public static final String MESSAGE_RULE = "Avoid use of static in interface.";

    private final UseInterfaceVisitor VISITOR = new UseInterfaceVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.INTERFACE);
    }

    @Override
    public void visitNode(@Nonnull Tree tree) {
        tree.accept(VISITOR);
    }

    private class UseInterfaceVisitor extends BaseTreeVisitor {
        @Override
        public void visitVariable(@Nonnull VariableTree tree) {
            if (tree.symbol().isStatic()) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitVariable(tree);
            }
        }
    }

}
