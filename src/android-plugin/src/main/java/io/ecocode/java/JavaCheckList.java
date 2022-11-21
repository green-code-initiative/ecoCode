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
package io.ecocode.java;

import io.ecocode.java.checks.batch.JobCoalesceRule;
import io.ecocode.java.checks.batch.SensorCoalesceRule;
import io.ecocode.java.checks.bottleneck.InternetInTheLoopRule;
import io.ecocode.java.checks.bottleneck.UncompressedDataTransmissionRule;
import io.ecocode.java.checks.bottleneck.WifiMulticastLockRule;
import io.ecocode.java.checks.idleness.*;
import io.ecocode.java.checks.leakage.*;
import io.ecocode.java.checks.optimized_api.BluetoothLowEnergyRule;
import io.ecocode.java.checks.optimized_api.FusedLocationRule;
import io.ecocode.java.checks.power.SaveModeAwarenessRule;
import io.ecocode.java.checks.power.ChargeAwarenessRule;
import io.ecocode.java.checks.sobriety.*;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class JavaCheckList {

    private JavaCheckList() {
    }

    public static List<Class<? extends JavaCheck>> getChecks() {
        List<Class<? extends JavaCheck>> checks = new ArrayList<>();
        checks.addAll(getJavaChecks());
        checks.addAll(getJavaTestChecks());
        return Collections.unmodifiableList(checks);
    }

    public static List<Class<? extends JavaCheck>> getJavaChecks() {
        return Collections.unmodifiableList(Arrays.asList(
                FusedLocationRule.class,
                BluetoothLowEnergyRule.class,
                KeepScreenOnAddFlagsRule.class,
                KeepScreenOnSetFlagsRule.class,
                BrightnessOverrideRule.class,
                InternetInTheLoopRule.class,
                KeepCpuOnRule.class,
                ThriftyMotionSensorRule.class,
                WifiMulticastLockRule.class,
                LocationLeakRule.class,
                CameraLeakRule.class,
                SensorManagerLeakRule.class,
                DurableWakeLockRule.class,
                MediaLeakMediaRecorderRule.class,
                MediaLeakMediaPlayerRule.class,
                RigidAlarmRule.class,
                ContinuousRenderingRule.class,
                KeepVoiceAwakeRule.class,
                ThriftyBluetoothLowEnergySetAdvertiseModeRule.class,
                ThriftyBluetoothLowEnergyRequestConnectionPriorityRule.class,
                ThriftyGeolocationMinTimeRule.class,
                ThriftyGeolocationMinDistanceRule.class,
                ChargeAwarenessRule.class,
                VibrationFreeRule.class,
                TorchFreeRule.class,
                ThriftyNotificationRule.class,
                UncompressedDataTransmissionRule.class,
                SensorCoalesceRule.class,
                JobCoalesceRule.class,
                SaveModeAwarenessRule.class,
                ThriftyGeolocationCriteriaRule.class,
                HighFrameRateRule.class
        ));
    }

    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return Collections.unmodifiableList(Arrays.asList(
        ));
    }
}
