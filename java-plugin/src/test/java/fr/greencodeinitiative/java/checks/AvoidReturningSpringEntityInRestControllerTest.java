package fr.greencodeinitiative.java.checks;

import fr.greencodeinitiative.java.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidReturningSpringEntityInRestControllerTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidReturningSpringEntityInRestController.java")
                .withCheck(new AvoidReturningSpringEntityInRestController())
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .verifyIssues();
    }


}
