package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(key = "EC66", name = "Developpement",
		description = FilteredColumnsAreIndexed.RULE_MESSAGE,
		priority = Priority.MINOR,
		tags = {
		"bug" }
)
public class FilteredColumnsAreIndexed extends IssuableSubscriptionVisitor {

	protected static final String RULE_MESSAGE = "Add index on filtered database columns (foreign keys, sql filters...)";

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return Arrays.asList(Tree.Kind.METHOD, Tree.Kind.VARIABLE);
	}

	@Override
	public void visitNode(final Tree tree) {
		SymbolMetadata symMeta = null;
		IdentifierTree name = null;
		if (tree instanceof MethodTree) {
			final MethodTree methodTree = (MethodTree) tree;
			symMeta = methodTree.symbol().metadata();
			name = methodTree.simpleName();
		} else if (tree instanceof VariableTree) {
			final VariableTree variableTree = (VariableTree) tree;
			symMeta = variableTree.symbol().metadata();
			name = variableTree.simpleName();
		}
		final boolean isRelation = containsAnnotation(symMeta, "ManyToOne", "OneToMany", "ManyToMany", "OneToOne");
		final boolean hasIndex = containsAnnotation(symMeta, "Index", "Id");
		final boolean hasJoinIndex = hasJoinIndex(symMeta);
		if (isRelation && !hasIndex && !hasJoinIndex) {
			reportIssue(name, "Add @Index on foreign key");
		}
	}

	private boolean hasJoinIndex(final SymbolMetadata symMeta) {
		if (symMeta == null) {
			return false;
		}
		for (final SymbolMetadata.AnnotationInstance annotation : symMeta.annotations()) {
			final Type type = annotation.symbol().type();
			if (type.name().equals("JoinTable")) {
				// Check if @JoinTable contains indexes={@Index()}
			}
		}
		return false;
	}

	private static boolean containsAnnotation(final SymbolMetadata symMeta, final String... names) {
		if (symMeta == null || names == null) {
			return false;
		}
		for (final SymbolMetadata.AnnotationInstance annotation : symMeta.annotations()) {
			final Type type = annotation.symbol().type();
			for (final String name : names) {
				if (type.name().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
}
