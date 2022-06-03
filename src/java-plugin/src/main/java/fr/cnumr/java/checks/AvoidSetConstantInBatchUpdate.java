package fr.cnumr.java.checks;

import static fr.cnumr.java.checks.ConstOrLiteralDeclare.isLiteral;
import static java.util.Arrays.asList;
import static org.sonar.plugins.java.api.semantic.Type.Primitives.INT;
import static org.sonar.plugins.java.api.tree.Tree.Kind.MEMBER_SELECT;
import static org.sonar.plugins.java.api.tree.Tree.Kind.METHOD_INVOCATION;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Stream;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(key = "S78", name = "Developpement", 
	description = AvoidSetConstantInBatchUpdate.MESSAGERULE, 
	priority = Priority.MINOR, 
	tags = { "bug" })

public class AvoidSetConstantInBatchUpdate extends IssuableSubscriptionVisitor {

	protected static final String MESSAGERULE = "Avoid setting constants in batch update";
	private final AvoidSetConstantInBatchUpdateVisitor visitorInFile = new AvoidSetConstantInBatchUpdateVisitor();

	@Override
	public List<Kind> nodesToVisit() {
		return asList(
				Tree.Kind.FOR_EACH_STATEMENT, 
				Tree.Kind.FOR_STATEMENT, 
				Tree.Kind.WHILE_STATEMENT,
				Tree.Kind.DO_STATEMENT);
	}

	@Override
	public void visitNode(Tree tree) {
		tree.accept(visitorInFile);
	}

	private class AvoidSetConstantInBatchUpdateVisitor extends BaseTreeVisitor {

		private final MethodMatchers setters = MethodMatchers.create().ofSubTypes(PreparedStatement.class.getName())
				.names("setBoolean", "setByte", "setShort", "setInt", "setLong", "setFloat", "setDouble",
						"setBigDecimal", "setString")
				.addParametersMatcher(args-> args.size() == 2 && args.get(0).isPrimitive(INT)).build();

		@Override
		public void visitMethodInvocation(MethodInvocationTree tree) {
			if (setters.matches(tree) && isConstant(tree.arguments().get(1))) {
				reportIssue(tree, MESSAGERULE);
			} else {
				super.visitMethodInvocation(tree);
			}
		}
	}

	private static final boolean isConstant(Tree arg) {

		if (arg.is(METHOD_INVOCATION)) {
			MethodInvocationTree m = (MethodInvocationTree) arg;
			return Stream.of(ConstOrLiteralDeclare.values()).anyMatch(o-> o.isLiteralDeclare(m));
		} else if (arg.is(MEMBER_SELECT)) {
			MemberSelectExpressionTree m = (MemberSelectExpressionTree) arg;
			return Stream.of(ConstOrLiteralDeclare.values()).anyMatch(o-> o.isPublicMember(m));
		}
		return isLiteral(arg);
	}
}