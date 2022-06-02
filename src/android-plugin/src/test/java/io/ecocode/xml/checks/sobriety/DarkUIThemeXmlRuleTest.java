package io.ecocode.xml.checks.sobriety;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class DarkUIThemeXmlRuleTest {

    @Test
    public void styleCheck() {
        SonarXmlCheckVerifier.verifyIssues("DarkUIThemeXmlCheckStyle.xml", new DarkUIThemeXmlRule());
    }

    @Test
    public void manifestCheck() {
        SonarXmlCheckVerifier.verifyIssues("DarkUIThemeXmlCheckManifest.xml", new DarkUIThemeXmlRule());
    }
}
