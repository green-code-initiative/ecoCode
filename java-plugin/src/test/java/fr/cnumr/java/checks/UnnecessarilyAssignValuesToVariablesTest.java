package fr.cnumr.java.checks;

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

}