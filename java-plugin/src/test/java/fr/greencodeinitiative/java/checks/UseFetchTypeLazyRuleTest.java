package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;


class UseFetchTypeLazyRuleTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/UseFetchTypeLazyRule.java")
                .withCheck(new UseFetchTypeLazyRule())
                .verifyIssues();
    }
}