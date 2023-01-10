package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Rule(
        key = "S76",
        name = "Developpement",
        description = AvoidUsageOfStaticCollections.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidUsageOfStaticCollections extends IssuableSubscriptionVisitor {

    protected static final String MESSAGE_RULE = "Avoid usage of static collections.";

    private final AvoidUsageOfStaticCollectionsVisitor visitor = new AvoidUsageOfStaticCollectionsVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(
                Tree.Kind.VARIABLE
        );
    }

    @Override
    public void visitNode(@Nonnull Tree tree) {
        tree.accept(visitor);
    }

    private class AvoidUsageOfStaticCollectionsVisitor extends BaseTreeVisitor {

        @Override
        public void visitVariable(@Nonnull VariableTree tree) {
            if (tree.symbol().isStatic() &&
                    (tree.type().symbolType().isSubtypeOf(Iterable.class.getName()) ||
                            tree.type().symbolType().is(Map.class.getName()))
            ) {
                reportIssue(tree, MESSAGE_RULE);
            } else {
                super.visitVariable(tree);
            }
        }

    }

}
