package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;
import java.util.Objects;

@Rule(key = "ECCRJVM208",
        name = "Developpement",
        description = AvoidSpringRepositoryCallInStreamCheck.REPOSITORY_METHODS_INSIDE_STREAM,
        priority = Priority.MINOR,
        tags = {"bug" })
public class AvoidSpringRepositoryCallInStreamCheck extends IssuableSubscriptionVisitor {

    public static final String REPOSITORY_METHODS_INSIDE_STREAM = "Avoid calling repository methods inside stream";
    private static final String SPRING_REPOSITORY = "org.springframework.data.repository.Repository";

    public static final String STREAM = "Stream";

    private static final MethodMatchers REPOSITORY_METHOD =
            MethodMatchers.create().ofSubTypes(SPRING_REPOSITORY).anyName().withAnyParameters()
                    .build();
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.LAMBDA_EXPRESSION);
    }

    @Override
    public void visitNode(Tree tree) {

            LambdaExpressionTree lambda = (LambdaExpressionTree) tree;

        if (Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(lambda.parent()).parent()).parent()).is(Tree.Kind.MEMBER_SELECT)){
           String methodName = ( (MemberSelectExpressionTree) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(lambda.parent()).parent()).parent())).expression().symbolType().name();
           if (methodName.equals(STREAM)){
               lambda.body().accept(new AvoidSpringRepositoryCallInStreamCheckVisitor());
           }
        }

    }



    private class AvoidSpringRepositoryCallInStreamCheckVisitor extends BaseTreeVisitor {
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            if (REPOSITORY_METHOD.matches(tree)) {
                reportIssue(tree, REPOSITORY_METHODS_INSIDE_STREAM);
            } else {
                super.visitMethodInvocation(tree);
            }
        }

    }


}
