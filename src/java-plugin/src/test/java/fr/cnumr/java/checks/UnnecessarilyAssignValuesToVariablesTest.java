package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class UnnecessarilyAssignValuesToVariablesTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/UnnecessarilyAssignValuesToVariablesTestCheck.java")
                .withCheck(new UnnecessarilyAssignValuesToVariables())
                .verifyIssues();
    }

}