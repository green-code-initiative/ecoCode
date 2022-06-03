package io.ecocode.java.checks.release;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ClearCacheCheckTest {
    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/release/ClearCacheCheck.java")
                .withChecks(new ClearCacheRule())
                .verifyIssues();
    }
}
