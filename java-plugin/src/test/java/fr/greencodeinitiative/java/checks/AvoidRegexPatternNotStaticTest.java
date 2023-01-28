package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class AvoidRegexPatternNotStaticTest {

    @Test
    void testHasIssues() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidRegexPatternNotStatic.java")
                .withCheck(new AvoidRegexPatternNotStatic())
                .verifyIssues();
    }

    @Test
    void testHasNoIssues() {
        CheckVerifier.newVerifier()
                .onFiles(
                        "src/test/files/ValidRegexPattern.java",
                        "src/test/files/ValidRegexPattern2.java",
                        "src/test/files/ValidRegexPattern3.java"
                )
                .withCheck(new AvoidRegexPatternNotStatic())
                .verifyNoIssues();
    }
}
