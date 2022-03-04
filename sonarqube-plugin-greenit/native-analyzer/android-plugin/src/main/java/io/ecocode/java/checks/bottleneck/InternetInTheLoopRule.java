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
package io.ecocode.java.checks.bottleneck;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * Check if the method openConnection of the Url class is called inside a loop.
 * Not thrown if openConnection() call is deported into an other method.
 */
@Rule(key = "EBOT001", name = "ecocodeInternetInTheLoop")
public class InternetInTheLoopRule extends IssuableSubscriptionVisitor {

    private static final String ERROR_MESSAGE = "Internet connection should not be opened in loops to preserve the battery.";
    private static final String METHOD_NAME = "openConnection";
    private static final String METHOD_OWNER_TYPE = "java.net.URL";
    private static final MethodMatchers METHOD_MATCHER = MethodMatchers.create().ofTypes(METHOD_OWNER_TYPE).names(METHOD_NAME).withAnyParameters().build();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        if (METHOD_MATCHER.matches(mit)) {
            reportIssueIfInLoop(mit, mit);
        }
    }

    private void reportIssueIfInLoop(Tree treeToCheck, Tree issueTree) {
        if (treeToCheck.is(Tree.Kind.FOR_STATEMENT)
                || treeToCheck.is(Tree.Kind.WHILE_STATEMENT)
                || treeToCheck.is(Tree.Kind.DO_STATEMENT)
                || treeToCheck.is(Tree.Kind.FOR_EACH_STATEMENT)) {
            // Tree is in a loop, report issue
            reportIssue(issueTree, ERROR_MESSAGE);
        } else if (treeToCheck.is(Tree.Kind.COMPILATION_UNIT)) {
            // Top of the tree, exit
        } else {
            // parent is not a loop, continue on next parent
            reportIssueIfInLoop(treeToCheck.parent(), issueTree);
        }
    }
}