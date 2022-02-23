/*
 * SonarQube Java Custom Rules Example
 * Copyright (C) 2016-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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

        RulesDefinition.Rule serviceBootTimeXmlRule = repository.rule("EBAT001");
        assertThat(serviceBootTimeXmlRule).isNotNull();
        assertThat(serviceBootTimeXmlRule.name()).isEqualTo("Batch: Service Boot Time");

        RulesDefinition.Rule keepScreenOnXmlRule = repository.rule("EIDL003");
        assertThat(keepScreenOnXmlRule).isNotNull();
        assertThat(keepScreenOnXmlRule.name()).isEqualTo("Idleness: Keep Screen On");

        RulesDefinition.Rule keepCpuOnXmlRule = repository.rule("EIDL005");
        assertThat(keepCpuOnXmlRule).isNotNull();
        assertThat(keepCpuOnXmlRule.name()).isEqualTo("Idleness: Keep CPU On");

        RulesDefinition.Rule compagnionInBackgroundXmlRule = repository.rule("EPOW002");
        assertThat(compagnionInBackgroundXmlRule).isNotNull();
        assertThat(compagnionInBackgroundXmlRule.name()).isEqualTo("Power: Compagnion In Background");

        RulesDefinition.Rule ignoreBatteryOptimizationsXmlRule = repository.rule("EPOW003");
        assertThat(ignoreBatteryOptimizationsXmlRule).isNotNull();
        assertThat(ignoreBatteryOptimizationsXmlRule.name()).isEqualTo("Power: Ignore Battery Optimizations");

        RulesDefinition.Rule chargeAwarenessXmlRule = repository.rule("EPOW005");
        assertThat(chargeAwarenessXmlRule).isNotNull();
        assertThat(chargeAwarenessXmlRule.name()).isEqualTo("Power: Charge Awareness");

        RulesDefinition.Rule DarkUIBrightColorsXmlRule = repository.rule("ESOB003");
        assertThat(DarkUIBrightColorsXmlRule).isNotNull();
        assertThat(DarkUIBrightColorsXmlRule.name()).isEqualTo("Sobriety: Dark UI (Bright Colors)");

        RulesDefinition.Rule DarkUIThemeXmlRule = repository.rule("ESOB004");
        assertThat(DarkUIThemeXmlRule).isNotNull();
        assertThat(DarkUIThemeXmlRule.name()).isEqualTo("Sobriety: Dark UI (Theme)");

        RulesDefinition.Rule saveModeAwarenessXml = repository.rule("EPOW007");
        assertThat(saveModeAwarenessXml).isNotNull();
        assertThat(saveModeAwarenessXml.name()).isEqualTo("Power: Save Mode Awareness");

        for (RulesDefinition.Rule rule : repository.rules()) {
            for (RulesDefinition.Param param : rule.params()) {
                assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
            }
        }
    }
}
