/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2021 SonarQube Community
 *  
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
package org.sonar.plugins.groovy.foundation;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;

public class GroovyFileSystemTest {

  private DefaultFileSystem fileSystem;
  private GroovyFileSystem groovyFileSystem;

  @Before
  public void setUp() {
    fileSystem = new DefaultFileSystem(Paths.get("."));
    groovyFileSystem = new GroovyFileSystem(fileSystem);
  }

  @Test
  public void isEnabled() {
    assertThat(groovyFileSystem.hasGroovyFiles()).isFalse();

    fileSystem.add(TestInputFileBuilder.create("", "fake.file").build());
    assertThat(groovyFileSystem.hasGroovyFiles()).isFalse();

    fileSystem.add(TestInputFileBuilder.create("", "fake.groovy").setLanguage(Groovy.KEY).build());
    assertThat(groovyFileSystem.hasGroovyFiles()).isTrue();
  }

  @Test
  public void inputFileFromRelativePath() {
    assertThat(groovyFileSystem.sourceInputFileFromRelativePath(null)).isNull();

    fileSystem.add(TestInputFileBuilder.create("", "fake1.file").build());
    assertThat(groovyFileSystem.sourceInputFileFromRelativePath("fake1.file")).isNull();

    fileSystem.add(
        TestInputFileBuilder.create("", "fake2.file")
            .setType(Type.MAIN)
            .setLanguage(Groovy.KEY)
            .build());
    assertThat(groovyFileSystem.sourceInputFileFromRelativePath("fake2.file")).isNotNull();

    fileSystem.add(
        TestInputFileBuilder.create("", "org/sample/foo/fake3.file")
            .setType(Type.MAIN)
            .setLanguage(Groovy.KEY)
            .build());
    assertThat(groovyFileSystem.sourceInputFileFromRelativePath("foo/fake3.file")).isNotNull();
  }
}
