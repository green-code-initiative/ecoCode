package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class OptimizeDatabaseQueriesTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeDatabaseQueries.java")
                .withCheck(new OptimizeDatabaseQueries())
                .verifyIssues();
    }

}