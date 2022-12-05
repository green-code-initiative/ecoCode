package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidUsageOfStaticCollectionsTests {

    @Test
    public void testHasIssues() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidUsageOfStaticCollections.java")
                .withCheck(new AvoidUsageOfStaticCollections())
                .verifyIssues();
    }

    @Test
    public void testNoIssues() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/GoodUsageOfStaticCollections.java")
                .withCheck(new AvoidUsageOfStaticCollections())
                .verifyNoIssues();
    }

}
