package io.ecocode.java.checks.leakage;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class EverlastingServiceRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/EverlastingServiceCheck.java")
                .withChecks(new EverlastingServiceRule())
                .verifyIssues();
    }

}
