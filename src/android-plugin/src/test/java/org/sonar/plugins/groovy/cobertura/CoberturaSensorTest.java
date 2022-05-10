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
package org.sonar.plugins.groovy.cobertura;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.plugins.groovy.TestUtils;
import org.sonar.plugins.groovy.foundation.Groovy;

public class CoberturaSensorTest {

  private MapSettings settings;
  private CoberturaSensor sensor;
  private DefaultFileSystem fileSystem;

  @Before
  public void setUp() throws Exception {
    settings = new MapSettings();
    settings.setProperty(
        CoberturaSensor.COBERTURA_REPORT_PATH,
        TestUtils.getResource(getClass(), "../coverage.xml").toString());
    fileSystem = new DefaultFileSystem(Paths.get("."));
    sensor = new CoberturaSensor(settings.asConfig(), fileSystem);
  }

  @Test
  public void test_description() {
    DefaultSensorDescriptor defaultSensorDescriptor = new DefaultSensorDescriptor();
    sensor.describe(defaultSensorDescriptor);
    assertThat(defaultSensorDescriptor.languages()).containsOnly(Groovy.KEY);
  }

  @Test
  public void should_parse_report() {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    DefaultFileSystem fs = context.fileSystem();
    fs.add(
        TestInputFileBuilder.create("", "com/test/web/EmptyResultException.java")
            .setLanguage("java")
            .build());
    fs.add(
        TestInputFileBuilder.create("", "grails-app/domain/AboveEighteenFilters.groovy")
            .setLanguage(Groovy.KEY)
            .setType(Type.MAIN)
            .setLines(Integer.MAX_VALUE)
            .build());

    sensor = new CoberturaSensor(settings.asConfig(), fs);
    sensor.execute(context);

    // random pick groovy file
    String filekey = ":grails-app/domain/AboveEighteenFilters.groovy";
    int[] lineHits = {2, 6, 7};
    int[] lineNoHits = {9, 10, 11};

    for (int line : lineHits) {
      assertThat(context.lineHits(filekey, line)).isEqualTo(1);
    }
    for (int line : lineNoHits) {
      assertThat(context.lineHits(filekey, line)).isZero();
    }

    // No value for java file
    assertThat(context.lineHits(":com/test/web/EmptyResultException.java", 16)).isNull();
  }

  @Test
  public void should_not_save_any_measure_if_files_can_not_be_found() {
    FileSystem mockfileSystem = mock(FileSystem.class);
    when(mockfileSystem.predicates()).thenReturn(fileSystem.predicates());
    when(mockfileSystem.inputFile(any(FilePredicate.class))).thenReturn(null);
    sensor = new CoberturaSensor(settings.asConfig(), mockfileSystem);

    SensorContext context = mock(SensorContext.class);
    sensor.execute(context);

    Mockito.verify(context, Mockito.never()).newCoverage();
  }

  @Test
  public void should_not_parse_report_if_settings_does_not_contain_report_path() {
    DefaultFileSystem fileSystem = new DefaultFileSystem(Paths.get("."));
    fileSystem.add(TestInputFileBuilder.create("", "fake.groovy").setLanguage(Groovy.KEY).build());
    sensor = new CoberturaSensor(new MapSettings().asConfig(), fileSystem);

    SensorContext context = mock(SensorContext.class);
    sensor.execute(context);

    Mockito.verify(context, Mockito.never()).newCoverage();
  }

  @Test
  public void should_not_parse_report_if_report_does_not_exist() {
    MapSettings settings = new MapSettings();
    settings.setProperty(
        CoberturaSensor.COBERTURA_REPORT_PATH,
        "org/sonar/plugins/groovy/cobertura/fake-coverage.xml");

    DefaultFileSystem fileSystem = new DefaultFileSystem(Paths.get("."));
    fileSystem.add(TestInputFileBuilder.create("", "fake.groovy").setLanguage(Groovy.KEY).build());

    sensor = new CoberturaSensor(settings.asConfig(), fileSystem);

    SensorContext context = mock(SensorContext.class);
    sensor.execute(context);

    Mockito.verify(context, Mockito.never()).newCoverage();
  }

  @Test
  public void should_use_relative_path_to_get_report() {
    MapSettings settings = new MapSettings();
    settings.setProperty(
        CoberturaSensor.COBERTURA_REPORT_PATH,
        "//org/sonar/plugins/groovy/cobertura/fake-coverage.xml");

    DefaultFileSystem fileSystem = new DefaultFileSystem(Paths.get("."));
    fileSystem.add(TestInputFileBuilder.create("", "fake.groovy").setLanguage(Groovy.KEY).build());

    sensor = new CoberturaSensor(settings.asConfig(), fileSystem);

    SensorContext context = mock(SensorContext.class);
    sensor.execute(context);

    Mockito.verify(context, Mockito.never()).newCoverage();
  }

  @Test
  public void should_execute_on_project() {
    fileSystem.add(TestInputFileBuilder.create("", "fake.groovy").setLanguage(Groovy.KEY).build());
    assertThat(sensor.shouldExecuteOnProject()).isTrue();
  }

  @Test
  public void should_not_execute_if_no_groovy_files() {
    assertThat(sensor.shouldExecuteOnProject()).isFalse();
  }

  @Test
  public void test_toString() {
    assertThat(sensor).hasToString("Groovy CoberturaSensor");
  }
}
