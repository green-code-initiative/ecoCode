package io.ecocode.xml.checks.power;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class CompagnionInBackgroundXmlRuleTest {

    @Test
    public void attributePresentTrue() {
        SonarXmlCheckVerifier.verifyIssues("CompagnionInBackgroundXmlCheck.xml", new CompagnionInBackgroundXmlRule());
    }
}
