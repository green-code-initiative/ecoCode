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
package io.ecocode.java.checks.idleness;

import com.google.common.collect.ImmutableList;
import io.ecocode.java.checks.helpers.CheckArgumentComplexTypeUtils;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Check New Class Node and Method Invocation Node.
 * If a new instance of android.service.voice.VoiceInteractionSession is declared.
 * Check whether or not the android.service.voice.VoiceInteractionSession#setKeepAwake() method is called.
 * If the argument 0 of the method is false, do not report an issue.
 * If the argument 0 of the method is true, report the method node.
 * Otherwise report an issue on the new class nodes.
 */
@Rule(key = "EIDL009", name = "ecocodeKeepVoiceAwake")
public class KeepVoiceAwakeRule extends IssuableSubscriptionVisitor {

    private static final Logger LOG = Loggers.get(KeepVoiceAwakeRule.class);

    private static final String OWNER_TYPE = "android.service.voice.VoiceInteractionSession";
    private static final String METHOD_NAME = "setKeepAwake";
    private static final String ERROR_MESSAGE = "VoiceInteractionSession.setKeepAwake(false) should be called to limit battery drain.";
    private final MethodMatchers methodMatcher = MethodMatchers.create().ofTypes(OWNER_TYPE).names(METHOD_NAME).addParametersMatcher("boolean").build();
    private boolean hasSeenMethod;
    private boolean hasSeenMethodTrue = false;
    private final List<Tree> constructorTreeList = new ArrayList<>();
    private final List<Tree> setKeepAwakeTrueTreeList = new ArrayList<>();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.NEW_CLASS, Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void leaveFile(JavaFileScannerContext context) {
        if (hasSeenMethodTrue) {
            // End of file, report each setKeepAwake(true)
            for (Tree tree : setKeepAwakeTrueTreeList) {
                reportIssue(tree, ERROR_MESSAGE);
            }
        } else if (!hasSeenMethod) {
            // End of file, setKeepAwake(...) not called, report an issue on new VoiceInteractionSession()
            for (Tree issueTree : constructorTreeList) {
                reportIssue(issueTree, ERROR_MESSAGE);
            }
        }
        initializeRule();
    }

    private void initializeRule() {
        hasSeenMethod = false;
        hasSeenMethodTrue = false;
        constructorTreeList.clear();
        setKeepAwakeTrueTreeList.clear();
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.NEW_CLASS)) {
            NewClassTree newClasstree = (NewClassTree) tree;
            if (newClasstree.symbolType().fullyQualifiedName().equals(OWNER_TYPE)) {
                constructorTreeList.add(tree);
            }
        } else if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            if (!mit.arguments().isEmpty() && methodMatcher.matches(mit)) {
                // Get first argument of VoiceInteractionSession.setKeepAwake(Boolean)
                handleArgument(mit.arguments().get(0));
            }
        }
    }

    private void checkArgumentIsTrue(ExpressionTree argument, Optional<Object> optionalConstantValue) {
        try {
            if (optionalConstantValue.isPresent() && !((boolean) optionalConstantValue.get())) {
                // setKeepAwake(false)
                hasSeenMethod = true;
            } else if (optionalConstantValue.isPresent() && ((boolean) optionalConstantValue.get())) {
                // setKeepAwake(true)
                hasSeenMethodTrue = true;
                setKeepAwakeTrueTreeList.add(argument);
            }
        } catch (Exception e) {
            LOG.debug(String.format("[%s] Cannot evaluate setKeepAwake(Boolean) argument value.", getClass().getName()));
            LOG.error("Exception:", e);
        }
    }

    private void handleArgument(ExpressionTree argument) {
        if (argument.is(Tree.Kind.IDENTIFIER)) {
            IdentifierTree expressionTree = (IdentifierTree) argument;
            if (expressionTree.symbolType().fullyQualifiedName().equals("boolean")) {
                checkArgumentIsTrue(argument, expressionTree.asConstant());
            }
        } else if (argument.is(Tree.Kind.BOOLEAN_LITERAL)) {
            checkArgumentIsTrue(argument, argument.asConstant());
        } else {
            ExpressionTree returnedArgument = (ExpressionTree) CheckArgumentComplexTypeUtils.getChildExpression(argument);
            if (returnedArgument != argument) {
                handleArgument(returnedArgument);
            }
        }
    }
}
