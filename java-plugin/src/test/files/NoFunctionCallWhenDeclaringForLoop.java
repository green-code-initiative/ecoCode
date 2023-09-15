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
class NoFunctionCallWhenDeclaringForLoop {
    NoFunctionCallWhenDeclaringForLoop(NoFunctionCallWhenDeclaringForLoop mc) {
    }

    public int getMyValue() {
        return 6;
    }

    public int incrementeMyValue(int i) {
        return i + 100;
    }

    public void test1() {
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test2() {
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        for (String i : cars) {
            System.out.println(i);
        }

    }

    public void test3() {
        for (int i = getMyValue(); i < 20; i++) {  // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test4() {
        for (int i = 0; i < getMyValue(); i++) {  // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test5() {
        for (int i = 0; i < getMyValue(); incrementeMyValue(i)) {  // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test6() {
        for (int i = getMyValue(); i < getMyValue(); i++) { // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

}