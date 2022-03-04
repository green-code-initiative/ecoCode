package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class VibrationFreeRuleTest {

    @Test
    public void verify() {

        JavaCheckVerifier.newVerifier().onFiles("src/test/files/sobriety/VibrationFreeCheckContext.java", "src/test/files/sobriety/VibrationFreeCheckActivity.java")
                .withChecks(new VibrationFreeRule(), new VibrationFreeRule())
                .verifyIssues();
    }
}
