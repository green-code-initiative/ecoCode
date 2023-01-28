package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidFullSQLRequestCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidFullSQLRequestCheck.java")
                .withCheck(new AvoidFullSQLRequest())
                .verifyIssues();
    }

}