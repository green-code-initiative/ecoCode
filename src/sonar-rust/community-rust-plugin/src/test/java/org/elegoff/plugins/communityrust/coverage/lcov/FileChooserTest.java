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
package org.elegoff.plugins.communityrust.coverage.lcov;

import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class FileChooserTest {
    @Test
    public void should_match_suffix() {
        InputFile inputFile = new TestInputFileBuilder("module1", "src/main/java/org/sonar/test/File.rs").build();
        FileChooser locator = new FileChooser(Collections.singleton(inputFile));
        assertThat(locator.getInputFile("org/sonar/test/File.rs")).isEqualTo(inputFile);
    }

    @Test
    public void should_not_match() {
        InputFile inputFile = new TestInputFileBuilder("module1", "src/main/java/org/sonar/test/File.rs").build();
        FileChooser locator = new FileChooser(Collections.singleton(inputFile));
        assertThat(locator.getInputFile("org/sonar/test/File2.rs")).isNull();
        assertThat(locator.getInputFile("org/sonar/test2/File.rs")).isNull();
        assertThat(locator.getInputFile("not/found")).isNull();
    }

    @Test
    public void should_match_first_with_many_options() {
        InputFile inputFile1 = new TestInputFileBuilder("module1", "src/main/java/org/sonar/test/File.rs").build();
        InputFile inputFile2 = new TestInputFileBuilder("module1", "src/test/java/org/sonar/test/File.rs").build();

        FileChooser locator = new FileChooser(Arrays.asList(inputFile1, inputFile2));
        assertThat(locator.getInputFile("org/sonar/test/File.rs")).isEqualTo(inputFile1);
    }

    @Test
    public void should_normalize_paths() {
        InputFile inputFile = new TestInputFileBuilder("module1", "src/main/java/org/sonar/test/File.rs").build();

        FileChooser locator = new FileChooser(singletonList(inputFile));
        assertThat(locator.getInputFile("./org/sonar/test/File.rs")).isEqualTo(inputFile);
        assertThat(locator.getInputFile("./org//sonar/./test/File.rs")).isEqualTo(inputFile);
        assertThat(locator.getInputFile("./org//sonar/../sonar/test/File.rs")).isEqualTo(inputFile);
        assertThat(locator.getInputFile("///a/b/c.txt")).isNull();
    }
}

