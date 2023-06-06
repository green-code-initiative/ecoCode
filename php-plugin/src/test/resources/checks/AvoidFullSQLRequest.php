<?php

$sql1 = 'SELECT * FROM'; // NOK {{Don't use the query SELECT * FROM}}
$sql2 = 'SeLeCt DiStInCt * FrOm'; // NOK {{Don't use the query SELECT * FROM}}
$sql3 = 'select name from';

class AvoidFullSQLRequest
{
    public function literalString()
    {
        OtherClass->SqlCall('SELECT * FROM'); // NOK {{Don't use the query SELECT * FROM}}
        OtherClass->SqlCall('SeLeCt DiStInCt * FrOm'); // NOK {{Don't use the query SELECT * FROM}}
        OtherClass->SqlCall('select name from');
    }

    public function passeAsVariable()
    {
        $sqlQuery1 = 'SELECT * FROM'; // NOK {{Don't use the query SELECT * FROM}}
        $sqlQuery2 = 'SeLeCt DiStInCt * FrOm'; // NOK {{Don't use the query SELECT * FROM}}
        $sqlQuery3 = 'select name from';
        OtherClass->SqlCall($sqlQuery1);
        OtherClass->SqlCall($sqlQuery2);
        OtherClass->SqlCall($sqlQuery3);
    }
}
