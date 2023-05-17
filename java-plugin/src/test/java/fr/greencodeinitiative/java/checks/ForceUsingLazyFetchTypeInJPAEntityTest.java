package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class ForceUsingLazyFetchTypeInJPAEntityTest {

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
    void checkAllTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseAllInOne.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyIssues();
    }

    @Test
    void checkDefaultOneToOneTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseDefaultOneToOne.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyIssues();
    }

    @Test
    void checkDefaultManyToOneTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseDefaultManyToOne.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyIssues();
    }

    @Test
    void checkManyToOneTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseManyToOne.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyIssues();
    }

    @Test
    void checkOneToOneTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUseOneToOne.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyIssues();

    }

}
