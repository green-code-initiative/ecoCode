package fr.greencodeinitiative.java.checks;

import java.util.List;
import java.util.Optional;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Type;
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
    private static final String JOIN_FETCH = "JOIN FETCH";
    private static final String VALUE = "value";

    private static final List<String> SPRING_PROBLEMATIC_ANNOTATIONS = List.of(
            "javax.persistence.OneToMany",
            "javax.persistence.ManyToOne",
            "javax.persistence.ManyToMany"
    );

    private final AvoidNPlusOneQueryProblemCheckVisitor visitorInFile = new AvoidNPlusOneQueryProblemCheckVisitor();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.INTERFACE);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;

        if (isSpringRepository(classTree) && hasManyToOneAnnotations(classTree)) {
            tree.accept(visitorInFile);
        }
    }

    //Check if the repository entity contains parameter annotate with ManyToOne, OneToMany or ManyToMany
    private boolean hasManyToOneAnnotations(ClassTree classTree) {
        Optional<Type> crudRepositoryInterface = classTree.symbol().interfaces().stream()
                .filter(t -> t.isSubtypeOf(SPRING_REPOSITORY))
                .findFirst();

        return crudRepositoryInterface.map(type -> type
                        .typeArguments()
                        .get(0)
                        .symbol()
                        .declaration()
                        .members()
                        .stream()
                        .filter(t -> t.is(Tree.Kind.VARIABLE))
                        .anyMatch(t -> ((VariableTree) t).modifiers()
                                .annotations()
                                .stream()
                                .anyMatch(a ->
                                        SPRING_PROBLEMATIC_ANNOTATIONS.stream().anyMatch(a.symbolType()::isSubtypeOf)
                                )))
                .orElse(false);
    }

    /**
     * Check if this class implements jpa Repository
     * @param classTree the class to analyze
     * @return true  if this class implements jpa Repository
     */
    private static boolean isSpringRepository(ClassTree classTree) {
        return classTree.symbol().type().isSubtypeOf(SPRING_REPOSITORY);
    }

    private class AvoidNPlusOneQueryProblemCheckVisitor extends BaseTreeVisitor {

        @Override
        public void visitMethod(MethodTree tree) {
            if (
                    //Check all methods of the repository that return an iterable
                    tree.returnType().symbolType().isSubtypeOf(Iterable.class.getName())
                            && hasNoCompliantAnnotation(tree)
            ) {
                reportIssue(tree, RULE_MESSAGE);
            } else {
                super.visitMethod(tree);
            }
        }

        /**
         * Check if the method is correctly annotated with Query or EntityGraph
         * @param tree the method to analyze
         * @return true if the method is not correctly annotated
         */
        boolean hasNoCompliantAnnotation(MethodTree tree) {
            return tree.modifiers().annotations().stream().noneMatch(
                    a -> isQueryAnnotationWithFetch(a) ||
                            a.symbolType().is(ENTITY_GRAPH)
            );
        }

        /**
         * Check if the method is correctly annotated with Query. That is to say the value parameter of the annotation
         * contains an sql query with {@link #JOIN_FETCH}.
         *
         * Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")
         * With this first solution, the annotation contains a single argument containing a single token which is the sql query
         *
         * Query(value = "SELECT p FROM User p LEFT JOIN FETCH p.roles")
         * With this second solution, the annotation contains a single argument containing three tokens which are :
         * - value
         * - equal sign
         * - sql query
         *
         * @param annotationTree annotation to analyze
         * @return true if annotationTree is a query annotation with sql query that contains "JOIN FETCH"
         */
        private boolean isQueryAnnotationWithFetch(AnnotationTree annotationTree) {
            return annotationTree.symbolType().is(QUERY)
                    && getSqlQuery(annotationTree).map(this::isValidSqlQuery).orElse(false);
        }

        /**
         * @param annotationTree annotation whose symbolType is {@link #QUERY}
         * @return the SQLQuery which is in annotation value attribute
         */
        private Optional<String> getSqlQuery(AnnotationTree annotationTree) {
            return getSqlQueryFromStringLiteralArgument(annotationTree)
                    .or(() -> getSqlQueryFromAssignementArgument(annotationTree));
        }

        /**
         * @param annotationTree annotation whose symbolType is {@link #QUERY}
         * @return the SQLQuery which is in annotation value attribute
         */
        private Optional<String> getSqlQueryFromStringLiteralArgument(AnnotationTree annotationTree) {
            return annotationTree.arguments().stream()
                    .filter(arg -> Tree.Kind.STRING_LITERAL.equals(arg.kind()))
                    .findAny()
                    .map(arg -> arg.firstToken().text());
        }

        /**
         * @param annotationTree annotation whose symbolType is {@link #QUERY}
         * @return the SQLQuery which is in annotation value attribute
         */
        private Optional<String> getSqlQueryFromAssignementArgument(AnnotationTree annotationTree) {
            return annotationTree.arguments().stream()
                    .filter(arg -> Tree.Kind.ASSIGNMENT.equals(arg.kind()))
                    .filter(arg -> VALUE.equals(arg.firstToken().text()))
                    .map(arg -> arg.lastToken().text())
                    .findAny();
        }

        /**
         *
         * @param sqlQuery sql query to analyze
         * @return true sqlQuery contains {@link #JOIN_FETCH}
         */
        private boolean isValidSqlQuery(String sqlQuery) {
            return sqlQuery.contains(JOIN_FETCH);
        }
    }
}
