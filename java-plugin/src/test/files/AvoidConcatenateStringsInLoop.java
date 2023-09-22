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

public class AvoidConcatenateStringsInLoop {

    public String concatenateStrings(String[] strings) {
        String result1 = "";

        for (String string : strings) {
            result1 += string; // Noncompliant {{Don't concatenate Strings in loop, use StringBuilder instead.}}
        }
        return result1;
    }

    public String concatenateStrings2() {
        String result2 = "";

        for (int i = 0; i < 1000; ++i) {
            result2 += "another"; // Noncompliant {{Don't concatenate Strings in loop, use StringBuilder instead.}}
        }
        return result2;
    }

    public String concatenateStrings3() {
        String result3 = "";

        for (int i = 0; i < 1000; ++i) {
            result3 = result3 + "another"; // Noncompliant {{Don't concatenate Strings in loop, use StringBuilder instead.}}
        }
        return result3;
    }

}
