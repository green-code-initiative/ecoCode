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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.plugins.groovy.foundation.Groovy;
import org.sonar.plugins.groovy.surefire.api.SurefireUtils;

/** Created by iwarapter */
public class GroovySurefireSensorTest {

  private DefaultFileSystem fs = new DefaultFileSystem(Paths.get("."));
  private GroovySurefireSensor surefireSensor;
  private PathResolver pathResolver = new PathResolver();
  private Groovy groovy;

  @Before
  public void before() {
    fs = new DefaultFileSystem(Paths.get("."));
    InputFile groovyFile =
        TestInputFileBuilder.create("", "src/org/foo/grvy").setLanguage(Groovy.KEY).build();
    fs.add(groovyFile);

    MapSettings settings = new MapSettings();
    settings.setProperty(Groovy.FILE_SUFFIXES_KEY, ".groovy,grvy");
    groovy = new Groovy(settings.asConfig());

    GroovySurefireParser parser = spy(new GroovySurefireParser(groovy, fs));

    doAnswer(invocation -> inputFile((String) invocation.getArguments()[0]))
        .when(parser)
        .getUnitTestInputFile(anyString());

    surefireSensor = new GroovySurefireSensor(parser, settings.asConfig(), fs, pathResolver);
  }

  @Test
  public void testDescription() {
    DefaultSensorDescriptor defaultSensorDescriptor = new DefaultSensorDescriptor();
    surefireSensor.describe(defaultSensorDescriptor);
    assertThat(defaultSensorDescriptor.languages()).containsOnly(Groovy.KEY);
  }

  @Test
  public void shouldNotFailIfReportsNotFound() {
    MapSettings settings = new MapSettings();
    settings.setProperty(SurefireUtils.SUREFIRE_REPORT_PATHS_PROPERTY, "unknown");

    GroovySurefireSensor localSensor =
        new GroovySurefireSensor(
            mock(GroovySurefireParser.class), settings.asConfig(), fs, pathResolver);
    localSensor.execute(mock(SensorContext.class));
  }

