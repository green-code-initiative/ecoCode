package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "EC1",
        name = "Developpement",
        description = AvoidSpringRepositoryCallInLoopCheck.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidSpringRepositoryCallInLoopCheck extends IssuableSubscriptionVisitor {

    protected static final String RULE_MESSAGE = "Avoid Spring repository call in loop";

    private static final String SPRING_REPOSITORY = "org.springframework.data.repository.Repository";

    private static final MethodMatchers REPOSITORY_METHOD =
            MethodMatchers.create().ofSubTypes(SPRING_REPOSITORY).anyName().withAnyParameters()
                    .build();

    private final AvoidSpringRepositoryCallInLoopCheckVisitor visitorInFile = new AvoidSpringRepositoryCallInLoopCheckVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.FOR_EACH_STATEMENT, Tree.Kind.FOR_STATEMENT, Tree.Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        tree.accept(visitorInFile);
    }

    private class AvoidSpringRepositoryCallInLoopCheckVisitor extends BaseTreeVisitor {
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            if (REPOSITORY_METHOD.matches(tree)) {
                reportIssue(tree, RULE_MESSAGE);
            } else {
                super.visitMethodInvocation(tree);
            }
        }

    }
}
