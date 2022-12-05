package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class AvoidSetConstantInBatchInsertTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidSetConstantInBatchUpdateCheck.java")
                .withCheck(new AvoidSetConstantInBatchUpdate())
                .verifyIssues();
    }

}