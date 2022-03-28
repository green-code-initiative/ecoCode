package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ThriftyBluetoothLowEnergyRequestConnectionPriorityRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyBluetoothLowEnergyRequestConnectionPriorityCheck.java")
                .withCheck(new ThriftyBluetoothLowEnergyRequestConnectionPriorityRule())
                .verifyIssues();
    }
}
