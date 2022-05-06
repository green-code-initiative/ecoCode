package io.ecocode.java.checks.batch;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class JobCoalesceRuleTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/batch/JobCoalesceCheckAlarmManager.java")
                .withCheck(new JobCoalesceRule())
                .verifyIssues();

        JavaCheckVerifier.newVerifier().onFile("src/test/files/batch/JobCoalesceCheckSyncAdapter.java")
                .withCheck(new JobCoalesceRule())
                .verifyIssues();
    }
}
