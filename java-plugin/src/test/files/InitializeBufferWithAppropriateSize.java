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
package fr.greencodeinitiative.java.checks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class InitializeBufferWithAppropriateSize {
	InitializeBufferWithAppropriateSize(InitializeBufferWithAppropriateSize mc) {
	}

	public void testBufferCompliant() {
		StringBuffer stringBuffer = new StringBuffer(16);
	}

	public void testBufferCompliant2() {
		StringBuffer stringBuffer = new StringBuffer(Integer.valueOf(16));
	}

	public void testBufferNonCompliant() {
		StringBuffer stringBuffer = new StringBuffer(); // Noncompliant {{Initialize StringBuilder or StringBuffer with appropriate size}}
	}

	public void testBuilderCompliant() {
		StringBuilder stringBuilder = new StringBuilder(16);
	}

	public void testBuilderNonCompliant() {
		StringBuilder stringBuilder = new StringBuilder(); // Noncompliant {{Initialize StringBuilder or StringBuffer with appropriate size}}
	}
}