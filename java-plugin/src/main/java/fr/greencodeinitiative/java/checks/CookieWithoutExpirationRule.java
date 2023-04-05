package fr.greencodeinitiative.java.checks;


import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.declaration.MethodTreeImpl;
import org.sonar.java.model.expression.NewArrayTreeImpl;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;


import java.util.Collections;
import java.util.List;


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

 @Override
 public List<Tree.Kind> nodesToVisit() {
     return Collections.singletonList(Tree.Kind.CLASS);
 }

 @Override
 public void visitNode(Tree tree) {
     ClassTree classTree = (ClassTree) tree;

  

     for (Tree member : classTree.members()) {
         if (member.is(Tree.Kind.METHOD)) {
             MethodInvocationTree methodInvocation = getMethodInvocation(member);
             if (methodInvocation != null && isCookieConstructor(methodInvocation)) {
                 checkCookieExpiration(methodInvocation);
             }
         }
     }
 }

  

    private MethodInvocationTree getMethodInvocation(Tree tree) {

      List<Tree> children = ((MethodTreeImpl)tree).children();
      for (Tree child : children) {
        if (child instanceof MethodInvocationTree) {
         return (MethodInvocationTree) child;
        }
      }

        /*if (tree.is(Tree.Kind.EXPRESSION_STATEMENT)) {

            ExpressionTree children = ((AssignmentExpressionTree)tree).variable();
            if(children.is(Tree.Kind.IDENTIFIER))
            {
                IdentifierTree current = (IdentifierTree) children;
                current.name().equals("Cookie");
                 return (MethodInvocationTree) tree;
            }
        }*/
     return null;
 }

  

    private boolean isCookieConstructor(MethodInvocationTree methodInvocation) {
     ExpressionTree methodSelect = methodInvocation.methodSelect();
     if (methodSelect.is(Tree.Kind.IDENTIFIER)) {
         IdentifierTree identifier = (IdentifierTree) methodSelect;
         return identifier.name().equals("Cookie");
     }
     return false;
 }

  

         private void checkCookieExpiration(MethodInvocationTree methodInvocation) {
     List<ExpressionTree> arguments = methodInvocation.arguments();
     for (ExpressionTree argument : arguments) {
         if (argument.symbolType().fullyQualifiedName().equals(COOKIE_CLASS_NAME)) {
             checkCookieExpirationArgument(argument);
         }
     }
 }

  

    private void checkCookieExpirationArgument(ExpressionTree argument) {
     boolean hasExpiration = false;



  

     if (!hasExpiration) {
         reportIssue(argument, "Le cookie est créé sans date d'expiration.");
     }
 }
}