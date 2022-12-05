package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class NoFunctionCallWhenDeclaringForLoopTest {
    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/NoFunctionCallWhenDeclaringForLoop.java")
                .withCheck(new NoFunctionCallWhenDeclaringForLoop())
                .verifyIssues();
    }
}
