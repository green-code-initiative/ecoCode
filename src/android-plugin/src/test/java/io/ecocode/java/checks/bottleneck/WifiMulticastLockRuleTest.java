package io.ecocode.java.checks.bottleneck;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class WifiMulticastLockRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/bottleneck/WifiMulticastLockCheck.java")
                .withCheck(new WifiMulticastLockRule())
                .verifyIssues();
    }

    @Test
    public void verifyNoIssue() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/bottleneck/WifiMulticastLockNoIssueCheck.java")
                .withCheck(new WifiMulticastLockRule())
                .verifyNoIssues();
    }
}
