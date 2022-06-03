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
import org.sonar.plugins.java.api.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks for 2 methods calls of the same file:
 * <ul>
 *     <li>The first one can be seen as the "opening" method (like a method to open a camera stream)</li>
 *     <li>The Second one can be seen as the "closing" method (like a method to close a camera stream)</li>
 * </ul>
 * If the "opening" method is called in a file without the call to the "closing" method, we raise an issue on
 * all the "opening" method call of the file.
 * If the "closing" method is called, we do not raise issues, even if several "opening" method are called.
 * <p>
 * This approach is far from perfect but cover a lot of the use cases.
 */
public abstract class OpeningClosingMethodCheck extends IssuableSubscriptionVisitor {

    private final MethodMatchers matcherOpeningMethod;
    private final MethodMatchers matcherClosingMethod;
    private boolean hasSeenClosingMethod = false;
    private final List<Tree> openingMethodTreeList = new ArrayList<>();

    protected OpeningClosingMethodCheck(String methodName, String secondMethodName, String methodOwnerType) {
        super();
        this.matcherOpeningMethod = MethodMatchers.create().ofTypes(methodOwnerType).names(methodName).withAnyParameters().build();
        this.matcherClosingMethod = MethodMatchers.create().ofTypes(methodOwnerType).names(secondMethodName).withAnyParameters().build();
    }

    /**
     * Message to report on the issue.
     *
     * @return Message to report on the issue.
     */
    public abstract String getMessage();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void leaveFile(JavaFileScannerContext context) {
        if (!hasSeenClosingMethod) {
            for (Tree issueTree :
                    openingMethodTreeList) {
                reportIssue(issueTree, getMessage());
            }
        }
        initializeRule();
    }

    private void initializeRule() {
        hasSeenClosingMethod = false;
        openingMethodTreeList.clear();
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        if (matcherOpeningMethod.matches(mit)) {
            openingMethodTreeList.add(mit);
        } else if (matcherClosingMethod.matches(mit)) {
            hasSeenClosingMethod = true;
        }
    }
}
