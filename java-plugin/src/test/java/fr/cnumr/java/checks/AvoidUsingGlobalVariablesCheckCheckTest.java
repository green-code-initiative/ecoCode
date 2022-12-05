package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class AvoidUsingGlobalVariablesCheckCheckTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidUsingGlobalVariablesCheck.java")
                .withCheck(new AvoidUsingGlobalVariablesCheck())
                .verifyIssues();
    }

}