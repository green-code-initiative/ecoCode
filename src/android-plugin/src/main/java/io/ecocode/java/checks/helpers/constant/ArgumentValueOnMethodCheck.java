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
package io.ecocode.java.checks.helpers.constant;

import com.google.common.collect.ImmutableList;
import io.ecocode.java.checks.helpers.CheckArgumentComplexType;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;
import java.util.Optional;

public abstract class ArgumentValueOnMethodCheck extends IssuableSubscriptionVisitor {

    private final MethodSpecs[] methodsSpecs;
    private int[] paramsPositions;
    private Object constantValueToCheck;

    /**
     * Constructor to configure the rule on a given class and method.
     *
     * @param methodName           name of the method to check
     * @param methodOwnerType      name of the type that own the method
     * @param constantValueToCheck the current value to check
     * @param paramPositions       the position(s) of the argument on the method to check
     */
    protected ArgumentValueOnMethodCheck(String methodName, String methodOwnerType, Object constantValueToCheck, int... paramPositions) {
        super();
        this.methodsSpecs = new MethodSpecs[]{new MethodSpecs(methodName, methodOwnerType, constantValueToCheck, paramPositions)};
    }

    /**
     * Constructor to configure the rule on a given class and method.
     *
     * @param methodsSpecs array of methods specs to check.
     */
    protected ArgumentValueOnMethodCheck(MethodSpecs[] methodsSpecs) {
        super();
        this.methodsSpecs = methodsSpecs;
    }

    /**
     * Message to report on the issue.
     *
     * @return Message to report on the issue.
     */
    public abstract String getMessage();

    /**
     * Way to check the constant
     *
     * @param optionalConstantValue  the argument value of the method as an optional value
     * @param reportTree             the tree where the issue will be reported
     * @param constantValueToCheckss the value to use to check the argument
     */
    protected abstract void checkConstantValue(Optional<Object> optionalConstantValue, Tree reportTree, Object constantValueToCheck);

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        for (MethodSpecs currentMethodSpecs : methodsSpecs) {
            MethodMatchers matcher = MethodMatchers.create().ofTypes(currentMethodSpecs.getMethodOwner()).names(currentMethodSpecs.getMethodName()).withAnyParameters().build();
            if (matcher.matches(mit)) {
                paramsPositions = currentMethodSpecs.getParamsPositions();
                constantValueToCheck = currentMethodSpecs.getConstantValueToCheck();
                checkFlagCallOnMethod(mit);
            }
        }
    }

    private void checkFlagCallOnMethod(MethodInvocationTree mit) {
        for (int paramPosition : paramsPositions) {
            handleArgument(mit.arguments().get(paramPosition));
        }
    }

    private void handleArgument(ExpressionTree argument) {
        if (argument.is(Tree.Kind.IDENTIFIER)) {
            IdentifierTree expressionTree = (IdentifierTree) argument;
            if (expressionTree.symbolType().isSubtypeOf("java.lang.String")
                    || expressionTree.symbolType().isSubtypeOf("java.lang.Boolean")
                    || expressionTree.symbolType().isPrimitive()) {
                checkConstantValue(expressionTree.asConstant(), expressionTree, constantValueToCheck);
            }
        } else if (argument.is(Tree.Kind.ARRAY_TYPE)
                || argument.is(Tree.Kind.CHAR_LITERAL)
                || argument.is(Tree.Kind.STRING_LITERAL)
                || argument.is(Tree.Kind.INT_LITERAL)
                || argument.is(Tree.Kind.LONG_LITERAL)
                || argument.is(Tree.Kind.FLOAT_LITERAL)
                || argument.is(Tree.Kind.DOUBLE_LITERAL)
                || argument.is(Tree.Kind.BOOLEAN_LITERAL)) {
            checkConstantValue(argument.asConstant(), argument, constantValueToCheck);
        } else {
            ExpressionTree returnedArgument = (ExpressionTree) CheckArgumentComplexType.getChildExpression(argument);
            if (returnedArgument != argument) {
                handleArgument(returnedArgument);
            }
        }
    }
}
