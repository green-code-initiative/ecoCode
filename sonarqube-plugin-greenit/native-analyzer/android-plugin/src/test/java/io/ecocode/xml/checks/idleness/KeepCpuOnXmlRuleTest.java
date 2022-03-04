package io.ecocode.xml.checks.idleness;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class KeepCpuOnXmlRuleTest {

    @Test
    public void attributePresentTrue() {
        SonarXmlCheckVerifier.verifyIssues("KeepCpuOnXmlCheckReport.xml", new KeepCpuOnXmlRule());
    }

    @Test
    public void attributePresentFalse() {
        SonarXmlCheckVerifier.verifyNoIssue("KeepCpuOnXmlCheckNoReport.xml", new KeepCpuOnXmlRule());
    }
}
