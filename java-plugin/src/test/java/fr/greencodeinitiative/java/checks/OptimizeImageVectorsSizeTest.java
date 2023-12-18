package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class OptimizeImageVectorsSizeTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeImageVectorsSize.java")
                .withCheck(new OptimizeImageVectorsSize())
                .verifyIssues();
    }

}