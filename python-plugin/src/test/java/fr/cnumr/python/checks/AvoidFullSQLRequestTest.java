package fr.cnumr.python.checks;

import org.junit.Test;
import org.sonar.python.checks.utils.PythonCheckVerifier;

public class AvoidFullSQLRequestTest {

    @Test
    public void test() {
        PythonCheckVerifier.verify("src/test/resources/checks/avoidFullSQLRequest.py", new AvoidFullSQLRequest());
    }
}
