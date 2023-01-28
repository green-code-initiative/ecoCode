package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class AvoidUsageOfStaticCollectionsTests {

    @Test
    void testHasIssues() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidUsageOfStaticCollections.java")
                .withCheck(new AvoidUsageOfStaticCollections())
                .verifyIssues();
    }

    @Test
    void testNoIssues() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/GoodUsageOfStaticCollections.java")
                .withCheck(new AvoidUsageOfStaticCollections())
                .verifyNoIssues();
    }

}
