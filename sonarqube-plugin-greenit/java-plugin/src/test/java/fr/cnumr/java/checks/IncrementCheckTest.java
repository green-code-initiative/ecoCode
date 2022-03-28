package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class IncrementCheckTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/IncrementCheck.java")
                .withCheck(new IncrementCheck())
                .verifyIssues();
    }

}