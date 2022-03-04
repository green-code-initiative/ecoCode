package io.ecocode.xml.checks.idleness;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class KeepScreenOnXmlRuleTest {

    @Test
    public void attributePresentTrue() {
        SonarXmlCheckVerifier.verifyIssues("KeepScreenOnXmlCheckTrue.xml", new KeepScreenOnXmlRule());
    }

    @Test
    public void attributePresentFalse() {
        SonarXmlCheckVerifier.verifyNoIssue("KeepScreenOnXmlCheckFalse.xml", new KeepScreenOnXmlRule());
    }
}
