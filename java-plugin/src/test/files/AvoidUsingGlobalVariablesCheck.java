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
public class Openclass {
    public static double price = 15.24; // Noncompliant {{Avoid using global variables}}
    public static long pages = 1053; // Noncompliant {{Avoid using global variables}}

    public static void main(String[] args) {
        double newPrice = Openclass.price;
        long newPages = Openclass.pages;
        System.out.println(newPrice);
        System.out.println(newPages);
        static long years = 3000; // Noncompliant {{Avoid using global variables}}
    }
    static{ // Noncompliant {{Avoid using global variables}}
        int a = 4;
    }

    public void printingA() {
        System.out.println(a);
    }

}