package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ThriftyBluetoothLowEnergySetAdvertiseModeRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyBluetoothLowEnergySetAdvertiseModeCheck.java")
                .withCheck(new ThriftyBluetoothLowEnergySetAdvertiseModeRule())
                .verifyIssues();
    }
}
