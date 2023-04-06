package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

/**
 * @author Mohamed Oussama ABIDI.
 * Created on 05/04/2023
 * @version 1.0
 */
class AvoidMethodUsageForOnlyBasicOperationsTest {

    @Test
    void it_should_verify_non_compliant_basic_operations_usage_in_methods() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidMethodUsageForOnlyBasicOperations.java")
                .withCheck(new AvoidMethodUsageForOnlyBasicOperations())
                .verifyIssues();
    }
}
