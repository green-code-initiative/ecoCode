package fr.cnumr.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class ArrayCopyCheckTest {

	/**
	 * @formatter:off
	 */
	@Test
	void test() {
		CheckVerifier.newVerifier()
			.onFile("src/test/files/ArrayCopyCheck.java")
			.withCheck(new ArrayCopyCheck())
			.verifyIssues();
	}

}