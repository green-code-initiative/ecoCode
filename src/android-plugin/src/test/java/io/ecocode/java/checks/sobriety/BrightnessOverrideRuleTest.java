package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class BrightnessOverrideRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/BrightnessOverrideCheck.java")
                .withCheck(new BrightnessOverrideRule())
                .verifyIssues();
    }
}
