package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "EC206",
        name = "Developpement",
        description = AvoidNPlusOneQueryProblemCheck.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidNPlusOneQueryProblemCheck extends IssuableSubscriptionVisitor {

    protected static final String RULE_MESSAGE = "Avoid N+1 Query problem";

    private static final String SPRING_REPOSITORY = "org.springframework.data.repository.Repository";

    private final AvoidSpringRepositoryCallInLoopCheckVisitor visitorInFile = new AvoidSpringRepositoryCallInLoopCheckVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.INTERFACE);
    }

    @Override
    public void visitNode(Tree tree) {
        if (((ClassTree) tree).symbol().type().isSubtypeOf(SPRING_REPOSITORY)) {
            tree.accept(visitorInFile);
        }
    }

    private class AvoidSpringRepositoryCallInLoopCheckVisitor extends BaseTreeVisitor {


        @Override
        public void visitMethod(MethodTree tree) {

            if (
                    tree.returnType().symbolType().isSubtypeOf(Iterable.class.getName())
                            && hasNoCompliantAnnotation(tree)
            ) {
                reportIssue(tree, RULE_MESSAGE);
            } else {
                super.visitMethod(tree);
            }


        }

        boolean hasNoCompliantAnnotation(MethodTree tree) {
            return tree.modifiers().annotations().stream().noneMatch(
                    a -> isQueryAnnotationWithFetch(a) ||
                            a.symbolType().is("EntityGraph")
            );
        }

        private boolean isQueryAnnotationWithFetch(AnnotationTree a) {
            return a.symbolType().is("Query")
                    // kind = ASSIGNMENT Value
                    // variable = value
                    // children[2] = HQL

                    // Kind = String LITERAL
                    // Children[0] = HQL
                    ;
        }

    }
}
