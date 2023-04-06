/*
 * SonarQube Java
 * Copyright (C) 2012-2021 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
package fr.greencodeinitiative.java;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.sonar.plugins.java.api.CheckRegistrar;

class JavaCheckRegistrarTest {

    @Test
    void checkNumberRules() {
        final CheckRegistrar.RegistrarContext context = new CheckRegistrar.RegistrarContext();

        final JavaCheckRegistrar registrar = new JavaCheckRegistrar();
        registrar.register(context);

        assertThat(context.checkClasses()).hasSize(20);
        assertThat(context.testCheckClasses()).isEmpty();
    }
}
