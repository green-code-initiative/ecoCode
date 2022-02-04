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
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * Check the call of the method "acquire" of "android.os.PowerManager$WakeLock".
 * Reports an issue if found without any parameters.
 */
@Rule(key = "EIDL006", name = "ecocodeDurableWakeLock")
public class DurableWakeLockRule extends IssuableSubscriptionVisitor {
    private String methodOwnerType = "android.os.PowerManager$WakeLock";
    private String methodName = "acquire";
    private static final String ERROR_MESSAGE = "Prefer setting a timeout when acquiring a wake lock to avoid running down the device's battery excessively.";
    private MethodMatchers matcher = MethodMatchers.create().ofTypes(methodOwnerType).names(methodName).addWithoutParametersMatcher().build();

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        if (matcher.matches(mit)) {
            reportIssue(mit, ERROR_MESSAGE);
        }
    }
}
