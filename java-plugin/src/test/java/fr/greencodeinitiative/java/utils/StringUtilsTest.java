/*
 * ecoCode - Java language - Provides rules to reduce the environmental footprint of your Java programs
 * Copyright © 2023 Green Code Initiative (https://www.ecocode.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.greencodeinitiative.java.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void spaces() {
        assertThat(StringUtils.spaces(5))
                .hasSize(5)
                .containsOnlyWhitespaces();
    }

    @Test
    void isNotEmpty() {
        assertThat(StringUtils.isNotEmpty(null)).isFalse();
        assertThat(StringUtils.isNotEmpty("")).isFalse();
        assertThat(StringUtils.isNotEmpty(" ")).isTrue();
        assertThat(StringUtils.isNotEmpty("bob")).isTrue();
        assertThat(StringUtils.isNotEmpty("   bob   ")).isTrue();
    }

}
