package io.ecocode.java.checks.bottleneck;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class UncachedDataReceptionRuleTest {

    @Test
    public void verifyUncachedDataReception() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/bottleneck/UncachedDataReceptionCheck.java")
                .withCheck(new UncachedDataReceptionRule())
                .verifyIssues();
    }
}
