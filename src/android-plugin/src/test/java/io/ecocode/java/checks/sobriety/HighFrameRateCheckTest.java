package org.sonar.samples.java.checks;

import org.junit.jupiter.api.Test;

class HighFrameRateCheckTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/HighFrameRateRule.java")
                .withChecks(new HighFrameRateRule())
                .verifyIssues();
    }

}