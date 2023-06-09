package io.ecocode.rules;

import org.junit.jupiter.api.Test;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.utils.Version;

import static io.ecocode.rules.Common.SONARQUBE_RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

class CommonTest {
  private static final Version MINIMAL_SONARQUBE_VERSION_COMPATIBILITY = Version.create(9, 8);

  @Test
  void testPluginCompatibility() {
    final SonarRuntime sonarRuntime = SONARQUBE_RUNTIME;

    assertThat(MINIMAL_SONARQUBE_VERSION_COMPATIBILITY.isGreaterThanOrEqual(sonarRuntime.getApiVersion()))
            .describedAs("Plugin must be compatible with SonarQube 9.8")
            .isTrue();
    assertThat(sonarRuntime.getProduct())
            .describedAs("Plugin should applied to SonarQube")
            .isEqualTo(SonarProduct.SONARQUBE);
    assertThat(sonarRuntime.getEdition())
            .describedAs("Plugin should be compatible with Community Edition")
            .isEqualTo(SonarEdition.COMMUNITY);
    assertThat(sonarRuntime.getSonarQubeSide())
            .describedAs("Plugin should be executed by scanner")
            .isEqualTo(SonarQubeSide.SCANNER);
  }
}
