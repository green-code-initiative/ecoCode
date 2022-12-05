package fr.cnumr.php.checks;

import org.junit.Test;
import org.sonar.plugins.php.api.tests.PHPCheckTest;
import org.sonar.plugins.php.api.tests.PhpTestFile;

import java.io.File;

public class UseOfMethodsForBasicOperationsTest {

    @Test
    public void test() throws Exception {
        PHPCheckTest.check(new UseOfMethodsForBasicOperations(), new PhpTestFile(new File("src/test/resources/checks/useOfMethodsForBasicOperations.php")));
    }
}
