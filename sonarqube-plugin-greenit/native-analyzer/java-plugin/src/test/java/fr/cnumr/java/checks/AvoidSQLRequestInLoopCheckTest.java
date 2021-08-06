package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class AvoidSQLRequestInLoopCheckTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidSQLRequestInLoopCheck.java")
                .withCheck(new AvoidSQLRequestInLoop())
                .verifyIssues();
    }

}