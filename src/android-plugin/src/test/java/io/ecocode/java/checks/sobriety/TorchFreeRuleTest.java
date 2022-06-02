package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class TorchFreeRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/TorchFreeCheck.java")
                .withCheck(new TorchFreeRule())
                .verifyIssues();
    }
}
