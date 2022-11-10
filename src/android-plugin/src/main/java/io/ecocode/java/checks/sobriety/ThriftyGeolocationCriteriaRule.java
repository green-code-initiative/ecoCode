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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.collect.ImmutableList;

/**
 * - Visit the method invocation nodes.
 * - check if method name matches `requestLocationUpdates`
 * - Check if the method kind is `android.location.LocationManager`
 * <p>
 * * **Case 1**:
 * - if the method `requestLocationUpdates` is called :
 * - if the method `getBestProvider` of type `android.location.LocationManager` is not called report an issue on `requestLocationUpdates`.
 * * **Case 2**:
 * - if the method `requestLocationUpdates` is called :
 * - if the method `getBestProvider` is called :
 * - if the method `setPowerRequirement` of type `android.location.Criteria` is not called report an issue on `getBestProvider`.
 * <p>
 * * **Case 3**:
 * - if the method `setPowerRequirement` of type `android.location.Criteria` is called report an issue on its first argument if it's not `1`.
 */
@Rule(key = "ESOB006", name = "ecocodeThriftyGeolocationCriteriaRule")
public class ThriftyGeolocationCriteriaRule extends IssuableSubscriptionVisitor {

    // CASE 1
    private static final String REPORT_MESSAGE_REQUEST_LOCATION = "You should configure a location provider (LocationManager.getBestProvider(...)) to optimize battery usage.";
    // CASE 2
    private static final String REPORT_MESSAGE_BEST_PROVIDER = "You should call Criteria.setPowerRequirement(POWER_LOW) to optimize battery usage.";
    // CASE 3
    private static final String REPORT_MESSAGE_SET_POWER_REQUIREMENT = "You should set the power requirement to POWER_LOW to optimize battery usage.";
    
    private final MethodMatchers matcherCriteria = MethodMatchers.create().ofTypes("android.location.Criteria").names("setPowerRequirement").withAnyParameters().build();
    private final MethodMatchers matcherBestProvider = MethodMatchers.create().ofTypes("android.location.LocationManager").names("getBestProvider").withAnyParameters().build();
    private final MethodMatchers matcherRequestLocation = MethodMatchers.create().ofTypes("android.location.LocationManager").names("requestLocationUpdates").withAnyParameters().build();
    private final ArrayList<Tree> requestTreesToReport = new ArrayList<>();
    private final ArrayList<Tree> bestProviderTreeToReport = new ArrayList<>();
    private boolean hasSeenRequestLocation = false;
    private boolean hasSeenSetBestProvider = false;
    private boolean hasSeenSetPowerRequirement = false;

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void leaveFile(JavaFileScannerContext context) {
        if (!requestTreesToReport.isEmpty()
                && hasSeenRequestLocation) {
            if (!hasSeenSetBestProvider) {
                for (Tree tree : requestTreesToReport) {
                    reportIssue(tree, REPORT_MESSAGE_REQUEST_LOCATION);
                }
            } else {
                if (!hasSeenSetPowerRequirement) {
                    for (Tree tree : bestProviderTreeToReport) {
                        reportIssue(tree, REPORT_MESSAGE_BEST_PROVIDER);
                    }
                }
            }

        }
        requestTreesToReport.clear();
        bestProviderTreeToReport.clear();
        hasSeenRequestLocation = false;
        hasSeenSetBestProvider = false;
        hasSeenSetPowerRequirement = false;
    }

    @Override
    public void visitNode(Tree tree) {
        super.visitNode(tree);
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        if (matcherRequestLocation.matches(mit)) {
            requestTreesToReport.add(tree);
            hasSeenRequestLocation = true;
        }
        if (matcherBestProvider.matches(mit)) {
            bestProviderTreeToReport.add(mit);
            hasSeenSetBestProvider = true;
        }
        if (matcherCriteria.matches(mit)) {
            hasSeenSetPowerRequirement = true;
            if (!mit.arguments().isEmpty()) {
                ExpressionTree arg = mit.arguments().get(0);
                Optional<Object> optionalArg = arg.asConstant();
                try {
                    if (optionalArg.isPresent() && ((Integer) optionalArg.get()) == 1) {
                        hasSeenSetPowerRequirement = true;
                    } else {
                        reportIssue(mit, REPORT_MESSAGE_SET_POWER_REQUIREMENT);
                    }
                } catch (Exception e) {
                    reportIssue(mit, REPORT_MESSAGE_SET_POWER_REQUIREMENT);
                }
            } else {
                reportIssue(mit, REPORT_MESSAGE_SET_POWER_REQUIREMENT);
            }
        }
    }
}
