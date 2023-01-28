package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class AvoidStatementForDMLQueriesTest {
    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidStatementForDMLQueries.java")
                .withCheck(new AvoidStatementForDMLQueries())
                .verifyIssues();
    }
}
