package io.ecocode.java.checks.sobriety;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ThriftyGeolocationCriteriaTest {

    @Test
    public void verifyOnlyRequest() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyGeolocationCriteriaCheckOnlyRequest.java")
                .withCheck(new ThriftyGeolocationCriteriaRule())
                .verifyIssues();
    }

    @Test
    public void verifyNoCriteria() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyGeolocationCriteriaCheckNoCriteria.java")
                .withCheck(new ThriftyGeolocationCriteriaRule())
                .verifyIssues();
    }

    @Test
    public void verifyWrongCriteria() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyGeolocationCriteriaCheckWrongCriteria.java")
                .withCheck(new ThriftyGeolocationCriteriaRule())
                .verifyIssues();
    }

    @Test
    public void verifyNoIssue() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/sobriety/ThriftyGeolocationCriteriaCheckNoIssue.java")
                .withCheck(new ThriftyGeolocationCriteriaRule())
                .verifyNoIssues();
    }
}
