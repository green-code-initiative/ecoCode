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

package io.ecocode.java.checks.batch;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

/**
 * Check the call of the method "registerListener" of "android.hardware.SensorManager" with 4 parameters (the 4th one being maxReportLatencyUs).
 * If argument value isn't present, report issue.
 */
@Rule(key = "EBAT002", name = "ecoCodeSensorCoalesce")
public class SensorCoalesceRule extends IssuableSubscriptionVisitor {

    private final MethodMatchers sensorListenerMethodMatcher = MethodMatchers.create().ofTypes("android.hardware.SensorManager").names("registerListener").withAnyParameters().build();

    public SensorCoalesceRule() {
        super();
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            if (sensorListenerMethodMatcher.matches(mit)) {
                if (!isFourthArgumentNumberPositive(mit.arguments())) {
                    reportIssue(mit, "Avoid using registerListener without a fourth parameter maxReportLatencyUs");
                }
            }
        }
    }

    /**
     * Method that checks if first, the method contains 4 or more argument, then if the 4th argument is a number (int, float ...)
     * Finally it checks if the 4th argument is strictly positive
     *
     * @param arguments the arguments of the method
     */
    private boolean isFourthArgumentNumberPositive(Arguments arguments) {
        if (arguments.size() > 3) {
            while (arguments.get(3).is(Tree.Kind.TYPE_CAST)
                    || arguments.get(3).is(Tree.Kind.MEMBER_SELECT)
                    || arguments.get(3).is(Tree.Kind.PARENTHESIZED_EXPRESSION)){
                arguments.set(3, (ExpressionTree) checkArgumentComplexType(arguments.get(3)));
            }
            return arguments.get(3).asConstant().isPresent()
                    && ((arguments.get(3).is(Tree.Kind.INT_LITERAL)
                    || arguments.get(3).is(Tree.Kind.LONG_LITERAL)
                    || arguments.get(3).is(Tree.Kind.FLOAT_LITERAL)
                    || arguments.get(3).is(Tree.Kind.DOUBLE_LITERAL))
                    && ((Number)arguments.get(3).asConstant().get()).doubleValue() > 0);
        }
        return false;
    }

    /**
     * Method that gives the argument child value when it's of a complex type
     *
     * @param argument the argument with a complex type
     */
    private Object checkArgumentComplexType(ExpressionTree argument) {
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
