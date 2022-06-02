package io.ecocode.java.checks.power;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ChargeAwarenessRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/power/ChargeAwarenessCheck.java")
                .withCheck(new ChargeAwarenessRule())
                .verifyIssues();
    }
}
