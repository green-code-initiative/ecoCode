package io.ecocode.xml.checks.sobriety;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class DarkUIBrightColorsXmlRuleTest {

    @Test
    public void colorCheck() {
        SonarXmlCheckVerifier.verifyIssues("DarkUIBrightColorsXmlCheck.xml", new DarkUIBrightColorsXmlRule());
    }

    @Test
    public void colorResourceCheck() {
        SonarXmlCheckVerifier.verifyIssues("DarkUIBrightColorsXmlCheckResources.xml", new DarkUIBrightColorsXmlRule());
    }
}
