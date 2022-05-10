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

import org.sonar.api.Plugin;
import org.sonar.plugins.groovy.cobertura.CoberturaSensor;
import org.sonar.plugins.groovy.codenarc.CodeNarcSensor;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.jacoco.JaCoCoExtensions;
import org.sonar.plugins.groovy.surefire.GroovySurefireSensor;

public class GroovyPlugin implements Plugin {

  @Override
  public void define(Context context) {
    context
        .addExtensions(Groovy.getExtensions())
        .addExtensions(GroovySensor.getExtensions())
        .addExtensions(CodeNarcSensor.getExtensions())
        .addExtensions(GroovySurefireSensor.getExtensions())
        .addExtensions(CoberturaSensor.getExtensions())
        .addExtensions(JaCoCoExtensions.getExtensions());
  }
}
