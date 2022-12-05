package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class UseCorrectLoopCheckTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/UseCorrectForLoopCheck.java")
                .withCheck(new UseCorrectForLoop())
                .verifyIssues();
    }

}