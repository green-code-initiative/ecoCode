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
class MyClass {
    MyClass(MyClass mc) {
    }

    int foo1() {
        int counter = 0;
        return counter++; // Noncompliant {{Use ++i instead of i++}}
    }

    int foo11() {
        int counter = 0;
        return ++counter;
    }

    void foo2(int value) {
        int counter = 0;
        counter++; // Noncompliant {{Use ++i instead of i++}}
    }

    void foo22(int value) {
        int counter = 0;
        ++counter;
    }

    void foo3(int value) {
        int counter = 0;
        counter = counter + 197845 ;
    }

    void foo4(int value) {
        int counter =0;
        counter = counter + 35 + 78 ;
    }

    void foo50(int value) {
        for (int i=0; i < 10; i++) { // Noncompliant {{Use ++i instead of i++}}
            System.out.println(i);
        }
    }

    void foo51(int value) {
        for (int i=0; i < 10; ++i) {
            System.out.println(i);
        }
    }
}