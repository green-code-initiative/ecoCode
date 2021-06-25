package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.declaration.VariableTreeImpl;
import org.sonar.java.model.statement.ExpressionStatementTreeImpl;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.*;

@Rule(
        key = "S69",
        name = "Developpement",
        description = "Remplacer les $i++ par ++$i",
        priority = Priority.MINOR,
        tags = {"bug"})
public class NoFunctionCallWhenDeclaringForLoop extends IssuableSubscriptionVisitor {

    private static final Map<String, Collection<Integer>> linesWithIssuesByClass = new HashMap<>();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.FOR_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        // METHOD_INVOCATION
        ForStatementTree method = (ForStatementTree) tree;

        checkCondition(method.condition());
        //update
        // initaliser
        checkUpdate(method.update());
        checkInitialiser(method.initializer());
    }

    public void checkInitialiser(ListTree<StatementTree> listTree) {
        StatementTree st = listTree.get(0);
        List<Tree> children = ((VariableTreeImpl) st).children();
        children.forEach(child -> {
            isMethodInvocation(child);
        });
    }

    public void checkUpdate(ListTree<StatementTree> listTree) {
        StatementTree st = listTree.get(0);
        List<Tree> children = ((ExpressionStatementTreeImpl) st).children();
        children.forEach(child -> {
            isMethodInvocation(child);
        });
    }

    public void checkCondition(ExpressionTree expressionTree) {
        // expressionTree.
        BinaryExpressionTree binaryExpressionTree = (BinaryExpressionTree) expressionTree;
        // if (binaryExpressionTree.rightOperand().is(Tree.Kind.METHOD_INVOCATION) || binaryExpressionTree.leftOperand().is(Tree.Kind.METHOD_INVOCATION)) {
        isMethodInvocation(binaryExpressionTree.rightOperand());
        isMethodInvocation(binaryExpressionTree.leftOperand());

    }

    public void isMethodInvocation(Tree tree) {
        if (lineAlreadyHasThisIssue(tree)) return;
        if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            repport(tree);
            return;

        }
    }


    private void repport(Tree tree) {
        if (tree.firstToken() != null) {
            final String classname = getFullyQualifiedNameOfClassOf(tree);
            final int line = tree.firstToken().line();

            if (!linesWithIssuesByClass.containsKey(classname)) {
                linesWithIssuesByClass.put(classname, new ArrayList<>());
            }

            linesWithIssuesByClass.get(classname).add(line);
        }

        reportIssue(tree, "New class!");
    }

    private boolean lineAlreadyHasThisIssue(Tree tree) {
        if (tree.firstToken() != null) {
            final String classname = getFullyQualifiedNameOfClassOf(tree);
            final int line = tree.firstToken().line();

            return linesWithIssuesByClass.containsKey(classname)
                    && linesWithIssuesByClass.get(classname).contains(line);
        }

        return false;
    }

    private String getFullyQualifiedNameOfClassOf(Tree tree) {
        Tree parent = tree.parent();

        while (parent != null) {
            final Tree grandparent = parent.parent();

            if (parent.is(Tree.Kind.CLASS) && grandparent != null && grandparent.is(Tree.Kind.COMPILATION_UNIT)) {
                final String packageName = getPackageName((CompilationUnitTree) grandparent);

                return packageName.isEmpty()
                        ? getClassName((ClassTree) parent)
                        : packageName + '.' + getClassName((ClassTree) parent);
            }

            parent = parent.parent();
        }

        return "";
    }


    private String getPackageName(CompilationUnitTree compilationUnitTree) {
        final PackageDeclarationTree packageDeclarationTree = compilationUnitTree.packageDeclaration();
        if (packageDeclarationTree == null) {
            return "";
        }

        return packageDeclarationTree.packageName().toString();
    }

    private String getClassName(ClassTree classTree) {
        final IdentifierTree simpleName = classTree.simpleName();
        return simpleName == null
                ? ""
                : simpleName.toString();
    }


}
