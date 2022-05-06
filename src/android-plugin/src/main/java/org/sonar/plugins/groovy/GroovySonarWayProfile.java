/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2021 SonarQube Community
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
package org.sonar.plugins.groovy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.plugins.groovy.codenarc.CodeNarcRulesDefinition;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonarsource.api.sonarlint.SonarLintSide;

@SonarLintSide
public class GroovySonarWayProfile implements BuiltInQualityProfilesDefinition {

  @Override
  public void define(Context context) {
    NewBuiltInQualityProfile sonarWay =
        context.createBuiltInQualityProfile(Groovy.PROFILE_NAME, Groovy.KEY);

    addRulesFromText(CodeNarcRulesDefinition.REPOSITORY_KEY, sonarWay);
    sonarWay.done();
  }

  private static void addRulesFromText(String repo, NewBuiltInQualityProfile profile) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(
                GroovySonarWayProfile.class.getResourceAsStream("profile-default.txt"),
                StandardCharsets.UTF_8))) {
      reader
          .lines()
          .forEach(
              (String rule) -> {
                if (!rule.isEmpty()) {
                  profile.activateRule(repo, rule);
                }
              });
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read: profile-default.txt", e);
    }
  }
}
