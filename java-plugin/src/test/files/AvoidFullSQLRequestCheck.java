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

import java.util.regex.Pattern;

class AvoidFullSQLRequestCheck {
    AvoidFullSQLRequestCheck(AvoidFullSQLRequestCheck mc) {
    }

    public void literalSQLrequest() {
        dummyCall("   sElEcT * fRoM myTable"); // Noncompliant {{Don't use the query SELECT * FROM}}
        dummyCall("   sElEcT user fRoM myTable");

        dummyCall("SELECTABLE 2*2 FROMAGE"); //not sql
        dummyCall("SELECT     *FROM table"); // Noncompliant {{Don't use the query SELECT * FROM}}
    }


    public void variableSQLrequest() {
        String requestNonCompiliant = "   SeLeCt * FrOm myTable"; // Noncompliant {{Don't use the query SELECT * FROM}}
        String requestCompiliant = "   SeLeCt user FrOm myTable";
        dummyCall(requestNonCompiliant);
        dummyCall(requestCompiliant);

        String noSqlCompiliant = "SELECTABLE 2*2 FROMAGE"; //not sql
        String requestNonCompiliant_nSpace = "SELECT   *FROM table"; // Noncompliant {{Don't use the query SELECT * FROM}}
    }

    private void dummyCall(String request) {

    }
   
}