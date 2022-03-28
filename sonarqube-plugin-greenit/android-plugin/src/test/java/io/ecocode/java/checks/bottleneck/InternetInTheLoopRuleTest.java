package io.ecocode.java.checks.bottleneck;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class InternetInTheLoopRuleTest {

    @Test
    public void verifyLoop() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/bottleneck/InternetInTheLoopCheck.java")
                .withCheck(new InternetInTheLoopRule())
                .verifyIssues();
    }
}
