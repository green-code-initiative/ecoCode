package io.ecocode.java.checks.leakage;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class MediaLeakMediaRecorderRuleTest {

    @Test
    public void onlyConstructor() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/MediaLeakMediaRecorderCheckIssue.java")
                .withCheck(new MediaLeakMediaRecorderRule())
                .verifyIssues();
    }

    @Test
    public void constructorAndRelease() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/MediaLeakMediaRecorderCheckNoIssue.java")
                .withCheck(new MediaLeakMediaRecorderRule())
                .verifyNoIssues();
    }
}
