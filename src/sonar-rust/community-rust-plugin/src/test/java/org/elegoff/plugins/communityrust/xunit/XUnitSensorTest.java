/**
 * Community Rust Plugin
 * Copyright (C) 2021 Eric Le Goff
 * mailto:community-rust AT pm DOT me
 * http://github.com/elegoff/sonar-rust
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
package org.elegoff.plugins.communityrust.xunit;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.LogTester;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class XUnitSensorTest {
    private XUnitSensor unitSensor;
    private SensorContextTester context;
    private MapSettings settings;

    private File moduleBaseDir = new File("src/test/resources/org/elegoff/plugins/communityrust/xunit").getAbsoluteFile();

    @Rule
    public LogTester logTester = new LogTester();

    @Before
    public void init() {

        unitSensor = new XUnitSensor();
        settings = new MapSettings();
        settings.setProperty(XUnitSensor.REPORT_PATH_KEY, "basic.xml");
        context = SensorContextTester.create(moduleBaseDir);
        context.setSettings(settings);
    }

    @Test
    public void test_basic_report() {
        unitSensor.execute(context);
        assertThat(moduleMeasure(CoreMetrics.TESTS)).isEqualTo(1);
        assertThat(moduleMeasure(CoreMetrics.SKIPPED_TESTS)).isEqualTo(0);
        assertThat(moduleMeasure(CoreMetrics.TEST_ERRORS)).isEqualTo(0);
        assertThat(moduleMeasure(CoreMetrics.TEST_FAILURES)).isEqualTo(0);
    }

    @Test
    public void test_report_with_failure() {

        settings.setProperty(XUnitSensor.REPORT_PATH_KEY, "report_with_failure.xml");
        context = SensorContextTester.create(moduleBaseDir);
        context.setSettings(settings);

        unitSensor.execute(context);
        assertThat(moduleMeasure(CoreMetrics.TESTS)).isEqualTo(13);
        assertThat(moduleMeasure(CoreMetrics.SKIPPED_TESTS)).isEqualTo(0);
        assertThat(moduleMeasure(CoreMetrics.TEST_ERRORS)).isEqualTo(0);
        assertThat(moduleMeasure(CoreMetrics.TEST_FAILURES)).isEqualTo(1);
    }

    @Test
    public void shouldReportNothingWhenNoReportFound() {
        settings.setProperty(XUnitSensor.REPORT_PATH_KEY, "notexistingpath");
        unitSensor.execute(context);
        assertThat(context.measures(context.module().key())).isEmpty();
    }

    private Integer moduleMeasure(Metric<Integer> metric) {
        return measure(context.module(), metric);
    }

    private Integer measure(InputComponent component, Metric<Integer> metric) {
        return context.measure(component.key(), metric).value();
    }


}
