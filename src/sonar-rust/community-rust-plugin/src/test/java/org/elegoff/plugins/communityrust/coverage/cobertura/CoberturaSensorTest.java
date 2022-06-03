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
package org.elegoff.plugins.communityrust.coverage.cobertura;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


import org.elegoff.plugins.communityrust.CommunityRustPlugin;
import org.elegoff.plugins.communityrust.Utils;
import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.utils.log.LogTester;
import org.sonar.api.utils.log.LoggerLevel;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;


public class CoberturaSensorTest {

    private static final String TESTFILE1 = "moduleKey:src/cgroups/common.rs";
    private static final String TESTFILE2 = "moduleKey:src/cgroups/test.rs";
    private static final String TESTFILE3 = "moduleKey:src/process/init.rs";

    private SensorContextTester context;
    private MapSettings settings;

    
    private CoberturaSensor coberturaSensor;
    private File moduleBaseDir = new File("src/test/resources/org/elegoff/plugins/communityrust/cobertura").getAbsoluteFile();

    @Rule
    public LogTester logTester = new LogTester();

    @Rule
    public TemporaryFolder tmpDir = new TemporaryFolder();

    @Before
    public void init() {

        coberturaSensor = new CoberturaSensor();
        settings = new MapSettings();
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "cobertura.xml");
        context = SensorContextTester.create(moduleBaseDir);
        context.setSettings(settings);

        inputFile("src/cgroups/common.rs", Type.MAIN);
        inputFile("src/cgroups/test.rs", Type.MAIN);
        inputFile("src/process/fork.rs", Type.MAIN);
        inputFile("src/process/init.rs", Type.MAIN);
    }

    private InputFile inputFile(String relativePath, Type type) {
        DefaultInputFile inputFile = TestInputFileBuilder.create("moduleKey", relativePath)
                .setModuleBaseDir(moduleBaseDir.toPath())
                .setLanguage(RustLanguage.KEY)
                .setType(type)
                .initMetadata(Utils.fileContent(new File(moduleBaseDir, relativePath), StandardCharsets.UTF_8))
                .build();

        context.fileSystem().add(inputFile);

        return inputFile;
    }


    @Test
    public void absolute_path() {
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, new File(moduleBaseDir, "cobertura.xml").getAbsolutePath());

        coberturaSensor.execute(context);

        assertThat(context.lineHits(TESTFILE1, 42)).isEqualTo(1);
    }

    @Test
    public void test_coverage() {
        coberturaSensor.execute(context);
        Map<Integer, Integer> file1Expected = new HashMap<>();
        file1Expected.put(42,1);
        file1Expected.put(43,3);
        file1Expected.put(48,1);

        checkLineHits(TESTFILE1, file1Expected);

        Map<Integer, Integer> file2Expected = new HashMap<>();
        file2Expected.put(39,1);
        file2Expected.put(40,1);
        file2Expected.put(45,0);
        file2Expected.put(46,0);

        checkLineHits(TESTFILE2, file2Expected);


    }

    private void checkLineHits(String fileKey, Map<Integer, Integer> expect){
        for (Integer k : expect.keySet()) {
            assertThat(context.lineHits(fileKey, k)).isEqualTo(expect.get(k));
        }
    }



    @Test
    public void test_ambigous_locations() {
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "coverage-other-2.xml");
        coberturaSensor.execute(context);

        assertThat(context.lineHits("moduleKey:src/cgroups/common.rs", 30)).isEqualTo(3);
        //  ambiguity detection
        assertThat(context.lineHits("moduleKey:src/cgroups/test.rs", 1)).isNull();
        assertThat(context.lineHits("moduleKey:src/process]/test.rs", 1)).isNull();
    }



    @Test
    public void test_report_with_absolute_path() throws Exception {
        String reportPath = generateReportWithAbsPaths();
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, reportPath);

        coberturaSensor.execute(context);

        assertThat(context.lineHits(TESTFILE1, 1)).isEqualTo(1);
        assertThat(context.lineHits(TESTFILE1, 4)).isZero();
        assertThat(context.lineHits(TESTFILE1, 6)).isZero();
    }

    @Test
    public void test_unresolved_path() {
        String separator = File.separator;

        logTester.clear();

        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "unresolved.xml");
        coberturaSensor.execute(context);

        String expectedMsg = String.format(
                "The file name '/mymodule/src/fake.rs' of the coverage report can not be resolved, the file does not exist in all <source>.",
                separator,
                separator,
                separator);
        assertThat(logTester.logs(LoggerLevel.ERROR)).containsExactly(
                expectedMsg,
                "2 file paths can not be resolved, coverage measures will be ignored for those files");
    }


    @Test
    public void test_multiple_reports() {
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "cobertura.xml,coverage-other*.xml");
        coberturaSensor.execute(context);

        assertThat(context.conditions(TESTFILE2, 2)).isNull();
        assertThat(context.lineHits(TESTFILE2, 39)).isEqualTo(1);
        assertThat(context.lineHits(TESTFILE2, 45)).isZero();
        assertThat(context.lineHits(TESTFILE3, 7)).isNull();
        assertThat(context.lineHits(TESTFILE3, 12)).isZero();
    }


    @Test(expected = IllegalStateException.class)
    public void fail_with_invalid_report() {
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "invalid.xml");
        coberturaSensor.execute(context);
    }

    @Test(expected = IllegalStateException.class)
    public void fail_with_invalid_eof() {
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "wrong_eof.xml");
        coberturaSensor.execute(context);
    }

    @Test
    public void ok_when_empty_report() {
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "empty.xml");
        coberturaSensor.execute(context);
        assertThat(context.lineHits(TESTFILE1, 42)).isNull();
    }

    @Test
    public void default_debug_logs() {
        settings.clear();
        CoberturaSensor sensor = new CoberturaSensor();
        sensor.execute(context);
        assertThat(logTester.logs(LoggerLevel.DEBUG)).contains("Using pattern 'cobertura.xml' to find reports");
    }

    @Test
    public void sensor_descriptor() {
        DefaultSensorDescriptor descriptor = new DefaultSensorDescriptor();
        new CoberturaSensor().describe(descriptor);
        assertThat(descriptor.name()).isEqualTo("Cobertura Sensor for Rust");
        assertThat(descriptor.languages()).containsOnly(RustLanguage.KEY);
    }

    private String generateReportWithAbsPaths() throws Exception {
        Path tmpPath = tmpDir.newFolder("sonar-rust").toPath().toAbsolutePath();
        String reportName="unresolved.xml";

        String absoluteSourcePath = new File(moduleBaseDir, TESTFILE1).getAbsolutePath();
        Path report = new File(moduleBaseDir, reportName).toPath();
        String reportContent = new String(Files.readAllBytes(report), UTF_8);
        reportContent = reportContent.replace("{CHANGE_ME}", absoluteSourcePath);

        Path generated = tmpPath.resolve(reportName);
        Files.write(generated, reportContent.getBytes(UTF_8));

        return generated.toAbsolutePath().toString();
    }

    @Test
    public void report_not_found() {
        settings.setProperty(CommunityRustPlugin.COBERTURA_REPORT_PATHS, "/report/not/found.xml");
        coberturaSensor.execute(context);
        assertThat(context.lineHits(TESTFILE1, 1)).isNull();
    }

}