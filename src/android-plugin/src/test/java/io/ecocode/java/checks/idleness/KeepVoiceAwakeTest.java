package io.ecocode.java.checks.idleness;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class KeepVoiceAwakeTest {

    @Test
    public void checkSetKeepAwakeToFalse() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/KeepVoiceAwakeToFalseCheck.java")
                .withCheck(new KeepVoiceAwakeRule())
                .verifyNoIssues();
    }

    @Test
    public void checkSetKeepAwakeToTrue() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/KeepVoiceAwakeCheck.java")
                .withCheck(new KeepVoiceAwakeRule())
                .verifyIssues();
    }

    @Test
    public void checkSetKeepAwakeNotPresent() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/KeepVoiceAwakeNotPresentCheck.java")
                .withCheck(new KeepVoiceAwakeRule())
                .verifyIssues();
    }
}
