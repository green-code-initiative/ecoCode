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
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * Checks the call of "set", "setAlarmClock", "setExact", "setInexactRepeating", "setRepeating" & "setWindow" from "android.app.AlarmManager".
 * Checks also the call of "onPerformSync" & "getSyncAdapterBinder" from "android.content.AbstractThreadedSyncAdapter".
 */
@Rule(key = "EBAT003", name = "ecocodeJobCoalesce")
public class JobCoalesceRule extends IssuableSubscriptionVisitor {
	
	private static final String ALARM_MANAGER_CLASS = "android.app.AlarmManager";

    private final MethodMatchers alarmSchedulerMethodMatcher = MethodMatchers.or(
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("set").withAnyParameters().build(),
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("setAlarmClock").withAnyParameters().build(),
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("setAndAllowWhileIdle").withAnyParameters().build(),
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("setExact").withAnyParameters().build(),
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("setExactAndAllowWhileIdle").withAnyParameters().build(),
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("setInexactRepeating").withAnyParameters().build(),
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("setRepeating").withAnyParameters().build(),
            MethodMatchers.create().ofTypes(ALARM_MANAGER_CLASS).names("setWindow").withAnyParameters().build(),
            //ofAnyType is used because the method is forced to be overridden in a new class expending the abstract class AbstractThreadedSyncAdapter
            MethodMatchers.create().ofAnyType().names("onPerformSync").withAnyParameters().build(),
            //ofSubTypes is used because the method is from an abstract class
            MethodMatchers.create().ofSubTypes("android.content.AbstractThreadedSyncAdapter").names("getSyncAdapterBinder").withAnyParameters().build()
    );

    public JobCoalesceRule() {
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
            if (alarmSchedulerMethodMatcher.matches(mit)) {
                reportIssue(mit, "Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.");
            }
            /* TODO: 21/02/2022 to upgrade this part, another method matcher could be used to search the fact the AbstractThreadedSyncAdapter class is implemented
            to avoid using an ofAnyType() methodMatcher (line 49) reducing the risk of a reported issue where there should not be one
             */
        }
    }
}
