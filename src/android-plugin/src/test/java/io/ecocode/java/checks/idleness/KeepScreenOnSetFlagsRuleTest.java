package io.ecocode.java.checks.idleness;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class KeepScreenOnSetFlagsRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/KeepScreenOnSetFlagsCheck.java")
                .withCheck(new KeepScreenOnSetFlagsRule())
                .verifyIssues();
    }
}
