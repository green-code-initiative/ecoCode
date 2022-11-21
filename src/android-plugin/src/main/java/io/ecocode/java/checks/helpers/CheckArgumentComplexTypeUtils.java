/*
 * ecoCode SonarQube Plugin
 * Copyright (C) 2020-2021 Snapp' - Université de Pau et des Pays de l'Adour
 * mailto: contact@ecocode.io
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package io.ecocode.java.checks.helpers;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.ParenthesizedTree;
import org.sonar.plugins.java.api.tree.TypeCastTree;

public class CheckArgumentComplexTypeUtils {

    private CheckArgumentComplexTypeUtils() {
    }

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
                return (memberSelectExpressionTree.identifier());
            case TYPE_CAST:
                TypeCastTree typeCastTree = (TypeCastTree) argument;
                return (typeCastTree.expression());
            case PARENTHESIZED_EXPRESSION:
                ParenthesizedTree parenthesizedTree = (ParenthesizedTree) argument;
                return (parenthesizedTree.expression());
            default:
                return argument;
        }
    }
}
