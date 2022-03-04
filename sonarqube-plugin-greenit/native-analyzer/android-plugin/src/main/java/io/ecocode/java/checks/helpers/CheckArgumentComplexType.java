package io.ecocode.java.checks.helpers;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.ParenthesizedTree;
import org.sonar.plugins.java.api.tree.TypeCastTree;

public class CheckArgumentComplexType {

    /**
     * Method that gives the argument's child value when it's of a complex type
     *
     * @param argument the argument with a complex type
     * @return the child expression of the argument that matched (for example if the argument is being cast)
     */
    public static Object getChildExpression(ExpressionTree argument) {
        switch (argument.kind()) {
            case MEMBER_SELECT:
                MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) argument;
                return(memberSelectExpressionTree.identifier());
            case TYPE_CAST:
                TypeCastTree typeCastTree = (TypeCastTree) argument;
                return(typeCastTree.expression());
            case PARENTHESIZED_EXPRESSION:
                ParenthesizedTree parenthesizedTree = (ParenthesizedTree) argument;
                return(parenthesizedTree.expression());
            default:
                return argument;
        }
    }
}
