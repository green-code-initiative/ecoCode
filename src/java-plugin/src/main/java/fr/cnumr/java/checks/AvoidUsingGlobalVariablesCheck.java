package fr.cnumr.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;
import java.util.regex.Pattern;

@Rule(
        key = "D4",
        name = "Developpement",
        description = "<p>Prefer local variables to globals</p>",
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidUsingGlobalVariablesCheck extends IssuableSubscriptionVisitor {

	public static final String KEY = "PreferLocalVariablesToGlobalsCheck";
	private static final String ERROR_MESSAGE = "Avoid using global variables";
	public static final String GLOBALS_PATTERN = "^.*(static).*$";
	private Pattern pattern;

	@Override
	public List<Kind> nodesToVisit() {
		return ImmutableList.of(Kind.STATIC_INITIALIZER, Kind.VARIABLE, Kind.METHOD);
	}
	public void visitNode(Tree tree) {
		pattern = Pattern.compile(GLOBALS_PATTERN, Pattern.CASE_INSENSITIVE);

		if(tree.is(Kind.STATIC_INITIALIZER)){
			reportIssue(tree, String.format(ERROR_MESSAGE, tree));
		}
		if(tree.is(Kind.VARIABLE)){
			VariableTree variableTree = (VariableTree) tree;
			int modifiersSize = ((VariableTree) tree).modifiers().modifiers().size();
			for(int i = 0; i < modifiersSize;i++){
				String modifier = ((VariableTree) tree).modifiers().modifiers().get(i).modifier().toString();
				if (pattern.matcher(modifier).matches()) {
					reportIssue(tree, String.format(ERROR_MESSAGE, modifier));
				}
			}
		}
	}
}