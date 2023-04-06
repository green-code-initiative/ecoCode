package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class DetectUnoptimizedImageFormatTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/DetectUnoptimizedImageFormat.java")
                .withCheck(new DetectUnoptimizedImageFormat())
                .verifyIssues();
    }

    @Test
    void testComplient() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/DetectUnoptimizedFileFormatComplient.java")
                .withCheck(new DetectUnoptimizedImageFormat())
                .verifyNoIssues();
    }
}
