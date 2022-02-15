package io.ecocode.xml.checks.batch;

import io.ecocode.xml.checks.XPathSimpleCheck;
import org.sonar.check.Rule;

@Rule(key = "EBAT001", name = "ecoServiceBootTimeXml")
public class ServiceBootTimeXmlRule extends XPathSimpleCheck {

    private static final String SERVICE_BOOT_TIME_ATTRIBUTE = "//manifest/application/receiver/intent-filter/action/@name[. = \"android.intent.action.BOOT_COMPLETED\"]";
    private static final String ERROR_MESSAGE = "Avoid using a receiver to launch a service with BOOT_COMPLETED to drain less battery";

    @Override
    protected String getMessage() {return ERROR_MESSAGE;}

    @Override
    protected String getXPathExpressionString() {return SERVICE_BOOT_TIME_ATTRIBUTE;}
}
