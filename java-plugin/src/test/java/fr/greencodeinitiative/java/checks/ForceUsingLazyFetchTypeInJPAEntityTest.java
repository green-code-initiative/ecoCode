package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class ForceUsingLazyFetchTypeInJPAEntityTest {

    @Test
    void checkNonCompliantTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseNonCompliant.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyIssues();
    }

    @Test
    void checkCompliantTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseCompliant.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyNoIssues();

    }

    @Test
    void checkPositiveFalseTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseFalseTest.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyNoIssues();
    }

    @Test
    void checkPositiveFalseTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseAllInOne.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyNoIssues();
    }
}
