package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Rule(
        key = "CRJVM208",
        name = "Developpement",
        description = AvoidSpringRepositoryCallInLoopWithStream.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidSpringRepositoryCallInLoopWithStream extends IssuableSubscriptionVisitor {

    public static final String MESSAGE_RULE = "Avoid spring repository call in stream!";
    private static final String SPRING_REPOSITORY = "org.springframework.data.repository.Repository";
    private static final String BASE_STREAM = "java.util.stream.BaseStream";
    private static final MethodMatchers REPOSITORY_METHOD =  MethodMatchers.create().ofSubTypes(SPRING_REPOSITORY).anyName().withAnyParameters().build();
    private static final MethodMatchers FOREACH_METHOD = MethodMatchers.create().ofSubTypes(BASE_STREAM).names("forEach", "forEachOrdered", "map", "peek").withAnyParameters().build();

    private final ForEachVisitor forEachVisitor = new ForEachVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
        if (FOREACH_METHOD.matches(methodInvocationTree)) {
            tree.accept(forEachVisitor);
        }
    }

    private class ForEachVisitor extends BaseTreeVisitor {
        private final LambdaVisitor lambdaVisitor = new LambdaVisitor();

        @Override
        public void visitLambdaExpression(LambdaExpressionTree tree) {
            tree.accept(lambdaVisitor);
        }

    }

    private class LambdaVisitor extends BaseTreeVisitor {

        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            if (REPOSITORY_METHOD.matches(tree)) {
                reportIssue(tree, MESSAGE_RULE);
            }
        }

    }


}
