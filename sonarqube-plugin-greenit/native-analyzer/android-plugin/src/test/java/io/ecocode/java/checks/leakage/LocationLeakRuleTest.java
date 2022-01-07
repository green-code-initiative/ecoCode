package io.ecocode.java.checks.leakage;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class LocationLeakRuleTest {

    @Test
    public void LocationOnlyRegister() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/LocationLeakCheckIssue.java")
                .withCheck(new LocationLeakRule())
                .verifyIssues();
    }

    @Test
    public void LocationRegisterAndUnregister() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/LocationLealCheckNoIssue.java")
                .withCheck(new LocationLeakRule())
                .verifyNoIssues();
    }
}
