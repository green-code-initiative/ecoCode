package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

import fr.cnumr.java.utils.FilesUtils;

class AvoidSpringRepositoryCallInLoopCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidSpringRepositoryCallInLoopCheck.java")
                .withCheck(new AvoidSpringRepositoryCallInLoopCheck())
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .verifyIssues();
    }

}
