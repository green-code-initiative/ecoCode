package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

@Rule(key = "CRJVM205", name = "Developpement",
        description = ForceUsingLazyFetchTypeInJPAEntity.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})

public class ForceUsingLazyFetchTypeInJPAEntity extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Force the use of LAZY FetchType";
    private static final String EAGER_KEYWORD = "EAGER";
    private static final String FETCH_KEYWORD = "fetch";
    private static final String ONE_TO_MANY =  "OneToMany";
    private static final String MANY_TO_ONE =  "ManyToOne";
    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {

        if (tree.is(Kind.VARIABLE)) {
            VariableTree variableTree = (VariableTree) tree;
            List<AnnotationTree> annotations = variableTree.modifiers().annotations();
            //-- get all annotations on the attribut
            for(AnnotationTree annotationTree : annotations){
                //--access only to One

                if(ONE_TO_MANY.equals(annotationTree.annotationType().symbolType().name())||MANY_TO_ONE.equals(annotationTree.annotationType().symbolType().name())){

                    Arguments arguments = annotationTree.arguments();

                    for (ListIterator<ExpressionTree> it = arguments.listIterator(); it.hasNext(); ) {

                        ExpressionTree argument = it.next();

                        AssignmentExpressionTree assignementExpression = (AssignmentExpressionTree)argument;

                        IdentifierTree variable = (IdentifierTree) assignementExpression.variable();

                        if(FETCH_KEYWORD.equals(variable.name())){
                            String fetchValue = ((MemberSelectExpressionTree)assignementExpression.expression()).identifier().name();
                            if(EAGER_KEYWORD.equals(fetchValue)){
                                reportIssue(tree, MESSAGERULE);
                            }
                        }

                    }

                }
            }

        }
    }


}
