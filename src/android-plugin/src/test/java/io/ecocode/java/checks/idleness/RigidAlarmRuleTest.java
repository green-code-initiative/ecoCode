package io.ecocode.java.checks.idleness;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class RigidAlarmRuleTest {

    @Test
    public void checkRigidAlarm() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/idleness/RigidAlarmCheck.java")
                .withCheck(new RigidAlarmRule())
                .verifyIssues();
    }
}
