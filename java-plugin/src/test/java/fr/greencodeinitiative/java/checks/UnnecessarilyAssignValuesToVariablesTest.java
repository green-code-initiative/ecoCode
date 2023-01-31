package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class UnnecessarilyAssignValuesToVariablesTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/UnnecessarilyAssignValuesToVariablesTestCheck.java")
                .withCheck(new UnnecessarilyAssignValuesToVariables())
                .verifyIssues();
    }

    @Test
    void testIgnoredEmptyReturn() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/UnnecessarilyAssignValuesToVariablesTestCheckWithEmptyReturn.java")
                .withCheck(new UnnecessarilyAssignValuesToVariables())
                .verifyNoIssues();
    }

}