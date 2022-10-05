package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidGettingSizeCollectionInLoopTest {
    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInLoopBad.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyIssues();
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidGettingSizeCollectionInLoopGood.java")
                .withCheck(new AvoidGettingSizeCollectionInLoop())
                .verifyNoIssues();
    }
}