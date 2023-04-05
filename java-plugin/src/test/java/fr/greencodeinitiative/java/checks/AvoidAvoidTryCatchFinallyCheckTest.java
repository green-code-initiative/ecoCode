package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;


class AvoidAvoidTryCatchFinallyCheckTest {

    @Test
    public void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidTryCatchFinallyCheck.java")
                .withCheck(new AvoidTryCatchFinallyCheck())
                .verifyIssues();
    }
}