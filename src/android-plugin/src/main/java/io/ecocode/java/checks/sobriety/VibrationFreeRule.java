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

import io.ecocode.java.checks.helpers.constant.ArgumentValueOnMethodCheck;
import io.ecocode.java.checks.helpers.constant.MethodSpecs;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Optional;

@Rule(key = "ESOB011", name = "ecocodeVibrationFree")
public class VibrationFreeRule extends ArgumentValueOnMethodCheck {
    
    private static final String SYSTEM_SERVICE_METHOD = "getSystemService";

    public VibrationFreeRule() {
        super(new MethodSpecs[]{
                new MethodSpecs(SYSTEM_SERVICE_METHOD, "android.content.Context", "vibrator", 0),
                new MethodSpecs(SYSTEM_SERVICE_METHOD, "android.content.Context", "vibrator_manager", 0),
                new MethodSpecs(SYSTEM_SERVICE_METHOD, "android.app.Activity", "vibrator", 0),
                new MethodSpecs(SYSTEM_SERVICE_METHOD, "android.app.Activity", "vibrator_manager", 0)
        });
    }

    @Override
    public String getMessage() {
        return "Avoid using the device vibrator to use less energy.";
    }

    @Override
    protected void checkConstantValue(Optional<Object> optionalConstantValue, Tree reportTree, Object constantValueToCheck) {
        if (optionalConstantValue.isPresent() && optionalConstantValue.get().equals(constantValueToCheck)) {
            reportIssue(reportTree, getMessage());
        }
    }

}
