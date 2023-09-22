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

import java.io.*;

class FreeResourcesOfAutoCloseableInterface {
    FreeResourcesOfAutoCloseableInterface(FreeResourcesOfAutoCloseableInterface mc) {

    }

    public void foo1() {
        String fileName = "./FreeResourcesOfAutoCloseableInterface.java";
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void foo2() {
        String fileName = "./FreeResourcesOfAutoCloseableInterface.java";
        try { // Noncompliant
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            System.out.printl(br.readLine());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (fr) {
                fr.close();
            }
            if (br) {
                br.close();
            }
        }
    }
}