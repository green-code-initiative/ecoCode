package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "S67",
        name = "Developpement",
        description = IncrementCheck.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class IncrementCheck extends IssuableSubscriptionVisitor {

	protected static final String  MESSAGERULE = "Use ++i instead of i++";
    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.POSTFIX_INCREMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        reportIssue(tree, MESSAGERULE);
    }
}