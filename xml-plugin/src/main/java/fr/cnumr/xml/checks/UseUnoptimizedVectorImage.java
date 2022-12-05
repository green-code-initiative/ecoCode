package fr.cnumr.xml.checks;

import java.util.Collections;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;

@Rule(key = "S99", name = "Developpement", description = UseUnoptimizedVectorImage.MESSAGERULE, priority = Priority.MINOR, tags = { "bug" })
public class UseUnoptimizedVectorImage extends SonarXmlCheck {

	protected static final String MESSAGERULE = "Avoid the use of unoptimized vector images";

	private static final String PATTERN_METADATA = "<metadata";
	private static final String PATTERN_TITLE = "<title";
	private static final String PATTERN_DESCRIPTION = "<desc";

	@Override
	public void scanFile(XmlFile file) {
		StringBuilder nodesError = new StringBuilder();
		if (file.getContents().contains(PATTERN_METADATA)) {
			nodesError = nodesError.append("<metadata> ");
		}
		if (file.getContents().contains(PATTERN_TITLE)) {
			nodesError = nodesError.append("<title> ");
		}
		if (file.getContents().contains(PATTERN_DESCRIPTION)) {
			nodesError = nodesError.append("<desc> ");

		}
		if (nodesError.length() > 0) {
			reportIssueOnFile("Issue on node(s) " + nodesError.toString() + "of the vector image.", Collections.emptyList());
		}
	}

}