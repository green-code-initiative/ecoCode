package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidGettingSizeCollectionInLoopTest {
    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInLoopBad.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyIssues();
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInLoopGood.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyNoIssues();
    }
}
