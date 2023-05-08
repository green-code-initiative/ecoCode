package fr.greencodeinitiative.java.checks;

import fr.greencodeinitiative.java.utils.FilesUtils;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.sonar.api.utils.log.LogTester;
import org.sonar.api.utils.log.LoggerLevel;
import org.sonar.java.checks.verifier.CheckVerifier;

class AvoidSpringRepositoryCallInStreamCheckTest {
    @Rule
    public LogTester logTester = new LogTester().setLevel(LoggerLevel.DEBUG);
    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidSpringRepositoryCallInStreamCheck.java")
                .withCheck(new AvoidSpringRepositoryCallInStreamCheck())
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .verifyIssues();
    }

}
