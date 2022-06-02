package io.ecocode.java.checks.batch;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class SensorCoalesceRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/batch/SensorCoalesceCheck.java")
                .withCheck(new SensorCoalesceRule())
                .verifyIssues();
    }
}
