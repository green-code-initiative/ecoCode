package fr.greencodeinitiative.php.checks;

import java.io.File;

import org.junit.Test;
import org.sonar.plugins.php.api.tests.PHPCheckTest;
import org.sonar.plugins.php.api.tests.PhpTestFile;

public class AvoidFullSQLRequestCheckTest {

    @Test
    public void test() {
        PHPCheckTest.check(new AvoidFullSQLRequestCheck(), new PhpTestFile(new File("src/test/resources/checks/AvoidFullSQLRequest.php")));
    }
}
