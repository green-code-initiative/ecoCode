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
package org.elegoff.plugins.communityrust;

import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.utils.log.LogTester;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RustSensorTest {

    private FileLinesContext fileLinesContext;
    private SensorContextTester tester;
    private RustSensor sensor;
    private File dir = new File("src/test/resources/");
    private static String LIBFILE = "sensor/lib.rs" ;
    private static String SIMPLE = "sensor/simple.rs" ;


    @org.junit.Rule
    public LogTester logTester = new LogTester();

    @Before
    public void init() {
        tester = SensorContextTester.create(dir);

        MapSettings settings = CommunityRustPluginConfigurationTest.getDefaultSettings();
        tester.setSettings(settings);

        FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
        fileLinesContext = mock(FileLinesContext.class);
        when(fileLinesContextFactory.createFor(any(InputFile.class))).thenReturn(fileLinesContext);
        ActiveRulesBuilder activeRuleBuilder = new ActiveRulesBuilder();
        CheckFactory checkFactory = new CheckFactory(activeRuleBuilder.build());
        sensor = new RustSensor(checkFactory, fileLinesContextFactory);
    }



    @Test
    public void analyseSimple() throws IOException {
        DefaultInputFile inputFile = executeSensorOnSingleFile(SIMPLE);

        assertEquals((Integer) 10, tester.measure(inputFile.key(), CoreMetrics.NCLOC).value());
        assertEquals((Integer) 4, tester.measure(inputFile.key(), CoreMetrics.STATEMENTS).value());
        assertEquals((Integer) 4, tester.measure(inputFile.key(), CoreMetrics.COMPLEXITY).value());
        assertEquals((Integer) 1, tester.measure(inputFile.key(), CoreMetrics.COMMENT_LINES).value());
        assertEquals((Integer) 2, tester.measure(inputFile.key(), CoreMetrics.FUNCTIONS).value());
        assertEquals(10, tester.cpdTokens(inputFile.key()).size());
        assertEquals(Collections.singletonList(TypeOfText.KEYWORD), tester.highlightingTypeAt(inputFile.key(), 1, 1));
        assertEquals(Collections.singletonList(TypeOfText.COMMENT), tester.highlightingTypeAt(inputFile.key(), 8, 5));
        assertEquals(Collections.singletonList(TypeOfText.CONSTANT), tester.highlightingTypeAt(inputFile.key(), 11, 13));
        assertEquals(Collections.singletonList(TypeOfText.STRING), tester.highlightingTypeAt(inputFile.key(), 6, 13));

        assertEquals(0, tester.allIssues().size());

        verify(fileLinesContext).setIntValue(CoreMetrics.NCLOC_DATA_KEY, 1, 1);
        verify(fileLinesContext).setIntValue(CoreMetrics.NCLOC_DATA_KEY, 2, 1);
        verify(fileLinesContext).setIntValue(CoreMetrics.NCLOC_DATA_KEY, 4, 1);


        verify(fileLinesContext).save();

        Assertions.assertThat(tester.allAnalysisErrors()).isEmpty();

    }


    @Test
    public void canParse() throws IOException {
        DefaultInputFile inputFile = executeSensorOnSingleFile("sensor/checkme.rs");
        verify(fileLinesContext).save();
        Assertions.assertThat(tester.allAnalysisErrors()).isEmpty();
    }

    @Test
    public void checkDuplication() throws IOException {
        DefaultInputFile inputFile = executeSensorOnSingleFile("sensor/cpd.rs");

        assertEquals(212, tester.cpdTokens(inputFile.key()).size());
        verify(fileLinesContext).save();
        assertEquals(Collections.singletonList(TypeOfText.ANNOTATION), tester.highlightingTypeAt(inputFile.key(), 5, 5));
        Assertions.assertThat(tester.allAnalysisErrors()).isEmpty();
    }

    @Test
    public void checkDuplicationIgnoringTests() throws IOException {
        tester.settings().setProperty(CommunityRustPlugin.IGNORE_DUPLICATION_FOR_TESTS,true);
        DefaultInputFile inputFile = executeSensorOnSingleFile("sensor/cpd.rs");

        assertEquals(3, tester.cpdTokens(inputFile.key()).size());
        verify(fileLinesContext).save();
        assertEquals(Collections.singletonList(TypeOfText.ANNOTATION), tester.highlightingTypeAt(inputFile.key(), 5, 5));
        Assertions.assertThat(tester.allAnalysisErrors()).isEmpty();
    }



    private DefaultInputFile executeSensorOnSingleFile(String fileName) throws IOException {
        DefaultInputFile inputFile = addInputFile(fileName);
        sensor.execute(tester);
        return inputFile;
    }

    @Test
    public void two_files_without_cancellation() throws Exception {
        DefaultInputFile file1 = addInputFile(LIBFILE);
        DefaultInputFile file2 = addInputFile(SIMPLE);
        sensor.execute(tester);
        Assertions.assertThat(tester.measure(file1.key(), CoreMetrics.NCLOC)).isNotNull();
        Assertions.assertThat(tester.measure(file2.key(), CoreMetrics.NCLOC)).isNotNull();
    }

    @Test
    public void two_files_with_cancellation() throws Exception {
        DefaultInputFile file1 = addInputFile(LIBFILE);
        DefaultInputFile file2 = addInputFile(SIMPLE);
        tester.setCancelled(true);
        sensor.execute(tester);
        Assertions.assertThat(tester.measure(file2.key(), CoreMetrics.NCLOC)).isNotNull();
        Assertions.assertThat(tester.measure(file1.key(), CoreMetrics.NCLOC)).isNull();
    }

    private DefaultInputFile addInputFile(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(new File(dir, fileName).toPath()));
        Assertions.assertThat(content).isNotEmpty();
        DefaultInputFile inputFile = new TestInputFileBuilder(tester.module().key(), fileName)
                .setModuleBaseDir(tester.fileSystem().baseDirPath())
                .setType(InputFile.Type.MAIN)
                .setLanguage(RustLanguage.KEY)
                .setCharset(StandardCharsets.UTF_8)
                .initMetadata(content)
                .build();
        tester.fileSystem().add(inputFile);
        return inputFile;
    }

}
