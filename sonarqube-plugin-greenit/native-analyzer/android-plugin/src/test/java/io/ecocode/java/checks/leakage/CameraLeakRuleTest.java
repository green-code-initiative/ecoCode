package io.ecocode.java.checks.leakage;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CameraLeakRuleTest {

    @Test
    public void CameraOnlyRegister() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/CameraLeakCheckIssue.java")
                .withCheck(new CameraLeakRule())
                .verifyIssues();
    }

    @Test
    public void CameraRegisterAndUnregister() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/leakage/CameraLeakCheckNoIssue.java")
                .withCheck(new CameraLeakRule())
                .verifyNoIssues();
    }
}
