package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class AvoidConcatenateStringsInLoopTest {

    @Test
    void checkNonCompliantTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidConcatenateStringsInLoop.java")
                .withCheck(new AvoidConcatenateStringsInLoop())
                .verifyIssues();
    }

    @Test
    void checkCompliantTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/GoodWayConcatenateStringsLoop.java")
                .withCheck(new AvoidConcatenateStringsInLoop())
                .verifyNoIssues();
    }

}
