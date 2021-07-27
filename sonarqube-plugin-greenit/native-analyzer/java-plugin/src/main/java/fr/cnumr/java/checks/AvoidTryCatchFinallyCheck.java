package fr.cnumr.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import java.util.Collections;
import java.util.List;

@Rule(key = "S34", 
		name = "Developpement", 
		description = "Avoid the use of try-catch-finally when not needed", 
		priority = Priority.MINOR, 
		tags = {"bug" })

public class AvoidTryCatchFinallyCheck extends IssuableSubscriptionVisitor {

	private final AvoidTryCatchFinallyCheckVisitor visitor = new AvoidTryCatchFinallyCheckVisitor();

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return Collections.singletonList(Tree.Kind.TRY_STATEMENT);
	}

	@Override
	public void visitNode(Tree tree) {
		if (!visitor.containsThrowsException((TryStatementTree) tree)) {
			reportIssue(tree, "Avoid the use of try-catch-finally when not needed");
		}
	}

	private class AvoidTryCatchFinallyCheckVisitor extends BaseTreeVisitor {

		private boolean isThrowingException;

		public boolean containsThrowsException(TryStatementTree tree) {
			isThrowingException = false;
			tree.block().accept(visitor);
			return isThrowingException;
		}

		@Override
		public void visitMethodInvocation(MethodInvocationTree tree) {
			if (isThrowingException(tree.symbol())) {
				return;
			}
			super.visitMethodInvocation(tree);
		}

		@Override
		public void visitNewClass(NewClassTree tree) {
			if (isThrowingException(tree.constructorSymbol())) {
				return;
			}
			super.visitNewClass(tree);
		}

		private boolean isThrowingException(Symbol symbol) {
			isThrowingException |= symbol.isUnknown() || ((MethodSymbol) symbol).thrownTypes().size() > 0;

			return isThrowingException;

		}
	}

}
