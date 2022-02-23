package io.ecocode.java.checks.bottleneck;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class UncompressedDataTransmissionRulesTest {

    @Test
    public void verify() {
        JavaCheckVerifier.newVerifier().onFile("src/test/files/bottleneck/UncompressedDataTransmissionCheck.java")
                .withCheck(new UncompressedDataTransmissionRule())
                .verifyIssues();
    }

}
