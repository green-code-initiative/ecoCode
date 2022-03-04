package io.ecocode.java.checks.idleness;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class DurableWakeLockRuleTest {

    @Test
    public void attributePresentTrue() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/DurableWakeLockCheck.java")
                .withCheck(new DurableWakeLockRule())
                .verifyIssues();
    }
}
