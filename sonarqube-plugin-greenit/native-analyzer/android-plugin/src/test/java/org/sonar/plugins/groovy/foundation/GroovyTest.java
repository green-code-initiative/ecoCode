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
package org.sonar.plugins.groovy.foundation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.sonar.api.config.internal.MapSettings;

public class GroovyTest {

  @Test
  public void test() {
    MapSettings settings = new MapSettings();
    Groovy language = new Groovy(settings.asConfig());
    assertThat(language.getKey()).isEqualTo("grvy");
    assertThat(language.getName()).isEqualTo("Groovy");
    assertThat(language.getFileSuffixes()).isEqualTo(new String[] {".groovy"});

    settings.setProperty(Groovy.FILE_SUFFIXES_KEY, "");
    assertThat(language.getFileSuffixes()).containsOnly(".groovy");

    settings.setProperty(Groovy.FILE_SUFFIXES_KEY, ".groovy, .grvy");
    assertThat(language.getFileSuffixes()).containsOnly(".groovy", ".grvy");
  }
}
