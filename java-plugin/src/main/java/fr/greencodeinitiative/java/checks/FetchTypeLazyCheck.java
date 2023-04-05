package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;

import java.lang.reflect.AnnotatedType;
import java.util.List;

import static java.util.Collections.singletonList;

@Rule(
        key = "CRJVM205",
        name = "Developpement",
        description = AvoidFullSQLRequest.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class FetchTypeLazyCheck extends IssuableSubscriptionVisitor {
    protected static final String MESSAGERULE = "Use lazy fetch type instead of egger ";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return singletonList(Tree.Kind.ANNOTATION);
    }

    @Override
    public void visitNode(Tree tree) {
       AnnotatedType ann = tree.kind().getDeclaringClass().getAnnotatedSuperclass();
       System.out.println(ann);
    }
}
