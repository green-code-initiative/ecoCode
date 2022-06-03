package io.ecocode.java.checks.social.privacy;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class GoogleTrackerRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/social/privacy/GoogleTrackerCheck.java")
                .withCheck(new GoogleTrackerRule())
                .verifyIssues();

        JavaCheckVerifier.newVerifier().onFile("src/test/files/social/privacy/GoogleTrackerFirebaseCheck.java")
                .withCheck(new GoogleTrackerRule())
                .verifyIssues();
    }
}
