package fr.cnumr.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.symbols.Symbol;
import org.sonar.plugins.python.api.tree.*;

import java.util.*;

@Rule(
        key = "S72",
        name = "Developpement",
        description = AvoidSQLRequestInLoop.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidSQLRequestInLoop extends PythonSubscriptionCheck {
    // TODO: Handle ORM lib
    private static final List<String> SQL_LIBS = Arrays.asList("cx_Oracle", "mysql.connector", "psycopg2", "pymssql", "pyodbc", "sqlite3");

    protected static final String MESSAGE_RULE = "Avoid performing SQL queries within a loop";

    private boolean isUsingSqlLib = false;

    @Override
    public void initialize(Context context) {
        context.registerSyntaxNodeConsumer(Tree.Kind.FILE_INPUT, this::visitFile);
        context.registerSyntaxNodeConsumer(Tree.Kind.CALL_EXPR, this::checkCallExpression);
    }

    private void visitFile(SubscriptionContext ctx) {
        FileInput tree = (FileInput) ctx.syntaxNode();
        SymbolsFromImport visitor = new SymbolsFromImport();
        tree.accept(visitor);
        visitor.symbols.stream()
                .filter(Objects::nonNull)
                .map(Symbol::fullyQualifiedName)
                .filter(Objects::nonNull)
                .forEach(qualifiedName -> {
                    if (SQL_LIBS.contains(qualifiedName)) {
                        isUsingSqlLib = true;
                    }
                });
    }

    private static class SymbolsFromImport extends BaseTreeVisitor {
        private Set<Symbol> symbols = new HashSet<>();

        @Override
        public void visitAliasedName(AliasedName aliasedName) {
            List<Name> names = aliasedName.dottedName().names();
            symbols.add(names.get(names.size() - 1).symbol());
        }
    }
    private void checkCallExpression(SubscriptionContext context) {
        CallExpression expression = (CallExpression) context.syntaxNode();

        if (expression.callee().is(Tree.Kind.QUALIFIED_EXPR)) {
            String name = ((QualifiedExpression) expression.callee()).name().name();
            if (isUsingSqlLib && "execute".equals(name) && hasLoopParent(expression)) {
                context.addIssue(expression, AvoidSQLRequestInLoop.MESSAGE_RULE);
            }
        }
    }

    private boolean hasLoopParent(Tree tree) {
        for (Tree parent = tree.parent(); parent != null; parent = parent.parent()) {
            Tree.Kind kind = parent.getKind();
            if (kind == Tree.Kind.FOR_STMT || kind == Tree.Kind.WHILE_STMT) {
                return true;
            }
        }
        return false;
    }
}