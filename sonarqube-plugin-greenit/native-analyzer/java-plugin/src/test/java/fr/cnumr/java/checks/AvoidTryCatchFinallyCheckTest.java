package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidTryCatchFinallyCheckTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidTryCatchFinallyCheck.java")
                .withCheck(new AvoidTryCatchFinallyCheck())
                .verifyIssues();
    }
}
