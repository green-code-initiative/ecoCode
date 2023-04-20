package fr.greencodeinitiative.java.checks;

import fr.greencodeinitiative.java.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidNPlusOneQueryProblemTest {

    /**
     * @formatter:off
     */
    @Test
    void testHasIssues() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidNPlusOneQueryProblemBad.java")
                .withCheck(new AvoidNPlusOneQueryProblemCheck())
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .verifyIssues();
    }

    @Test
    void testHasNoIssue() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidNPlusOneQueryProblemGood.java")
                .withCheck(new AvoidNPlusOneQueryProblemCheck())
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .verifyNoIssues();
    }

    @Test
    void testHasNoIssueWhenNotRepository() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidNPlusOneQueryProblemNotRepositoryGood.java")
                .withCheck(new AvoidNPlusOneQueryProblemCheck())
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .verifyNoIssues();
    }

}
