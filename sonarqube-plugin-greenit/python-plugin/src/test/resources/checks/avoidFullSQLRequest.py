def displayMessage(argument1):
    print(argument) 

displayMessage('   sElEcT * fRoM myTable') # Noncompliant {{Don't use the query SELECT * FROM}}
displayMessage('   sElEcT user fRoM myTable')
  
requestNonCompiliant = '   SeLeCt * FrOm myTable' # Noncompliant {{Don't use the query SELECT * FROM}}
requestCompiliant = '   SeLeCt user FrOm myTable' 
displayMessage(requestNonCompiliant)
displayMessage(requestCompiliant)
	   

