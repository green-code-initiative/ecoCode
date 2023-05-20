package fr.greencodeinitiative.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

import fr.greencodeinitiative.java.utils.FilesUtils;

class FilteredColumnsAreIndexedTest {

	@Test
	void test() {
		CheckVerifier.newVerifier().onFile("src/test/files/FilteredColumnsAreIndexed.java")
				.withCheck(new FilteredColumnsAreIndexed()).withClassPath(FilesUtils.getClassPath("target/test-jars"))
				.verifyIssues();
	}

}
