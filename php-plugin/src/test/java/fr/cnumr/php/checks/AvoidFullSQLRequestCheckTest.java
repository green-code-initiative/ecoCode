package fr.cnumr.php.checks;

import org.junit.Test;
import org.sonar.plugins.php.api.tests.PHPCheckTest;
import org.sonar.plugins.php.api.tests.PhpTestFile;

import java.io.File;

public class AvoidFullSQLRequestCheckTest {

	@Test
	public void test() {
		PHPCheckTest.check(new AvoidFullSQLRequestCheck(), new PhpTestFile(new File("src/test/resources/checks/AvoidFullSQLRequest.php")));
	}
}
