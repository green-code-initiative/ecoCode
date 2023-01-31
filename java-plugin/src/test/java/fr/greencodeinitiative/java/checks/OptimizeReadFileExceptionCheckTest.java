package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class OptimizeReadFileExceptionCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeReadFileExceptionCheck.java")
                .withCheck(new OptimizeReadFileExceptions())
                .verifyIssues();
    }

    @Test
    void test2() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeReadFileExceptionCheck2.java")
                .withCheck(new OptimizeReadFileExceptions())
                .verifyIssues();
    }

    @Test
    void test3() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeReadFileExceptionCheck3.java")
                .withCheck(new OptimizeReadFileExceptions())
                .verifyIssues();
    }

    @Test
    void test4() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeReadFileExceptionCheck4.java")
                .withCheck(new OptimizeReadFileExceptions())
                .verifyIssues();
    }

    @Test
    void test5() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/OptimizeReadFileExceptionCheck5.java")
                .withCheck(new OptimizeReadFileExceptions())
                .verifyIssues();
    }

}
