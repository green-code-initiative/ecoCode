package io.ecocode.java.checks.idleness;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class KeepCpuOnRuleTest {

    @Test
    public void attributePresentTrue() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/KeepCpuOnCheck.java")
                .withCheck(new KeepCpuOnRule())
                .verifyIssues();
    }
}
