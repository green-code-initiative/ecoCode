package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidUsingGlobalVariablesCheckCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidUsingGlobalVariablesCheck.java")
                .withCheck(new AvoidUsingGlobalVariablesCheck())
                .verifyIssues();
    }

}