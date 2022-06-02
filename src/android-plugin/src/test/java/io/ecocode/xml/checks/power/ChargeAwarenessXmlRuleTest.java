package io.ecocode.xml.checks.power;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class ChargeAwarenessXmlRuleTest {

    @Test
    public void attributePresentTrue() {
        SonarXmlCheckVerifier.verifyIssues("ChargeAwarenessXmlCheck.xml", new ChargeAwarenessXmlRule());
    }
}
