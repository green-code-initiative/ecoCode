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
package org.sonar.plugins.groovy;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.PropertyDefinitions;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.utils.System2;
import org.sonar.plugins.groovy.jacoco.JaCoCoConfiguration;

public final class TestUtils {

  private TestUtils() {}

  /**
   * Search for a test resource in the classpath. For example
   * getResource("org/sonar/MyClass/foo.txt");
   *
   * @param path the starting slash is optional
   * @return the resource. Null if resource not found
   */
  public static File getResource(String path) {
    String resourcePath = path;
    if (!resourcePath.startsWith("/")) {
      resourcePath = "/" + resourcePath;
    }
    URL url = TestUtils.class.getResource(resourcePath);
    if (url != null) {
      return FileUtils.toFile(url);
    }
    return null;
  }

  /**
   * Search for a resource in the classpath. For example calling the method getResource(getClass(),
   * "myTestName/foo.txt") from the class org.sonar.Foo loads the file
   * $basedir/src/test/resources/org/sonar/Foo/myTestName/foo.txt
   *
   * @return the resource. Null if resource not found
   */
  public static Path getResource(Class<?> baseClass, String path) {
    String resourcePath = StringUtils.replaceChars(baseClass.getCanonicalName(), '.', '/');
    if (!path.startsWith("/")) {
      resourcePath += "/";
    }
    resourcePath += path;
    return getResource(resourcePath).toPath();
  }

  public static MapSettings jacocoDefaultSettings() {
    PropertyDefinitions prop = null;
    try {
      // Legacy declaration, doesn't compile on modern SonarQube
      Constructor<PropertyDefinitions> c =
          PropertyDefinitions.class.getConstructor(Collection.class);
      prop = c.newInstance(JaCoCoConfiguration.getPropertyDefinitions());
    } catch (ReflectiveOperationException | IllegalArgumentException e) {
      prop =
          new PropertyDefinitions(System2.INSTANCE, JaCoCoConfiguration.getPropertyDefinitions());
    }
    return new MapSettings(prop);
  }
}
