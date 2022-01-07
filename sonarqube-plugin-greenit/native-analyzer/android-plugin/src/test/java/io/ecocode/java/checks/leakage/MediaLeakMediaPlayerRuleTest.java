package io.ecocode.java.checks.leakage;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class MediaLeakMediaPlayerRuleTest {

    @Test
    public void onlyConstructor() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/MediaLeakMediaPlayerCheckIssue.java")
                .withCheck(new MediaLeakMediaPlayerRule())
                .verifyIssues();
    }

    @Test
    public void constructorAndRelease() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/MediaLeakMediaPlayerCheckNoIssue.java")
                .withCheck(new MediaLeakMediaPlayerRule())
                .verifyNoIssues();
    }
}
