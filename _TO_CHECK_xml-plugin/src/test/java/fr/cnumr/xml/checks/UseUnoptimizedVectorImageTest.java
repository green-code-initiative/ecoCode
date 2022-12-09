package fr.cnumr.xml.checks;

import java.io.IOException;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class UseUnoptimizedVectorImageTest {

	@Test
	public void test() throws IOException {
		UseUnoptimizedVectorImage check = new UseUnoptimizedVectorImage();
		SonarXmlCheckVerifier.verifyIssueOnFile("UseUnoptimizedVectorImage.svg", check, "Issue on node(s) <metadata> <title> <desc> of the vector image.");
	}

	@Test
	public void testSvgo() throws IOException {
		UseUnoptimizedVectorImage check = new UseUnoptimizedVectorImage();
		SonarXmlCheckVerifier.verifyNoIssue("UseUnoptimizedVectorImageSVGO.svg", check);
	}

	@Test
	public void test3() throws IOException {
		UseUnoptimizedVectorImage check = new UseUnoptimizedVectorImage();
		SonarXmlCheckVerifier.verifyIssueOnFile("pom.xml", check, "Issue on node(s) <title> <desc> of the vector image.");
	}

}