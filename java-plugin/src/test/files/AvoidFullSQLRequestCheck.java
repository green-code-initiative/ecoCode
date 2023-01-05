package fr.cnumr.java.checks;

import java.util.regex.Pattern;

class AvoidFullSQLRequestCheck {
	AvoidFullSQLRequestCheck(AvoidFullSQLRequestCheck mc) {
    }

	   public void literalSQLrequest() {
		   dummyCall("   sElEcT * fRoM myTable"); // Noncompliant
		   dummyCall("   sElEcT user fRoM myTable"); 

		   dummyCall("SELECTABLE 2*2 FROMAGE"); //not sql
		   dummyCall("SELECT     *FROM table"); // Noncompliant
	   }
	   

	   public void variableSQLrequest() {
		   String requestNonCompiliant = "   SeLeCt * FrOm myTable"; // Noncompliant
		   String requestCompiliant = "   SeLeCt user FrOm myTable"; 
		   dummyCall(requestNonCompiliant); 
		   dummyCall(requestCompiliant); 
		   
		   String noSqlCompiliant = "SELECTABLE 2*2 FROMAGE"; //not sql
		   String requestNonCompiliant_nSpace = "SELECT   *FROM table"; // Noncompliant
	   }
	   
   private void dummyCall (String request) {
	   
   }
   
}