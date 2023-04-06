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
    private static final String QUERY = "org.springframework.data.jpa.repository.Query";
    private static final String ENTITY_GRAPH = "org.springframework.data.jpa.repository.EntityGraph";
    private static final String LEFT_JOIN = "LEFT JOIN";
    private static final String VALUE = "value";

    private final AvoidNPlusOneQueryProblemCheckVisitor visitorInFile = new AvoidNPlusOneQueryProblemCheckVisitor();

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

    private class AvoidNPlusOneQueryProblemCheckVisitor extends BaseTreeVisitor {

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
                            a.symbolType().is(ENTITY_GRAPH)
            );
        }

        private boolean isQueryAnnotationWithFetch(AnnotationTree a) {
            return a.symbolType().is(QUERY)
            && (a.arguments().stream().filter(arg -> Tree.Kind.STRING_LITERAL.equals(arg.kind()))
                    .anyMatch(arg -> arg.firstToken().text().contains(LEFT_JOIN))
                    || (a.arguments().stream().filter(arg -> Tree.Kind.ASSIGNMENT.equals(arg.kind()))
                    .filter(arg -> VALUE.equals(arg.firstToken().text()))
                    .anyMatch(arg -> arg.lastToken().text().contains(LEFT_JOIN))));
        }
    }
}
