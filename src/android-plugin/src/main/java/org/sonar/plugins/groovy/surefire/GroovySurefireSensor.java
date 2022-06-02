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
package org.sonar.plugins.groovy.surefire;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.surefire.api.SurefireUtils;

@DependedUpon("surefire-java")
public class GroovySurefireSensor implements Sensor {

  private static final Logger LOGGER = Loggers.get(GroovySurefireSensor.class);

  private final GroovySurefireParser groovySurefireParser;
  private final Configuration settings;
  private final FileSystem fs;
  private final PathResolver pathResolver;

  public GroovySurefireSensor(
      GroovySurefireParser groovySurefireParser,
      Configuration settings,
      FileSystem fs,
      PathResolver pathResolver) {
    this.groovySurefireParser = groovySurefireParser;
    this.settings = settings;
    this.fs = fs;
    this.pathResolver = pathResolver;
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.onlyOnLanguage(Groovy.KEY).name("GroovySurefireSensor");
  }

  @Override
  public void execute(SensorContext context) {
    List<File> dirs = SurefireUtils.getReportDirectories(settings, fs, pathResolver);
    collect(context, dirs);
  }

  protected void collect(SensorContext context, List<File> reportsDirs) {
    LOGGER.info("parsing {}", reportsDirs);
    groovySurefireParser.collect(context, reportsDirs);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  public static List<Object> getExtensions() {
    return Arrays.asList(GroovySurefireParser.class, GroovySurefireSensor.class);
  }
}
