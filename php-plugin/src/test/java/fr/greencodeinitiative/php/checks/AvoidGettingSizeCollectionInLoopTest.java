package fr.greencodeinitiative.php.checks;

import org.junit.Test;
import org.sonar.plugins.php.api.tests.PHPCheckTest;
import org.sonar.plugins.php.api.tests.PhpTestFile;

import java.io.File;

public class AvoidGettingSizeCollectionInLoopTest {

    @Test
    public void test() throws Exception {
        PHPCheckTest.check(new AvoidGettingSizeCollectionInLoopCheck(), new PhpTestFile(new File("src/test/resources/checks/AvoidGettingSizeCollectionInLoop.php")));
    }
}
