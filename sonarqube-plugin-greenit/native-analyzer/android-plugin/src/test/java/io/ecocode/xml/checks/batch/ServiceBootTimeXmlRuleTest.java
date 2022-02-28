package io.ecocode.xml.checks.batch;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class ServiceBootTimeXmlRuleTest {

    @Test
    public void attributePresentTrue() {
        SonarXmlCheckVerifier.verifyIssues("ServiceBootTimeXmlCheck.xml", new ServiceBootTimeXmlRule());
    }
}
