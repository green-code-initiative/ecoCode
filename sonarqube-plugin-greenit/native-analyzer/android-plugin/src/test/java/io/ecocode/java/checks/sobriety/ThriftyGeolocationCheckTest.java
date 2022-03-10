package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ThriftyGeolocationCheckTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyGeolocationCheck.java")
                .withChecks(new ThriftyGeolocationMinTimeRule(), new ThriftyGeolocationMinDistanceRule())
                .verifyIssues();
    }
}
