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
import io.ecocode.java.checks.helpers.CheckArgumentComplexTypeUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.Arguments;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;
import java.util.Optional;

/**
 * Check the call of the method "registerListener" of "android.hardware.SensorManager" with 4 parameters (the 4th one being the latency).
 * If it isn't present, report issue.
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
            if (sensorListenerMethodMatcher.matches(mit)
        	    && !isFourthArgumentPositiveNumber(mit.arguments())) {
                reportIssue(mit, "Prefer using a reported latency on your SensorManager to reduce the power consumption of the app");
            }
        }
    }

    /**
     * Method that checks if first, the method contains 4 or more argument, then if the 4th argument is a number (int, float ...)
     * Finally it checks if the 4th argument is strictly positive
     *
     * @param arguments the arguments of the method that matched
     * @return true if the 4th argument is a number
     */
    private boolean isFourthArgumentPositiveNumber(Arguments arguments) {
        //Check method contains 4 or more arguments
        if (arguments.size() > 3) {
            ExpressionTree thirdArgument = arguments.get(3);
            //Check 4th argument is a complex type (that needs to be managed)
            while (thirdArgument.is(Tree.Kind.TYPE_CAST, Tree.Kind.MEMBER_SELECT, Tree.Kind.PARENTHESIZED_EXPRESSION)) {
                thirdArgument = (ExpressionTree) CheckArgumentComplexTypeUtils.getChildExpression(thirdArgument);
            }
            Optional<Object> optionalThirdArgument = thirdArgument.asConstant();
            return optionalThirdArgument.isPresent()
                    //Check 4th argument is a number
                    && (thirdArgument.is(Tree.Kind.INT_LITERAL, Tree.Kind.LONG_LITERAL, Tree.Kind.FLOAT_LITERAL, Tree.Kind.DOUBLE_LITERAL)
                    //Check 4th argument is strictly positive
                    && ((Number) optionalThirdArgument.get()).doubleValue() > 0);
        }
        return false;
    }
}
