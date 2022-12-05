package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidRegexPatternNotStaticTest {

    @Test
    public void testHasIssues() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidRegexPatternNotStatic.java")
                .withCheck(new AvoidRegexPatternNotStatic())
                .verifyIssues();
    }

    @Test
    public void testHasNoIssues() {
        JavaCheckVerifier.newVerifier()
                .onFiles(
                        "src/test/files/ValidRegexPattern.java",
                        "src/test/files/ValidRegexPattern2.java",
                        "src/test/files/ValidRegexPattern3.java"
                )
                .withCheck(new AvoidRegexPatternNotStatic())
                .verifyNoIssues();
    }
}
