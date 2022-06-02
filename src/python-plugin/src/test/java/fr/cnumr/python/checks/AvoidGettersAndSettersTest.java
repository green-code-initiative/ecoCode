package fr.cnumr.python.checks;

import org.junit.Test;
import org.sonar.python.checks.utils.PythonCheckVerifier;

public class AvoidGettersAndSettersTest {
    
    @Test
    public void test() {
        PythonCheckVerifier.verify("src/test/resources/checks/avoidGettersAndSettersRaiseIssue.py", new AvoidGettersAndSetters());
        PythonCheckVerifier.verifyNoIssue("src/test/resources/checks/avoidGettersAndSetters.py", new AvoidGettersAndSetters());
    }
}
