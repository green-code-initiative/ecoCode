package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static fr.greencodeinitiative.java.checks.ConstOrLiteralDeclare.isLiteral;
import static java.util.Arrays.asList;
import static org.sonar.plugins.java.api.semantic.Type.Primitives.INT;
import static org.sonar.plugins.java.api.tree.Tree.Kind.MEMBER_SELECT;
import static org.sonar.plugins.java.api.tree.Tree.Kind.METHOD_INVOCATION;

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
            List<AnnotationTree> listOfAnnotations = variableTree.type().annotations();
            for(AnnotationTree annotationTree : listOfAnnotations){

                System.out.print(annotationTree.toString());
            }
            reportIssue(tree, MESSAGERULE);
        }
    }


}
