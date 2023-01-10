<?php

$sql1 = "SeLeCt * FrOm"; // NOK {{Don't use the query SELECT * FROM}}
$sql2 = "SeLeCt DiStInCt * FrOm"; // NOK {{Don't use the query SELECT * FROM}}
$sql3 = "select name from";

class AvoidFullSQLRequest
{
	public function LiteralString()
	{
		OtherClass->SqlCall("SeLeCt * FrOm table"); // NOK {{Don't use the query SELECT * FROM}}
		OtherClass->SqlCall("SeLeCt DiStInCt * FrOm table"); // NOK {{Don't use the query SELECT * FROM}}
		OtherClass->SqlCall("SeLeCt name FrOm table");
	}

	public function PasseAsVariable()
	{
		$sqlQuery1 = "SeLeCt * FrOm table"; // NOK {{Don't use the query SELECT * FROM}}
		$sqlQuery2 = "SeLeCt DiStInCt * FrOm table"; // NOK {{Don't use the query SELECT * FROM}}
		$sqlQuery3 = "SeLeCt name FrOm table";
		OtherClass->SqlCall($sqlQuery1);
		OtherClass->SqlCall($sqlQuery2);
	}
}