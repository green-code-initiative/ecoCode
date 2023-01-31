package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidMultipleIfElseStatementTest {
    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidMultipleIfElseStatement.java")
                .withCheck(new AvoidMultipleIfElseStatement())
                .verifyIssues();
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidMultipleIfElseStatementNoIssue.java")
                .withCheck(new AvoidMultipleIfElseStatement())
                .verifyNoIssues();
    }


}
