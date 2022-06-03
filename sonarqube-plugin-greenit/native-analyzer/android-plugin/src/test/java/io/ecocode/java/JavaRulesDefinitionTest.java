/*
 * SonarQube Java Custom Rules Example
 * Copyright (C) 2016-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
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

import org.junit.Test;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction.Type;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Param;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;

import static org.fest.assertions.Assertions.assertThat;

public class JavaRulesDefinitionTest {

  @Test
  public void test() {
    JavaRulesDefinition rulesDefinition = new JavaRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository(Java.REPOSITORY_KEY);

    assertThat(repository.name()).isEqualTo(Java.REPOSITORY_NAME);
    assertThat(repository.language()).isEqualTo(Java.KEY);
    assertThat(repository.rules()).hasSize(JavaCheckList.getChecks().size());

    assertRuleProperties(repository);
    assertAllRuleParametersHaveDescription(repository);
  }

  private void assertRuleProperties(Repository repository) {
    Rule rule = repository.rule("EOPT001");
    assertThat(rule).isNotNull();
    assertThat(rule.name()).isEqualTo("Optimized API: Fused Location");
    assertThat(rule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(rule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule bleRule = repository.rule("EOPT002");
    assertThat(bleRule).isNotNull();
    assertThat(bleRule.name()).isEqualTo("Optimized API: Bluetooth Low-Energy");
    assertThat(bleRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(bleRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule keepScreenOnAddFlagRule = repository.rule("EIDL001");
    assertThat(keepScreenOnAddFlagRule).isNotNull();
    assertThat(keepScreenOnAddFlagRule.name()).isEqualTo("Idleness: Keep Screen On (addFlags)");
    assertThat(keepScreenOnAddFlagRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(keepScreenOnAddFlagRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule keepScreenOnSetFlagRule = repository.rule("EIDL002");
    assertThat(keepScreenOnSetFlagRule).isNotNull();
    assertThat(keepScreenOnSetFlagRule.name()).isEqualTo("Idleness: Keep Screen On (setFlags)");
    assertThat(keepScreenOnSetFlagRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(keepScreenOnSetFlagRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule brightnessRule = repository.rule("ESOB002");
    assertThat(brightnessRule).isNotNull();
    assertThat(brightnessRule.name()).isEqualTo("Sobriety: Brightness Override");
    assertThat(brightnessRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(brightnessRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule internetInTheLoopRule = repository.rule("EBOT001");
    assertThat(internetInTheLoopRule).isNotNull();
    assertThat(internetInTheLoopRule.name()).isEqualTo("Bottleneck: Internet In The Loop");
    assertThat(internetInTheLoopRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(internetInTheLoopRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule keepCpuOnRule = repository.rule("EIDL004");
    assertThat(keepCpuOnRule).isNotNull();
    assertThat(keepCpuOnRule.name()).isEqualTo("Idleness: Keep Cpu On");
    assertThat(keepCpuOnRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(keepCpuOnRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule thriftyMotionSensorRule = repository.rule("ESOB001");
    assertThat(thriftyMotionSensorRule).isNotNull();
    assertThat(thriftyMotionSensorRule.name()).isEqualTo("Sobriety: Thrifty Motion Sensor");
    assertThat(thriftyMotionSensorRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(thriftyMotionSensorRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule wifiMulticastLockRule = repository.rule("EBOT002");
    assertThat(wifiMulticastLockRule).isNotNull();
    assertThat(wifiMulticastLockRule.name()).isEqualTo("Bottleneck: Wifi Multicast Lock");
    assertThat(wifiMulticastLockRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(wifiMulticastLockRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule cameraLeakRule = repository.rule("ELEA002");
    assertThat(cameraLeakRule).isNotNull();
    assertThat(cameraLeakRule.name()).isEqualTo("Leakage: Camera Leak");
    assertThat(cameraLeakRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(cameraLeakRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule locationLeakRule = repository.rule("ELEA003");
    assertThat(locationLeakRule).isNotNull();
    assertThat(locationLeakRule.name()).isEqualTo("Leakage: Location Leak");
    assertThat(locationLeakRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(locationLeakRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule sensorManagerLeakRule = repository.rule("ELEA004");
    assertThat(sensorManagerLeakRule).isNotNull();
    assertThat(sensorManagerLeakRule.name()).isEqualTo("Leakage: SensorManager Leak");
    assertThat(sensorManagerLeakRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(sensorManagerLeakRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule mediaLeakMediaRecorderLeakRule = repository.rule("ELEA005");
    assertThat(mediaLeakMediaRecorderLeakRule).isNotNull();
    assertThat(mediaLeakMediaRecorderLeakRule.name()).isEqualTo("Leakage: Media Leak (MediaRecorder)");
    assertThat(mediaLeakMediaRecorderLeakRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(mediaLeakMediaRecorderLeakRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule mediaLeakMediaPlayerLeakRule = repository.rule("ELEA006");
    assertThat(mediaLeakMediaPlayerLeakRule).isNotNull();
    assertThat(mediaLeakMediaPlayerLeakRule.name()).isEqualTo("Leakage: Media Leak (MediaPlayer)");
    assertThat(mediaLeakMediaPlayerLeakRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(mediaLeakMediaPlayerLeakRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule durableWakeLockRule = repository.rule("EIDL006");
    assertThat(durableWakeLockRule).isNotNull();
    assertThat(durableWakeLockRule.name()).isEqualTo("Idleness: Durable Wake Lock");
    assertThat(durableWakeLockRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(durableWakeLockRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule rigidAlarmSetRepeatingRule = repository.rule("EIDL007");
    assertThat(rigidAlarmSetRepeatingRule).isNotNull();
    assertThat(rigidAlarmSetRepeatingRule.name()).isEqualTo("Idleness: Rigid Alarm");
    assertThat(rigidAlarmSetRepeatingRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(rigidAlarmSetRepeatingRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule continuousRenderingRule = repository.rule("EIDL008");
    assertThat(continuousRenderingRule).isNotNull();
    assertThat(continuousRenderingRule.name()).isEqualTo("Idleness: Continuous Rendering");
    assertThat(continuousRenderingRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(continuousRenderingRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule keepVoiceAwakeRule = repository.rule("EIDL009");
    assertThat(keepVoiceAwakeRule).isNotNull();
    assertThat(keepVoiceAwakeRule.name()).isEqualTo("Idleness: Keep Voice Awake");
    assertThat(keepVoiceAwakeRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(keepVoiceAwakeRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule thriftyGeolocationMinTimeRule = repository.rule("ESOB005");
    assertThat(thriftyGeolocationMinTimeRule).isNotNull();
    assertThat(thriftyGeolocationMinTimeRule.name()).isEqualTo("Sobriety: Thrifty Geolocation (minTime)");
    assertThat(thriftyGeolocationMinTimeRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(thriftyGeolocationMinTimeRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule thriftyBluetoothLowEnergySetAdvertiseMode = repository.rule("ESOB007");
    assertThat(thriftyBluetoothLowEnergySetAdvertiseMode).isNotNull();
    assertThat(thriftyBluetoothLowEnergySetAdvertiseMode.name()).isEqualTo("Sobriety: Thrifty Bluetooth Low Energy (setAdvertiseMode)");
    assertThat(thriftyBluetoothLowEnergySetAdvertiseMode.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(thriftyBluetoothLowEnergySetAdvertiseMode.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule thriftyBluetoothLowEnergyRequestConnectionPriority = repository.rule("ESOB008");
    assertThat(thriftyBluetoothLowEnergyRequestConnectionPriority).isNotNull();
    assertThat(thriftyBluetoothLowEnergyRequestConnectionPriority.name()).isEqualTo("Sobriety: Thrifty Bluetooth Low Energy (requestConnectionPriority)");
    assertThat(thriftyBluetoothLowEnergyRequestConnectionPriority.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(thriftyBluetoothLowEnergyRequestConnectionPriority.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule thriftyGeolocationMinDistanceRule = repository.rule("ESOB010");
    assertThat(thriftyGeolocationMinDistanceRule).isNotNull();
    assertThat(thriftyGeolocationMinDistanceRule.name()).isEqualTo("Sobriety: Thrifty Geolocation (minDistance)");
    assertThat(thriftyGeolocationMinDistanceRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(thriftyGeolocationMinDistanceRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule vibrationFreeRule = repository.rule("ESOB011");
    assertThat(vibrationFreeRule).isNotNull();
    assertThat(vibrationFreeRule.name()).isEqualTo("Sobriety: Vibration Free");
    assertThat(vibrationFreeRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(vibrationFreeRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule thriftyNotification = repository.rule("ESOB012");
    assertThat(thriftyNotification).isNotNull();
    assertThat(thriftyNotification.name()).isEqualTo("Sobriety: Thrifty Notification");
    assertThat(thriftyNotification.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(thriftyNotification.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule torchFreeRule = repository.rule("ESOB013");
    assertThat(torchFreeRule).isNotNull();
    assertThat(torchFreeRule.name()).isEqualTo("Sobriety: Torch Free");
    assertThat(torchFreeRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(torchFreeRule.type()).isEqualTo(RuleType.CODE_SMELL);

    Rule chargeAwarenessRule = repository.rule("EPOW004");
    assertThat(chargeAwarenessRule).isNotNull();
    assertThat(chargeAwarenessRule.name()).isEqualTo("Power: Charge Awareness");
    assertThat(chargeAwarenessRule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(chargeAwarenessRule.type()).isEqualTo(RuleType.CODE_SMELL);
  }

  private void assertAllRuleParametersHaveDescription(Repository repository) {
    for (Rule rule : repository.rules()) {
      for (Param param : rule.params()) {
        assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
      }
    }
  }

}
