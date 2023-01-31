package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class FreeResourcesOfAutoCloseableInterfaceTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/FreeResourcesOfAutoCloseableInterface.java")
                .withCheck(new FreeResourcesOfAutoCloseableInterface())
                .withJavaVersion(7)
                .verifyIssues();
    }

    @Test
    void test_no_java_version() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/FreeResourcesOfAutoCloseableInterface.java")
                .withCheck(new FreeResourcesOfAutoCloseableInterface())
                .verifyIssues();
    }
}