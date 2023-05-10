package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class FetchTypeLazyCheckTest {

    @Test
    void testNoIssues() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/FetchTypeLazyCheckGood.java")
                .withCheck(new FetchTypeLazyCheck())
                .verifyNoIssues();
    }


    @Test
    void testIssues() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/FetchTypeLazyCheckBad.java")
                .withCheck(new FetchTypeLazyCheck())
                .verifyIssues();
    }

}
