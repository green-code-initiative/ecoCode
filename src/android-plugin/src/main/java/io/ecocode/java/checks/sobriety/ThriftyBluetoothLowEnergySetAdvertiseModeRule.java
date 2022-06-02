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
 * On the method `android.bluetooth.le.AdvertiseSettings$Builder#setAdvertiseMode(int)`, report an issue if
 * it is called with parameter value different from `ADVERTISE_MODE_LOW_POWER (0)`.
 */
@Rule(key = "ESOB007", name = "ecoCodeThriftyBluetoothLowEnergySetAdvertiseMode")
public class ThriftyBluetoothLowEnergySetAdvertiseModeRule extends ConstantOnMethodCheck {

    private static final int CONSTANT_POWER_LOW = 0;

    public ThriftyBluetoothLowEnergySetAdvertiseModeRule() {
        super("setAdvertiseMode", "android.bluetooth.le.AdvertiseSettings$Builder", CONSTANT_POWER_LOW, 0);
    }

    @Override
    public String getMessage() {
        return "You should call AdvertiseSettings.Builder.setAdvertiseMode(ADVERTISE_MODE_LOW_POWER) to optimize battery usage.";
    }
}
