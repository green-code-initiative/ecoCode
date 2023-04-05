package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.ast.parser.ArgumentListTreeImpl;
import org.sonar.java.model.declaration.AnnotationTreeImpl;
import org.sonar.java.model.expression.AssignmentExpressionTreeImpl;
import org.sonar.java.model.expression.MemberSelectExpressionTreeImpl;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeTree;

import java.lang.reflect.AnnotatedType;
import java.util.List;

import static java.util.Collections.singletonList;

@Rule(
        key = "CRJVM205",
        name = "Developpement",
        description = AvoidFullSQLRequest.MESSAGERULE,
        priority = Priority.MINOR,
        tags = { "bug" } )
public class FetchTypeLazyCheck extends IssuableSubscriptionVisitor {
    protected static final String MESSAGERULE = "Use lazy fetch type instead of egger ";
    public static final String MANY_TO_ONE = "ManyToOne";
    public static final String LAZY = "LAZY";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return singletonList( Tree.Kind.ANNOTATION );
    }

    @Override
    public void visitNode( Tree tree ) {
        TypeTree typeTree = (( AnnotationTreeImpl ) tree).annotationType();
        if ( MANY_TO_ONE.equals( typeTree.symbolType().name() ) ) {
            List<Tree> annotationListTree = (( AnnotationTreeImpl ) tree).children();
            if ( !annotationListTree.isEmpty() && annotationListTree.size() > 1 ) {
                ArgumentListTreeImpl argumentListTree = ( ArgumentListTreeImpl ) annotationListTree.get( 2 );
                List<Tree> argumentListTreeChildren = argumentListTree.getChildren();
                if ( !argumentListTreeChildren.isEmpty() && argumentListTreeChildren.size() > 1 ) {
                    AssignmentExpressionTreeImpl assignmentExpressionTree = ( AssignmentExpressionTreeImpl ) argumentListTreeChildren.get( 1 );
                    List<Tree> assignmentExpressionTreeChildren = assignmentExpressionTree.getChildren();
                    if ( !assignmentExpressionTreeChildren.isEmpty() && assignmentExpressionTreeChildren.size() > 1 ) {
                        MemberSelectExpressionTreeImpl memberSelectExpressionTree = ( MemberSelectExpressionTreeImpl ) assignmentExpressionTreeChildren.get( 2 );
                        if ( ! LAZY.equals( memberSelectExpressionTree.identifier().name() ) ) {
                            reportIssue( tree, MESSAGERULE );
                        }
                    }
                }
            }
        }

    }
}
