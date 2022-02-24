package io.ecocode.java.checks.power;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class SaveModeAwarenessRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/power/SaveModeAwarenessCheckTest.java")
                .withCheck(new SaveModeAwarenessRule())
                .verifyIssues();
    }
}