package org.sonar.samples.java.checks;

import io.ecocode.java.checks.sobriety.HighFrameRateRule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class HighFrameRateCheckTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/HighFrameRateRule.java")
                .withChecks(new HighFrameRateRule())
                .verifyIssues();
    }

}