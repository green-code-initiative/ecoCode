package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class NoFunctionCallWhenDeclaringForLoopTest {
    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/NoFunctionCallWhenDeclaringForLoop.java")
                .withCheck(new NoFunctionCallWhenDeclaringForLoop())
                .verifyIssues();
    }
}
