/*
 * SonarQube Python Plugin
 * Copyright (C) 2012-2019 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
package fr.greencodeinitiative.php;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.internal.PluginContextImpl;
import org.sonar.api.utils.Version;

import static fr.greencodeinitiative.php.PhpRuleRepository.SONARQUBE_RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

class PhpPluginTest {
  private static final Version MINIMAL_SONARQUBE_VERSION_COMPATIBILITY = Version.create(9, 8);

  private Plugin.Context context;

  @BeforeEach
  void init() {
    context = new PluginContextImpl.Builder().setSonarRuntime(SONARQUBE_RUNTIME).build();
  }

  @Test
  void test() {
    new PHPPlugin().define(context);
    assertThat(context.getExtensions()).hasSize(1);
  }

  @Test
  void testPluginCompatibility() {
    SonarRuntime sonarRuntime = context.getRuntime();
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