  @Test
  public void shouldHandleTestSuiteDetails() throws URISyntaxException {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context
        .fileSystem()
        .add(inputFile("org.sonar.core.ExtensionsFinderTest"))
        .add(inputFile("org.sonar.core.ExtensionsFinderTest2"))
        .add(inputFile("org.sonar.core.ExtensionsFinderTest3"));
    surefireSensor.collect(
        context,
        Collections.singletonList(
            new File(
                getClass()
                    .getResource(
                        "/org/sonar/plugins/groovy/surefire/SurefireSensorTest/shouldHandleTestSuiteDetails/")
                    .toURI())));

    // 3 classes, 5 measures by class
    assertThat(context.measures(":org.sonar.core.ExtensionsFinderTest")).hasSize(5);
    assertThat(context.measures(":org.sonar.core.ExtensionsFinderTest2")).hasSize(5);
    assertThat(context.measures(":org.sonar.core.ExtensionsFinderTest3")).hasSize(5);

    assertThat(context.measure(":org.sonar.core.ExtensionsFinderTest", CoreMetrics.TESTS).value())
        .isEqualTo(4);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest", CoreMetrics.TEST_EXECUTION_TIME)
                .value())
        .isEqualTo(111);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest", CoreMetrics.TEST_FAILURES)
                .value())
        .isEqualTo(1);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest", CoreMetrics.TEST_ERRORS)
                .value())
        .isEqualTo(1);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest", CoreMetrics.SKIPPED_TESTS)
                .value())
        .isEqualTo(0);

    assertThat(context.measure(":org.sonar.core.ExtensionsFinderTest2", CoreMetrics.TESTS).value())
        .isEqualTo(2);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest2", CoreMetrics.TEST_EXECUTION_TIME)
                .value())
        .isEqualTo(2);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest2", CoreMetrics.TEST_FAILURES)
                .value())
        .isEqualTo(0);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest2", CoreMetrics.TEST_ERRORS)
                .value())
        .isEqualTo(0);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest2", CoreMetrics.SKIPPED_TESTS)
                .value())
        .isEqualTo(0);

    assertThat(context.measure(":org.sonar.core.ExtensionsFinderTest3", CoreMetrics.TESTS).value())
        .isEqualTo(1);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest3", CoreMetrics.TEST_EXECUTION_TIME)
                .value())
        .isEqualTo(16);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest3", CoreMetrics.TEST_FAILURES)
                .value())
        .isEqualTo(0);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest3", CoreMetrics.TEST_ERRORS)
                .value())
        .isEqualTo(0);
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest3", CoreMetrics.SKIPPED_TESTS)
                .value())
        .isEqualTo(1);
  }

  private static InputFile inputFile(String key) {
    return TestInputFileBuilder.create("", key).setType(InputFile.Type.TEST).build();
  }

  @Test
  public void shouldSaveErrorsAndFailuresInXML() throws URISyntaxException {

    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context
        .fileSystem()
        .add(inputFile("org.sonar.core.ExtensionsFinderTest"))
        .add(inputFile("org.sonar.core.ExtensionsFinderTest2"))
        .add(inputFile("org.sonar.core.ExtensionsFinderTest3"));

    surefireSensor.collect(
        context,
        Collections.singletonList(
            new File(
                getClass()
                    .getResource(
                        "/org/sonar/plugins/groovy/surefire/SurefireSensorTest/shouldSaveErrorsAndFailuresInXML/")
                    .toURI())));

    // 1 classes, 6 measures by class
    assertThat(
            context
                .measure(":org.sonar.core.ExtensionsFinderTest", CoreMetrics.SKIPPED_TESTS)
                .value())
        .isEqualTo(1);
    assertThat(context.measure(":org.sonar.core.ExtensionsFinderTest", CoreMetrics.TESTS).value())
        .isEqualTo(7);
    assertThat(context.measures(":org.sonar.core.ExtensionsFinderTest")).hasSize(5);
  }

  @Test
  public void shouldManageClassesWithDefaultPackage() throws URISyntaxException {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context.fileSystem().add(inputFile("NoPackagesTest"));

    surefireSensor.collect(
        context,
        Collections.singletonList(
            new File(
                getClass()
                    .getResource(
                        "/org/sonar/plugins/groovy/surefire/SurefireSensorTest/shouldManageClassesWithDefaultPackage/")
                    .toURI())));

    assertThat(context.measure(":NoPackagesTest", CoreMetrics.TESTS).value()).isEqualTo(2);
  }

  @Test
  public void successRatioIsZeroWhenAllTestsFail() throws URISyntaxException {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context.fileSystem().add(inputFile("org.sonar.Foo"));

    surefireSensor.collect(
        context,
        Collections.singletonList(
            new File(
                getClass()
                    .getResource(
                        "/org/sonar/plugins/groovy/surefire/SurefireSensorTest/successRatioIsZeroWhenAllTestsFail/")
                    .toURI())));

    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TESTS).value()).isEqualTo(2);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TEST_FAILURES).value()).isEqualTo(1);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TEST_ERRORS).value()).isEqualTo(1);
  }

  @Test
  public void measuresShouldNotIncludeSkippedTests() throws URISyntaxException {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context.fileSystem().add(inputFile("org.sonar.Foo"));

    surefireSensor.collect(
        context,
        Collections.singletonList(
            new File(
                getClass()
                    .getResource(
                        "/org/sonar/plugins/groovy/surefire/SurefireSensorTest/measuresShouldNotIncludeSkippedTests/")
                    .toURI())));

    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TESTS).value()).isEqualTo(2);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TEST_FAILURES).value()).isEqualTo(1);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TEST_ERRORS).value()).isEqualTo(0);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.SKIPPED_TESTS).value()).isEqualTo(1);
  }

  @Test
  public void noSuccessRatioIfNoTests() throws URISyntaxException {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context.fileSystem().add(inputFile("org.sonar.Foo"));

    surefireSensor.collect(
        context,
        Collections.singletonList(
            new File(
                getClass()
                    .getResource(
                        "/org/sonar/plugins/groovy/surefire/SurefireSensorTest/noSuccessRatioIfNoTests/")
                    .toURI())));

    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TESTS).value()).isEqualTo(0);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TEST_FAILURES).value()).isEqualTo(0);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.TEST_ERRORS).value()).isEqualTo(0);
    assertThat(context.measure(":org.sonar.Foo", CoreMetrics.SKIPPED_TESTS).value()).isEqualTo(2);
  }

  @Test
  public void ignoreSuiteAsInnerClass() throws URISyntaxException {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context.fileSystem().add(inputFile("org.sonar.Foo"));

    surefireSensor.collect(
        context,
        Collections.singletonList(
            new File(
                getClass()
                    .getResource(
                        "/org/sonar/plugins/groovy/surefire/SurefireSensorTest/ignoreSuiteAsInnerClass/")
                    .toURI())));

    // ignore TestHandler$Input.xml
    assertThat(
            context.measure(":org.apache.shindig.protocol.TestHandler", CoreMetrics.TESTS).value())
        .isEqualTo(0);
    assertThat(
            context
                .measure(":org.apache.shindig.protocol.TestHandler", CoreMetrics.SKIPPED_TESTS)
                .value())
        .isEqualTo(1);
  }
}
