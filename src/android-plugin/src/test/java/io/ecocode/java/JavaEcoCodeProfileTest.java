package io.ecocode.java;

import org.junit.Test;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.api.utils.ValidationMessages;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaEcoCodeProfileTest {

    @Test
    public void should_create_sonar_way_profile() {
        ValidationMessages validation = ValidationMessages.create();

        BuiltInQualityProfilesDefinition.Context context = new BuiltInQualityProfilesDefinition.Context();

        JavaEcoCodeProfile definition = new JavaEcoCodeProfile();
        definition.define(context);

        BuiltInQualityProfilesDefinition.BuiltInQualityProfile profile = context.profile(Java.KEY, Java.PROFILE_NAME);

        assertThat(profile.language()).isEqualTo(Java.KEY);
        assertThat(profile.name()).isEqualTo(Java.PROFILE_NAME);
        assertThat(profile.rules()).hasSameSizeAs(JavaCheckList.getJavaChecks());
        assertThat(validation.hasErrors()).isFalse();
    }
}
