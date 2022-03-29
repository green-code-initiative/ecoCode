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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.plugins.groovy.foundation.Groovy;

public class GroovySensorTest {

  private MapSettings settings = new MapSettings();
  private FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
  private DefaultFileSystem fileSystem = new DefaultFileSystem(Paths.get("."));
  private GroovySensor sensor =
      new GroovySensor(settings.asConfig(), fileLinesContextFactory, fileSystem);

  @Test
  public void do_nothing_when_no_groovy_file() throws IOException {
    SensorContextTester context = SensorContextTester.create(Paths.get("."));
    context = Mockito.spy(context);
    sensor = new GroovySensor(settings.asConfig(), fileLinesContextFactory, context.fileSystem());
    sensor.execute(context);

    Mockito.verify(context, Mockito.never()).newHighlighting();
  }

  @Test
  public void compute_metrics() throws IOException {
    testMetrics(false, 5);
  }

  @Test
  public void compute_metrics_ignoring_header_comment() throws IOException {
    testMetrics(true, 3);
  }

  private void testMetrics(boolean headerComment, int expectedCommentMetric) throws IOException {
    settings.setProperty(GroovySensor.IGNORE_HEADER_COMMENTS, headerComment);

    Path sourceFile = TestUtils.getResource(getClass(), "../gmetrics/Greeting.groovy");
    SensorContextTester context = SensorContextTester.create(sourceFile.getParent());

    fileSystem = context.fileSystem();
    InputFile groovyFile =
        TestInputFileBuilder.create("", sourceFile.getParent().toFile(), sourceFile.toFile())
            .setLanguage(Groovy.KEY)
            .setContents(new String(Files.readAllBytes(sourceFile), StandardCharsets.UTF_8))
            .setCharset(StandardCharsets.UTF_8)
            .build();
    fileSystem.add(groovyFile);

    FileLinesContext fileLinesContext = mock(FileLinesContext.class);
    when(fileLinesContextFactory.createFor(any(DefaultInputFile.class)))
        .thenReturn(fileLinesContext);

    sensor = new GroovySensor(settings.asConfig(), fileLinesContextFactory, fileSystem);
    sensor.execute(context);

    String key = groovyFile.key();
    assertThat(context.measure(key, CoreMetrics.CLASSES).value()).isEqualTo(2);
    assertThat(context.measure(key, CoreMetrics.FUNCTIONS).value()).isEqualTo(2);

    assertThat(context.measure(key, CoreMetrics.NCLOC).value()).isEqualTo(17);
    assertThat(context.measure(key, CoreMetrics.COMMENT_LINES).value())
        .isEqualTo(expectedCommentMetric);

    assertThat(context.measure(key, CoreMetrics.COMPLEXITY).value()).isEqualTo(4);

    // 11 times for comment because we register comment even when ignoring header comment
    Mockito.verify(fileLinesContext, Mockito.times(17))
        .setIntValue(Mockito.eq(CoreMetrics.NCLOC_DATA_KEY), anyInt(), Mockito.eq(1));
    Mockito.verify(fileLinesContext).setIntValue(CoreMetrics.NCLOC_DATA_KEY, 18, 1);
    // Only "Greeting.groovy" is part of the file system.
    Mockito.verify(fileLinesContext, Mockito.times(1)).save();
  }

  @Test
  public void test_toString() {
    assertThat(sensor).hasToString("GroovySensor");
  }

  @Test
  public void test_description() {
    DefaultSensorDescriptor defaultSensorDescriptor = new DefaultSensorDescriptor();
    sensor.describe(defaultSensorDescriptor);
    assertThat(defaultSensorDescriptor.languages()).containsOnly(Groovy.KEY);
  }
}
