package fr.greencodeinitiative.python.checks;

import org.junit.Test;
import org.sonar.python.checks.utils.PythonCheckVerifier;

public class AvoidDoubleQuoteTest {
    @Test
    public void test() {
        PythonCheckVerifier.verify("src/test/resources/checks/avoidDoubleQuoteCheck.py", new AvoidDoubleQuoteCheck());
    }
}
