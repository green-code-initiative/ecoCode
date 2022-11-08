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
package io.ecocode.java.checks.sobriety;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;
import java.util.Optional;

/**
 * Check the assignment of the variable android.view.WindowManager$LayoutParams.screenBrightness with the value
 * "BRIGHTNESS_OVERRIDE_FULL" (1.0f).
 */
@Rule(key = "ESOB002", name = "ecoCodeBrightness")
public class BrightnessOverrideRule extends IssuableSubscriptionVisitor {

    private static final String ERROR_MESSAGE = "Forcing brightness to max value may cause useless energy consumption.";
    private static final String EXPRESSION_FULL_QUALIFIED_NAME = "android.view.WindowManager$LayoutParams";
    private static final String MEMBER_NAME = "screenBrightness";
    private static final int BRIGHTNESS_FULL_VALUE = 1;

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.ASSIGNMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        AssignmentExpressionTree assignmentTree = (AssignmentExpressionTree) tree;
        if (isBrightnessAssignment(assignmentTree)) {
            Tree.Kind assignmentTreeKind = assignmentTree.expression().kind();
            switch (assignmentTreeKind) {
                case FLOAT_LITERAL:
                    LiteralTree floatLiteralTree = (LiteralTree) assignmentTree.expression();
                    checkBrightnessAssignmentExpressionValue(floatLiteralTree,
                            floatLiteralTree.symbolType().fullyQualifiedName(),
                            Float.valueOf(floatLiteralTree.value()));
                    break;
                case INT_LITERAL:
                    LiteralTree intLiteralTree = (LiteralTree) assignmentTree.expression();
                    checkBrightnessAssignmentExpressionValue(intLiteralTree,
                            intLiteralTree.symbolType().fullyQualifiedName(),
                            Integer.valueOf(intLiteralTree.value()));
                    break;
                case VARIABLE:
                    VariableTree variableTree = (VariableTree) assignmentTree.expression();
                    Optional<Object> variableTreeConstant = variableTree.initializer().asConstant();
                    if (variableTreeConstant.isPresent()) {
                        checkBrightnessAssignmentExpressionValue(variableTree,
                                variableTree.initializer().symbolType().fullyQualifiedName(),
                                (Number) variableTreeConstant.get());
                    }
                    break;
                case IDENTIFIER:
                    IdentifierTree identifierTree = (IdentifierTree) assignmentTree.expression();
                    Optional<Object> identifierTreeConstant = identifierTree.asConstant();
                    if (identifierTreeConstant.isPresent()) {
                        // Constant identifier only, other cases are ignored
                        checkBrightnessAssignmentExpressionValue(identifierTree,
                                identifierTree.symbolType().fullyQualifiedName(),
                                (Number) identifierTreeConstant.get());
                    }
                    break;
                default:
                    // For other tree types we cannot do anything, juste ignore them
                    break;
            }
        }
    }

    /**
     * Check that the assignment is an assignment of android.view.WindowManager$LayoutParams screenBrightness element
     *
     * @param assignmentExpressionTree the assignment expression to check
     * @return true if the expression is LayoutParams.screenBrightness assignment
     */
    private boolean isBrightnessAssignment(AssignmentExpressionTree assignmentExpressionTree) {
        if (assignmentExpressionTree.variable().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) assignmentExpressionTree.variable();
            return mset.expression().symbolType().fullyQualifiedName().equals(EXPRESSION_FULL_QUALIFIED_NAME)
                    && mset.identifier().name().equals(MEMBER_NAME);
        } else {
            return false;
        }
    }

    private void checkBrightnessAssignmentExpressionValue(Tree tree, String qualifiedType, Number value) {
        int intValue = value.intValue();
        float floatValue = value.floatValue();
        if (qualifiedType.equals("float") && floatValue == BRIGHTNESS_FULL_VALUE
                || qualifiedType.equals("int") && intValue == BRIGHTNESS_FULL_VALUE) {
            reportIssue(tree, ERROR_MESSAGE);
        }
    }
}
