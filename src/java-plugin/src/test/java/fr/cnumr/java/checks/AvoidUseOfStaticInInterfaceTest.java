package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidUseOfStaticInInterfaceTest {

    @Test
    public void checkNonCompliantTests() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/AvoidUseOfStaticInInterface.java")
                .withCheck(new AvoidUseOfStaticInInterface())
                .verifyIssues();
    }

    @Test
    public void checkCompliantTests() {
        JavaCheckVerifier.newVerifier()
                .onFile("src/test/files/GoodUsageOfEnumInsteadOfInterface.java")
                .withCheck(new AvoidUseOfStaticInInterface())
                .verifyNoIssues();
    }
}
