package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ThriftyNotificationRuleTest {

    @Test
    public void verify() {

        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyNotificationCheckBuilder.java")
                .withCheck(new ThriftyNotificationRule())
                .verifyIssues();

        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyNotificationCheckChannel.java")
                .withCheck(new ThriftyNotificationRule())
                .verifyIssues();
    }
}
