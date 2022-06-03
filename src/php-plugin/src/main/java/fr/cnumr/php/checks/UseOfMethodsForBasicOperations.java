package fr.cnumr.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.php.api.tree.ScriptTree;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.declaration.ClassDeclarationTree;
import org.sonar.plugins.php.api.tree.declaration.ClassMemberTree;
import org.sonar.plugins.php.api.tree.declaration.MethodDeclarationTree;
import org.sonar.plugins.php.api.tree.expression.FunctionCallTree;
import org.sonar.plugins.php.api.tree.statement.*;
import org.sonar.plugins.php.api.visitors.PHPSubscriptionCheck;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Rule(
        key = "D2",
        name = "Developpement",
        description = UseOfMethodsForBasicOperations.ERROR_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class UseOfMethodsForBasicOperations extends PHPSubscriptionCheck  {

    protected static final String ERROR_MESSAGE = "Use of methods for basic operations";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.FUNCTION_CALL);
    }

    @Override
    public void visitNode(Tree tree) {
        AtomicBoolean contains = new AtomicBoolean(false);

        final FunctionCallTree functionTree = ((FunctionCallTree) tree);
        final String functionName = functionTree.callee().toString();

        final List<Tree> parents = this.getAllParent(tree, new ArrayList<>());

        parents.forEach(parent -> {
            if(parent.is(Tree.Kind.SCRIPT)) {

                final ScriptTree specific = (ScriptTree) parent;
                final List<StatementTree> trees = specific.statements();

                trees.forEach(statement -> {

                    if(statement.is(Tree.Kind.CLASS_DECLARATION)) {

                        final List<ClassMemberTree> methodDeclarations = ((ClassDeclarationTree) statement).members()
                                .stream()
                                .filter(member -> member.is(Tree.Kind.METHOD_DECLARATION))
                                .map(MethodDeclarationTree.class::cast)
                                .filter(declarationTree -> this.isFunctionDeclared(declarationTree, functionName))
                                        .collect(Collectors.toList());

                        if(methodDeclarations != null && !methodDeclarations.isEmpty()) {
                            contains.set(true);
                        }
                    }
                });
            }
        });

        if(!contains.get()) {
            context().newIssue(this, tree, ERROR_MESSAGE);
        }
    }

    public boolean isFunctionDeclared(final MethodDeclarationTree method, final String name) {
        if(method == null) {
            return false;
        }

        return method.name().text()
                .trim()
                .equals(name.trim());
    }

    public List<Tree> getAllParent(final Tree tree, final List<Tree> list) {
        if(tree == null)
            return list;

        final Tree parent = tree.getParent();

        if(parent == null)
            return list;

        list.add(parent);

        return this.getAllParent(parent, list);
    }
}
