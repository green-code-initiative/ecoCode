package fr.cnumr.python.checks;

import org.junit.Test;
import org.sonar.python.checks.utils.PythonCheckVerifier;

public class AvoidSQLRequestInLoopTest {
    @Test
    public void test() {
        PythonCheckVerifier.verify("src/test/resources/checks/avoidSQLRequestInLoop.py", new AvoidSQLRequestInLoop());
        PythonCheckVerifier.verifyNoIssue("src/test/resources/checks/avoidSQLRequestInLoopNoImports.py", new AvoidSQLRequestInLoop());
    }
}
