package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidSQLRequestInLoopCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidSQLRequestInLoopCheck.java")
                .withCheck(new AvoidSQLRequestInLoop())
                .verifyIssues();
    }

}