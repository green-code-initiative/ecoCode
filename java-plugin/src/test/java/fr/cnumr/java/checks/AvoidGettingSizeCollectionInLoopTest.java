package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidGettingSizeCollectionInLoopTest {
    @Test
    void testBadForLoop() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInForLoopBad.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyIssues();
    }

    @Test
    void testGoodForLoop() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInForLoopGood.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyNoIssues();
    }

    @Test
    void testBadWhileFoop() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInWhileLoopBad.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyIssues();
    }

    @Test
    void testGoodWhileLoop() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInWhileLoopGood.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyNoIssues();
    }

    @Test
    void testIgnoredForEachLoop() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInForEachLoopIgnored.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyNoIssues();
    }
}
