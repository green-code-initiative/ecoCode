package io.ecocode.java.checks.optimized_api;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class FusedLocationCheckTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/optimized_api/FusedLocationCheck.java")
                .withCheck(new FusedLocationRule())
                .verifyIssues();
    }

    @Test
    public void verifyNoIssue() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/optimized_api/FusedLocationCheckNoIssue.java")
                .withCheck(new FusedLocationRule())
                .verifyNoIssues();
    }
}
