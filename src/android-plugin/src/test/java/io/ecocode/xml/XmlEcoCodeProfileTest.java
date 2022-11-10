package io.ecocode.xml;

import org.junit.Test;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.api.utils.ValidationMessages;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlEcoCodeProfileTest {

    @Test
    public void should_create_sonar_way_profile() {
        ValidationMessages validation = ValidationMessages.create();

        BuiltInQualityProfilesDefinition.Context context = new BuiltInQualityProfilesDefinition.Context();

        XmlEcoCodeProfile definition = new XmlEcoCodeProfile();
        definition.define(context);

        BuiltInQualityProfilesDefinition.BuiltInQualityProfile profile = context.profile(Xml.KEY, Xml.PROFILE_NAME);

        assertThat(profile.language()).isEqualTo(Xml.KEY);
        assertThat(profile.name()).isEqualTo(Xml.PROFILE_NAME);
        // "-1" because we do not want the rule "DarkUIBrightColors" in the profile
        assertThat(profile.rules()).hasSize(XmlCheckList.getXmlChecks().size() - 1);
        assertThat(validation.hasErrors()).isFalse();
    }
}
