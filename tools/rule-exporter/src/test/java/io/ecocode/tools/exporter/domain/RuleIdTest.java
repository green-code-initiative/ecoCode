/*
 * ecoCode Rule Exporter - Export all rules to JSON files usable by the website
 * Copyright © 2024 Green Code Initiative (https://www.ecocode.io)
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
package io.ecocode.tools.exporter.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class RuleIdTest {

    @Test
    void valid() {
        RuleId rule1 = new RuleId("EC1");
        RuleId rule956 = new RuleId("EC956");

        assertThat(rule1.toString()).isEqualTo("EC1");
        assertThat(rule956.toString()).isEqualTo("EC956");
    }

    @Test
    void invalid() {
        try {
            new RuleId("EC");
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid rule id: EC");
        }

        try {
            new RuleId("INVALID");
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid rule id: INVALID");
        }
    }

    @Test
    void equals() {
        RuleId rule1 = new RuleId("EC1");
        assertThat(rule1).isEqualTo(new RuleId("EC1"));
        assertThat(rule1).isNotEqualTo(new RuleId("EC956"));
    }

    @Test
    void _hashCode() {
        assertThat(new RuleId("EC1").hashCode()).isEqualTo(new RuleId("EC1").hashCode());
    }

    @Test
    void comparison() {
        assertThat(new RuleId("EC5").compareTo(new RuleId("EC6"))).isLessThan(0);
        assertThat(new RuleId("EC5").compareTo(new RuleId("EC5"))).isZero();
        assertThat(new RuleId("EC6").compareTo(new RuleId("EC5"))).isGreaterThan(0);
    }

}
