package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class UseCorrectLoopCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/UseCorrectForLoopCheck.java")
                .withCheck(new UseCorrectForLoop())
                .verifyIssues();
    }

}