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

import com.google.common.collect.ImmutableList;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>Inspect new class nodes to find a given class constructor call</li>
 * <li>Inspect method invocation nodes to find a given method call on the same class type</li>
 * <li>When leaving the file, checks if at least one method call was detected (for the given method)</li>
 * <li>If not, throw an issue on each new class node.</li>
 * </ul>
 */
public abstract class ConstructorBeforeMethodCheck extends IssuableSubscriptionVisitor {
    private final String ownerType;
    private boolean hasSeenMethod;
    private final List<Tree> constructorTreeList = new ArrayList<>();
    private final MethodMatchers releaseMatcher;

    /**
     * Message to report on the issue.
     *
     * @return Message to report on the issue.
     */
    public abstract String getMessage();

    protected ConstructorBeforeMethodCheck(String ownerType, String methodName) {
        this.ownerType = ownerType;
        hasSeenMethod = false;
        this.releaseMatcher = MethodMatchers.create().ofTypes(ownerType).names(methodName).withAnyParameters().build();
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.NEW_CLASS, Tree.Kind.METHOD_INVOCATION);
    }


    @Override
    public void leaveFile(JavaFileScannerContext context) {
        if (!hasSeenMethod) {
            for (Tree issueTree : constructorTreeList) {
                reportIssue(issueTree, getMessage());
            }
        }
        initializeRule();
    }

    private void initializeRule() {
        hasSeenMethod = false;
        constructorTreeList.clear();
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.NEW_CLASS)) {
            NewClassTree newClasstree = (NewClassTree) tree;
            if (newClasstree.symbolType().fullyQualifiedName().equals(ownerType)) {
                constructorTreeList.add(tree);
            }
        } else if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            if (releaseMatcher.matches(mit)) {
                hasSeenMethod = true;
            }

        }
    }
}
