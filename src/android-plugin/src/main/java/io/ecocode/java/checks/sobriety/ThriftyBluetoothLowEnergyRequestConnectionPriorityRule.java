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

import io.ecocode.java.checks.helpers.constant.ConstantOnMethodCheck;
import org.sonar.check.Rule;

/**
 * On the method `android.bluetooth.BluetoothGatt#requestConnectionPriority(int)`, reports an issue if
 * it is called with a parameter value different from `CONNECTION_PRIORITY_LOW_POWER (2)`.
 */
@Rule(key = "ESOB008", name = "ecoCodeThriftyBluetoothLowEnergyRequestConnectionPriority")
public class ThriftyBluetoothLowEnergyRequestConnectionPriorityRule extends ConstantOnMethodCheck {

    private static final int CONNECTION_PRIORITY_LOW_POWER = 2;

    public ThriftyBluetoothLowEnergyRequestConnectionPriorityRule() {
        super("requestConnectionPriority", "android.bluetooth.BluetoothGatt", CONNECTION_PRIORITY_LOW_POWER, 0);
    }

    @Override
    public String getMessage() {
        return "Invoking BluetoothGatt.requestConnectionPriority(CONNECTION_PRIORITY_LOW_POWER) is recommend to reduce power consumption.";
    }
}
