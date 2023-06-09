package io.ecocode.rules;

import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.utils.Version;

/**
 * This class is used to reduce duplication code and to pass SonarCloud Quality Gate
 * //TODO: Remove this class when repository will be split
 */
public final class Common {
  private static final Version SONARQUBE_RUNTIME_VERSION = Version.create(9, 8);

  /**
   * Base compatibility version of plugins
   */
  public static final SonarRuntime SONARQUBE_RUNTIME = new SonarRuntime() {
    @Override
    public Version getApiVersion() {
      return SONARQUBE_RUNTIME_VERSION;
    }

    @Override
    public SonarProduct getProduct() {
      return SonarProduct.SONARQUBE;
    }

    @Override
    public SonarQubeSide getSonarQubeSide() {
      return SonarQubeSide.SCANNER;
    }

    @Override
    public SonarEdition getEdition() {
      return SonarEdition.COMMUNITY;
    }
  };

  private Common() {
  }
}
