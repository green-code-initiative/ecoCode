package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

@Rule(key = "EC80", name = "Developpement",
        description = ForceUsingLazyFetchTypeInJPAEntity.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class ForceUsingLazyFetchTypeInJPAEntity extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Force the use of LAZY FetchType";
    private static final String EAGER_KEYWORD = "EAGER";
    private static final String FETCH_KEYWORD = "fetch";
    private static final String ONE_TO_MANY = "OneToMany";
    private static final String MANY_TO_ONE = "ManyToOne";
    private static final String ONE_TO_ONE = "OneToOne";
    private static final String MANY_TO_MANY = "ManyToMany";

    @Override
    public List<Kind> nodesToVisit() {
        return List.of(Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        if (!tree.is(Kind.VARIABLE)) {
            return;
        }
        VariableTree variableTree = (VariableTree) tree;
        List<AnnotationTree> annotations = variableTree.modifiers().annotations();
        // get all annotations on the attribute
        for (AnnotationTree annotationTree : annotations) {
            if (!needToCheckExplicitAnnotation(annotationTree.annotationType().symbolType().name())) {
                // no Explicit annotation (@OneToMany, @ManyToOne, @ManyToMany, @OneToOne) was found,
                // and we don't need to investigate further
                continue;
            }
            Arguments arguments = annotationTree.arguments();
            performsCheck(arguments, annotationTree, tree);
        }
    }

    /**
     * perform the check for two case :
     * fetch keyword is found -> parse the annotation argument and search for eager keyword
     * fetch keyword was not found -> we use the default value of fetch type by join type :
     * OneToMany: Lazy
     * ManyToOne: Eager
     * ManyToMany: Lazy
     * OneToOne: Eager
     * reporting the issues if necessary
     */
    private void performsCheck(Arguments arguments, AnnotationTree annotationTree, Tree tree) {
        boolean fetchFound = false;
        for (ExpressionTree argument : arguments) {
            AssignmentExpressionTree assignmentExpression = (AssignmentExpressionTree) argument;
            IdentifierTree variable = (IdentifierTree) assignmentExpression.variable();

            if (!FETCH_KEYWORD.equals(variable.name())) {
                // no need to continue checking this argument
                continue;
            }
            String fetchValue = ((MemberSelectExpressionTree) assignmentExpression.expression()).identifier().name();
            fetchFound = true;
            if (EAGER_KEYWORD.equals(fetchValue)) {
                reportIssue(tree, MESSAGERULE);
            }
        }
        //- The default case of the ManyToOne and the OneToOne
        // the fetch keyword is not explicit
        if (fetchFound) {
            return;
        }
        String symbolType = annotationTree.annotationType().symbolType().name();
        if ((MANY_TO_ONE.equals(symbolType) || ONE_TO_ONE.equals(symbolType))) {
            reportIssue(tree, MESSAGERULE);
        }
    }

    /**
     * @param symbolType the annotation type name : @OneToMany, @ManyToOne...
     * @return true if searched annotation was found and checks need to be performed
     */
    private boolean needToCheckExplicitAnnotation(String symbolType) {
        return ONE_TO_MANY.equals(symbolType)
                || MANY_TO_ONE.equals(symbolType)
                || ONE_TO_ONE.equals(symbolType)
                || MANY_TO_MANY.equals(symbolType);
    }
}
