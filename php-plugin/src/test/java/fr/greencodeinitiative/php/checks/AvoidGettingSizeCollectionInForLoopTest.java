package fr.greencodeinitiative.php.checks;

import org.junit.Test;
import org.sonar.plugins.php.api.tests.PHPCheckTest;
import org.sonar.plugins.php.api.tests.PhpTestFile;

import java.io.File;

public class AvoidGettingSizeCollectionInForLoopTest {

    @Test
    public void test() throws Exception {
        PHPCheckTest.check(new AvoidGettingSizeCollectionInForLoopCheck(), new PhpTestFile(new File("src/test/resources/checks/AvoidGettingSizeCollectionInForLoop.php")));
    }
}
