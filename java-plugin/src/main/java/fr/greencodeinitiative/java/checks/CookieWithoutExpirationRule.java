package fr.greencodeinitiative.java.checks;
import java.util.*;


import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.declaration.VariableTreeImpl;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;


@Rule(
        key = "CRJVM207-JAVA",
        name = "Developpement",
        description = CookieWithoutExpirationRule.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"smell"})

public class CookieWithoutExpirationRule extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Customer data must have end-of-life information, so cookies must have a maxAge";

 
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
     //Class visitor
     if (visitorInFile.hasANewCookieWithoutMaxDate())
     {
         //if we found a cookie that maxDate is not initialized, we report issue
         reportIssue(tree, "Avoid not setting MaxAge");
     }
 }

    private class CookieWithoutExpirationRuleCheckVisitor extends BaseTreeVisitor {


        // storage of variable name for setMaxAge
        private ArrayList<String> hasSetMaxAgeForCookiesVariableName = new ArrayList<>();
        // storage of variable name for New Cookies
        private ArrayList<String>  newCookieVariableName = new ArrayList<>();
        @Override
        public void visitReturnStatement(ReturnStatementTree tree) {
            this.scan((Tree)tree.expression());
        }

        @Override
        public void visitVariable(VariableTree tree) {
            //when we visit variable affectation
            for (Tree children : ((VariableTreeImpl) tree).children())
            {
                //if we found an affectation
                if (children.is(Tree.Kind.NEW_CLASS)
                        && ((IdentifierTree)((NewClassTree)children).identifier()).toString().equals("Cookie"))
                {
                    //if this is a New Cookie affectation, we store the name of the variable
                    this.newCookieVariableName.add(tree.simpleName().toString());
                }
                else
                    super.visitVariable(tree);
            }
        }


        public boolean hasANewCookieWithoutMaxDate()
        {
            //parcours des variables pour lesquelles on a fait un new Cookie
            for (String variableName : newCookieVariableName )
            {
                if (!hasSetMaxAgeForCookiesVariableName.contains(variableName))
                    //si on n'a pas fait setMaxAge pour ces variables
                    return true;
            }
            return false;
        }
        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            //lors de la visite d'une méthode
            if (tree.methodSelect().is(Kind.MEMBER_SELECT)) {

                if (((MemberSelectExpressionTree)tree.methodSelect()).identifier().name().equals("setMaxAge"))
                {

                    //si on est sur un setMaxAge, on enregistre la variable qui est affectée
                    hasSetMaxAgeForCookiesVariableName.add(((MemberSelectExpressionTree)tree.methodSelect()).expression().toString());
                }
            }

        }


    }
}