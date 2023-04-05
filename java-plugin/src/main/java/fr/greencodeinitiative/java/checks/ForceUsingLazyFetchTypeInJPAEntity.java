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
                ;
                if("OneToMany".equals(annotationTree.annotationType().symbolType().name())||"ManyToOne".equals(annotationTree.annotationType().symbolType().name())){

                    Arguments arguments = annotationTree.arguments();

                    for (ListIterator<ExpressionTree> it = arguments.listIterator(); it.hasNext(); ) {

                        ExpressionTree argument = it.next();

                        AssignmentExpressionTree assignementExpression = (AssignmentExpressionTree)argument;

                        IdentifierTree variable = (IdentifierTree) assignementExpression.variable();

                        if("fetch".equals(variable.name())){
                            String fetchValue = ((MemberSelectExpressionTree)assignementExpression.expression()).identifier().name();
                            if("EAGER".equals(fetchValue)){
                                reportIssue(tree, MESSAGERULE);
                            }
                        }

                    }

                }
            }

        }
    }


}
