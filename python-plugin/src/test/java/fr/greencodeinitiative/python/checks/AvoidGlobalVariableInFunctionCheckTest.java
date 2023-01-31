package fr.greencodeinitiative.python.checks;

import org.junit.Test;
import org.sonar.python.checks.utils.PythonCheckVerifier;

public class AvoidGlobalVariableInFunctionCheckTest {
    @Test
    public void test() {
        PythonCheckVerifier.verify("src/test/resources/checks/avoidGlobalVariableInFunction.py", new AvoidGlobalVariableInFunctionCheck());
    }
}
