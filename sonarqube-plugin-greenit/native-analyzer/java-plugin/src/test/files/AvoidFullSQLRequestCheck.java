package fr.cnumr.java.checks;

class AvoidFullSQLRequestCheck {
	AvoidFullSQLRequestCheck(AvoidFullSQLRequestCheck mc) {
    }

	   public void literalSQLrequest() {
		   dummyCall("   sElEcT * fRoM myTable"); // Noncompliant
		   dummyCall("   sElEcT user fRoM myTable"); 
	   }
	   

	   public void variableSQLrequest() {
		   String requestNonCompiliant = "   SeLeCt * FrOm myTable"; // Noncompliant
		   String requestCompiliant = "   SeLeCt user FrOm myTable"; 
		   dummyCall(requestNonCompiliant); 
		   dummyCall(requestCompiliant); 
	   }
	   
   private void dummyCall (String request) {
	   
   }
}