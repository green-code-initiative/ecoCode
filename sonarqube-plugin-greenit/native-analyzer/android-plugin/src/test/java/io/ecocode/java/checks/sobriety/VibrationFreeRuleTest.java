package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class VibrationFreeRuleTest {

    @Test
    public void verify() {

        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/VibrationFreeCheck.java")
                .withChecks(new VibrationFreeRule())
                .verifyIssues();
    }

}
