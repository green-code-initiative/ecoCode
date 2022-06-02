package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class AvoidFullSQLRequestCheckTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidFullSQLRequestCheck.java")
                .withCheck(new AvoidFullSQLRequest())
                .verifyIssues();
    }

}