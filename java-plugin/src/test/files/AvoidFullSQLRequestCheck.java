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