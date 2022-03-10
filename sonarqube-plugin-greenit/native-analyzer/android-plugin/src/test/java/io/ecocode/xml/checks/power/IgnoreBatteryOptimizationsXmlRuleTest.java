package io.ecocode.xml.checks.power;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class IgnoreBatteryOptimizationsXmlRuleTest {

    @Test
    public void attributePresentTrue() {
        SonarXmlCheckVerifier.verifyIssues("IgnoreBatteryOptimizationsXmlCheckReport.xml", new IgnoreBatteryOptimizationsXmlRule());
    }

    @Test
    public void attributePresentFalse() {
        SonarXmlCheckVerifier.verifyNoIssue("IgnoreBatteryOptimizationsXmlCheckNoReport.xml", new IgnoreBatteryOptimizationsXmlRule());
    }
}
