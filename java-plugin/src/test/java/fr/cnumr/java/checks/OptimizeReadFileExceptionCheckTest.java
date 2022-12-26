package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class OptimizeReadFileExceptionCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeReadFileExceptionCheck.java")
                .withCheck(new OptimizeReadFileExceptions())
                .verifyIssues();
    }

}
