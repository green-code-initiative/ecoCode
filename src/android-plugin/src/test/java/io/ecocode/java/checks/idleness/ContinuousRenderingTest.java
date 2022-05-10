package io.ecocode.java.checks.idleness;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ContinuousRenderingTest {

    @Test
    public void checkContinuousRendering() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/ContinuousRenderingCheck.java")
                .withCheck(new ContinuousRenderingRule())
                .verifyIssues();
    }
}
