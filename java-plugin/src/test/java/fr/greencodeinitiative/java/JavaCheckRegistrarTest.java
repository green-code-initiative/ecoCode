/*
 * Copyright (C) 2023 Green Code Initiative
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
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.greencodeinitiative.java;

import org.junit.jupiter.api.Test;
import org.sonar.plugins.java.api.CheckRegistrar;

import static org.assertj.core.api.Assertions.assertThat;

class JavaCheckRegistrarTest {

    @Test
    void checkNumberRules() {
        final CheckRegistrar.RegistrarContext context = new CheckRegistrar.RegistrarContext();

        final JavaCheckRegistrar registrar = new JavaCheckRegistrar();
        registrar.register(context);

        assertThat(context.checkClasses()).hasSize(19);
        assertThat(context.testCheckClasses()).isEmpty();
    }

}
