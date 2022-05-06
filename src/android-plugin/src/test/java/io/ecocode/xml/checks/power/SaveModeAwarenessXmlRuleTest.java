package io.ecocode.xml.checks.power;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class SaveModeAwarenessXmlRuleTest {

    @Test
    public void attributePresentTrue() {
        SonarXmlCheckVerifier.verifyIssues("SaveModeAwarenessXmlCheck.xml", new SaveModeAwarenessXmlRule());
    }

}