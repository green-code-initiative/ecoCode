package fr.cnumr.php.checks;

import org.junit.Test;
import org.sonar.plugins.php.api.tests.PHPCheckTest;
import org.sonar.plugins.php.api.tests.PhpTestFile;

import java.io.File;

public class AvoidTryCatchFinallyCheckNOKfailsAllTryStatementsTest {

        @Test
        public void test() throws Exception {
            PHPCheckTest.check(new AvoidTryCatchFinallyCheck_NOK_failsAllTryStatements(), new PhpTestFile(new File("src/test/resources/checks/avoidTryCatchFinallyCheck_NOK_FailsAllTryStatements.php")));
        }

}
