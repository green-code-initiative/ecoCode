package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

class FreeResourcesOfAutoCloseableInterfaceTest {

    @Test
    void test() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/FreeResourcesOfAutoCloseableInterface.java")
                .withCheck(new FreeResourcesOfAutoCloseableInterface())
                .withJavaVersion(7)
                .verifyIssues();
    }

    @Test
    void test_no_java_version() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/FreeResourcesOfAutoCloseableInterface.java")
                .withCheck(new FreeResourcesOfAutoCloseableInterface())
                .verifyIssues();
    }
}