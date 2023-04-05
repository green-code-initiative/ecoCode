package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class FetchTypeLazyCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/FetchTypeLazyCheck.java")
                .withCheck(new FetchTypeLazyCheck())
                .verifyNoIssues();
    }

}
