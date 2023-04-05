package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class ForceUsingLazyFetchTypeInJPAEntityTest {

    @Test
    void checkNonCompliantTests() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/ForceLazyFetchTypeUse.java")
                .withCheck(new ForceUsingLazyFetchTypeInJPAEntity())
                .verifyIssues();
    }

    @Test
    void checkCompliantTests() {
       /* CheckVerifier.newVerifier()
                .onFile("src/test/files/GoodWayConcatenateStringsLoop.java")
                .withCheck(new AvoidConcatenateStringsInLoop())
                .verifyNoIssues();

        */
    }

}
