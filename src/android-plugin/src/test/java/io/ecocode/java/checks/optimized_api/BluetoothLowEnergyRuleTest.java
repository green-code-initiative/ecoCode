package io.ecocode.java.checks.optimized_api;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class BluetoothLowEnergyRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/optimized_api/BluetoothLowEnergyCheckBothBleBc.java")
                .withCheck(new BluetoothLowEnergyRule())
                .verifyIssues();

        JavaCheckVerifier.newVerifier().onFile("src/test/files/optimized_api/BluetoothLowEnergyCheckOnlyBc.java")
                .withCheck(new BluetoothLowEnergyRule())
                .verifyIssues();

        JavaCheckVerifier.newVerifier().onFile("src/test/files/optimized_api/BluetoothLowEnergyCheckOnlyBle.java")
                .withCheck(new BluetoothLowEnergyRule())
                .verifyIssues();

        JavaCheckVerifier.newVerifier().onFile("src/test/files/optimized_api/BluetoothLowEnergyCheckWildcard.java")
                .withCheck(new BluetoothLowEnergyRule())
                .verifyIssues();
    }
}
