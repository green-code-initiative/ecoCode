<?php

$sql1 = "SELECT * FROM"; // NOK {{Don't use the query SELECT * FROM}}
$sql2 = "SELECT DISTINCT * FROM"; // NOK {{Don't use the query SELECT * FROM}}
$sql3 = "SELECT name FROM";

class AvoidFullSQLRequest
{
	public function literalString() {
		OtherClass->SqlCall('SELECT * FROM'); // NOK {{Don't use the query SELECT * FROM}}
		OtherClass->SqlCall('SELECT DISTINCT * FROM'); // NOK {{Don't use the query SELECT * FROM}}
		OtherClass->SqlCall('SELECT name FROM');
	}

	public function passeAsVariable() {
        $sqlQuery1 = "SELECT * FROM"; // NOK {{Don't use the query SELECT * FROM}}
        $sqlQuery2 = "SELECT DISTINCT * FROM"; // NOK {{Don't use the query SELECT * FROM}}
        $sqlQuery3 = "SELECT name FROM";
		OtherClass->SqlCall($sqlQuery1);
		OtherClass->SqlCall($sqlQuery2);
		OtherClass->SqlCall($sqlQuery3);
	}
}