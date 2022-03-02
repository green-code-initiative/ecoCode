package io.ecocode.xml;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlRulesDefinitionTest {

    @Test
    public void test() {
        XmlRulesDefinition rulesDefinition = new XmlRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        RulesDefinition.Repository repository = context.repository(Xml.REPOSITORY_KEY);

        assertThat(repository.name()).isEqualTo(Xml.REPOSITORY_NAME);
        assertThat(repository.language()).isEqualTo(Xml.KEY);
        assertThat(repository.rules()).hasSize(XmlCheckList.getXmlChecks().size());

        RulesDefinition.Rule alertUseRule = repository.rule("ESOB004");
        assertThat(alertUseRule).isNotNull();
        assertThat(alertUseRule.name()).isEqualTo("Sobriety: Dark UI (Theme)");

        RulesDefinition.Rule keepScreenOnXmlRule = repository.rule("EIDL003");
        assertThat(keepScreenOnXmlRule).isNotNull();
        assertThat(keepScreenOnXmlRule.name()).isEqualTo("Idleness: Keep Screen On");

        RulesDefinition.Rule xmlBrightnessRule = repository.rule("ESOB003");
        assertThat(xmlBrightnessRule).isNotNull();
        assertThat(xmlBrightnessRule.name()).isEqualTo("Sobriety: Dark UI (Bright Colors)");

        RulesDefinition.Rule ignoreBatteryOptimizations = repository.rule("EPOW003");
        assertThat(ignoreBatteryOptimizations).isNotNull();
        assertThat(ignoreBatteryOptimizations.name()).isEqualTo("Power: Ignore Battery Optimizations");

        RulesDefinition.Rule keepCpuOnXml = repository.rule("EIDL005");
        assertThat(keepCpuOnXml).isNotNull();
        assertThat(keepCpuOnXml.name()).isEqualTo("Idleness: Keep CPU On");

        RulesDefinition.Rule compagnionInBackgroundXml = repository.rule("EPOW002");
        assertThat(compagnionInBackgroundXml).isNotNull();
        assertThat(compagnionInBackgroundXml.name()).isEqualTo("Power: Compagnion In Background");

        RulesDefinition.Rule chargeAwarenessXml = repository.rule("EPOW005");
        assertThat(chargeAwarenessXml).isNotNull();
        assertThat(chargeAwarenessXml.name()).isEqualTo("Power: Charge Awareness");

        RulesDefinition.Rule serviceBootTimeXml = repository.rule("EBAT001");
        assertThat(serviceBootTimeXml).isNotNull();
        assertThat(serviceBootTimeXml.name()).isEqualTo("Batch: Service Boot Time");

        for (RulesDefinition.Rule rule : repository.rules()) {
            for (RulesDefinition.Param param : rule.params()) {
                assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
            }
        }
    }

}
