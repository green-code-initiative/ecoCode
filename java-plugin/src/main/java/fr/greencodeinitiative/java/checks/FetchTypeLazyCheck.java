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

import java.util.List;

import static java.util.Collections.singletonList;

@Rule(
        key = "CRJVM205",
        name = "Developpement",
        description = AvoidFullSQLRequest.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class FetchTypeLazyCheck extends IssuableSubscriptionVisitor {
    public static final String MANY_TO_ONE = "ManyToOne";
    public static final String LAZY = "LAZY";
    public static final String ONE_TO_MANY = "OneToMany";
    protected static final String MESSAGERULE = "Use lazy fetch type instead of egger";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return singletonList(Tree.Kind.ANNOTATION);
    }


    @Override
    public void visitNode(Tree tree) {
        TypeTree typeTree = ((AnnotationTreeImpl) tree).annotationType();
        if (MANY_TO_ONE.equals(typeTree.symbolType().name()) || ONE_TO_MANY.equals(typeTree.symbolType().name()) || "ManyToMany".equals(typeTree.symbolType().name())) {
            visitAnnotations(tree);
        }
    }

    private void visitAnnotations(final Tree tree) {
        List<Tree> annotationListTree = ((AnnotationTreeImpl) tree).children();
        if (!annotationListTree.isEmpty() && annotationListTree.size() > 1) {
            ArgumentListTreeImpl argumentListTree = (ArgumentListTreeImpl) annotationListTree.get(2);
            visitArguments(argumentListTree, tree);
        }
    }

    private void visitArguments(final ArgumentListTreeImpl argumentListTree, final Tree tree) {
        List<Tree> argumentListTreeChildren = argumentListTree.getChildren();
        if (!argumentListTreeChildren.isEmpty() && argumentListTreeChildren.size() > 1) {
            AssignmentExpressionTreeImpl assignmentExpressionTree = (AssignmentExpressionTreeImpl) argumentListTreeChildren.get(1);
            visitAssignements(assignmentExpressionTree, tree);
        }
    }

    private void visitAssignements(final AssignmentExpressionTreeImpl assignmentExpressionTree, final Tree tree) {
        List<Tree> assignmentExpressionTreeChildren = assignmentExpressionTree.getChildren();
        if (!assignmentExpressionTreeChildren.isEmpty() && assignmentExpressionTreeChildren.size() > 1) {
            MemberSelectExpressionTreeImpl memberSelectExpressionTree = (MemberSelectExpressionTreeImpl) assignmentExpressionTreeChildren.get(2);
            checkFetchTypeManyToOne(tree, memberSelectExpressionTree);
        }
    }

    private void checkFetchTypeManyToOne(final Tree tree, final MemberSelectExpressionTreeImpl memberSelectExpressionTree) {
        if (!LAZY.equals(memberSelectExpressionTree.identifier()
                .name())) {
            reportIssue(tree, MESSAGERULE);
        }
    }
}
