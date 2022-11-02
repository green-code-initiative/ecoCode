package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidConcatenateStringsInLoopTest {

    @Test
    public void checkNonCompliantTests() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidConcatenateStringsInLoop.java")
                .withCheck(new AvoidConcatenateStringsInLoop())
                .verifyIssues();
    }

    @Test
    public void checkCompliantTests() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/GoodWayConcatenateStringsLoop.java")
                .withCheck(new AvoidConcatenateStringsInLoop())
                .verifyNoIssues();
    }

}
