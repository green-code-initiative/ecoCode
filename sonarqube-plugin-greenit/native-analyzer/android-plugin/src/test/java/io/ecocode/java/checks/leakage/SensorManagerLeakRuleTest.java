package io.ecocode.java.checks.leakage;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class SensorManagerLeakRuleTest {

    @Test
    public void SensorManagerOnlyRegister() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/SensorManagerLeakCheckIssue.java")
                .withCheck(new SensorManagerLeakRule())
                .verifyNoIssues();
    }

    @Test
    public void SensorManagerRegisterAndUnregister() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/SensorManagerLeakCheckNoIssue.java")
                .withCheck(new SensorManagerLeakRule())
                .verifyNoIssues();
    }
}
