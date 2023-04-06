package fr.greencodeinitiative.java.checks;
import java.util.*;


import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.declaration.MethodTreeImpl;
import org.sonar.java.model.declaration.VariableTreeImpl;
import org.sonar.java.model.expression.MemberSelectExpressionTreeImpl;
import org.sonar.java.model.expression.NewArrayTreeImpl;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;


@Rule(
        key = "CRJVM207-JAVA",
        name = "Developpement",
        description = CookieWithoutExpirationRule.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class CookieWithoutExpirationRule extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Customer data must have end-of-life information";

    private static final String COOKIE_CLASS_NAME = "javax.servlet.http.Cookie";
    private static final String SET_MAX_AGE_METHOD_NAME = "setMaxAge";

    //TODO pour trouver les extend Cookie
    private static final MethodMatchers REPOSITORY_METHOD =
            MethodMatchers.create().ofSubTypes("SPRING_REPOSITORY").anyName().withAnyParameters()
                    .build();

 @Override
 public List<Tree.Kind> nodesToVisit() {
     return Collections.singletonList(Tree.Kind.CLASS);
 }
 private final CookieWithoutExpirationRuleCheckVisitor visitorInFile = new CookieWithoutExpirationRuleCheckVisitor();


 @Override
 public void visitNode(Tree tree) {

     tree.accept(visitorInFile);
     if (visitorInFile.hasANewCookieWithoutMaxDate())
     {
         reportIssue(tree, "Avoid not setting MaxAge");
     }
 }

    private class CookieWithoutExpirationRuleCheckVisitor extends BaseTreeVisitor {

        @Override
        public void visitReturnStatement(ReturnStatementTree tree) {
            this.scan((Tree)tree.expression());
        }

        @Override
        public void visitNewClass(NewClassTree tree) {
            if (tree.identifier().toString().equals("Cookie"))
            {
                System.out.println(tree.toString());
            }
        }
        @Override
        public void visitVariable(VariableTree tree) {

            for (Tree children : ((VariableTreeImpl) tree).children())
            {
                if (children.is(Tree.Kind.NEW_CLASS)
                        && ((IdentifierTree)((NewClassTree)children).identifier()).toString().equals("Cookie"))
                {
                    this.newCookieVariableName.add(tree.simpleName().toString());
                }
            }
            //todo appel à super();
        }
        private ArrayList<String> hasSetMaxAgeForCookiesVariableName = new ArrayList<>();
        private ArrayList<String>  newCookieVariableName = new ArrayList<>();

        public boolean hasANewCookieWithoutMaxDate()
        {
            for (String variableName : newCookieVariableName )
            {
                if (!hasSetMaxAgeForCookiesVariableName.contains(variableName))
                    return true;
            }
            return false;
        }
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            //System.out.println(((MemberSelectExpressionTree)tree.methodSelect()).identifier().name());
        	
        	if (tree.methodSelect().is(Kind.MEMBER_SELECT)) {
        		MemberSelectExpressionTree member = (MemberSelectExpressionTree)tree.methodSelect();
        		if (member.identifier().name().equals("setMaxAge"))
        		{
        			hasSetMaxAgeForCookiesVariableName.add(((MemberSelectExpressionTree)tree.methodSelect()).expression().toString());
        		}
        		
        	}
        }


    }
}