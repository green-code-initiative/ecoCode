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
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * Check the call of "setSound", "setVibrate" & "setVibrationPattern" of "android.app.NotificationChannel" & "android.app.Notification$Builder".
 */
@Rule(key = "ESOB012", name = "ecocodeThriftyNotification")
public class ThriftyNotificationRule extends IssuableSubscriptionVisitor {
    private static final String ERROR_MESSAGE = "Avoid using vibration or sound when notifying the users to use less energy.";
    private MethodMatchers[] methodMatchers = new MethodMatchers[]{
            MethodMatchers.create().ofTypes("android.app.NotificationChannel").names("setVibrationPattern").withAnyParameters().build(),
            MethodMatchers.create().ofTypes("android.app.NotificationChannel").names("setSound").withAnyParameters().build(),
            MethodMatchers.create().ofTypes("android.app.Notification$Builder").names("setVibrate").withAnyParameters().build(),
            MethodMatchers.create().ofTypes("android.app.Notification$Builder").names("setSound").withAnyParameters().build()
    };

    public ThriftyNotificationRule() {
        super();
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (!hasSemantic()) {
            return;
        }
        if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            Object[] args = mit.arguments().toArray();
            if (methodMatchers[0].matches(mit) || methodMatchers[2].matches(mit)) {
                if (((ExpressionTree) args[0]).kind() != Tree.Kind.NULL_LITERAL) {
                    reportIssue(mit, ERROR_MESSAGE);
                }
            }
            else if (methodMatchers[1].matches(mit) || methodMatchers[3].matches(mit)) {
                if (((ExpressionTree)args[0]).kind() != Tree.Kind.NULL_LITERAL) {
                    reportIssue(mit, ERROR_MESSAGE);
                }
                else if (args.length == 2){
                    if (((ExpressionTree) args[1]).kind() != Tree.Kind.NULL_LITERAL){
                        reportIssue(mit, ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
