<?php

class AvoidFullSQLRequest
{
	private $DbUser = 'user';
	private $DbName = 'name';
	private $DbPass = 'pass';
	private $DbHost = 'host';
	private $Query = 'SELECT * FROM Table';
	private $OtherQuery = 'SELECT name FROM User';
	private $Connection;

	public function LaunchSQLRequest($someCondition)
	{
		$expectedNbOfRequest = 5;
		$arrayOfQuery = array($Query,$Query,$Query,$Query,$Query);
		$this->Init();
		$this->NoLoop();
		$this->ForLoop($expectedNbOfRequest, $someCondition);
		$this->ForEachLoop($arrayOfQuery, $someCondition);
		$this->WhileLoop($expectedNbOfRequest, $someCondition);
	}
	private function Init()
	{
		$this->Connection = mysql_connect($dbhost, $dbuser, $dbpass) or die("Unable to Connect to '$dbhost'");
		mysql_select_db($dbname) or die("Could not open the db '$dbname'");
	}

	private function NoLoop()
	{
		$result = mysql_query($this->Query);
		// display result or work with it
	}

	private function ForLoop($expectedNbOfRequest, $someCondition)
	{
		for($index = 0; $expectedNbOfRequest > $index; ++$index){
			$result = mysql_query($this->Query); // NOK {{Avoid SQL request in loop}}
			// display result or work with it
			if($someCondition)
			{
				$result = mysql_query($OtherQuery); // NOK {{Avoid SQL request in loop}}
				$result = mysql_query($OtherQuery); // NOK {{Avoid SQL request in loop}}
			}
		}
	}

	private function ForEachLoop($arrayOfQuery, $someCondition)
	{
		foreach($arrayOfQuery as $query){
			$result = mysql_query($Query); // NOK {{Avoid SQL request in loop}}
			// display result or work with it
			if($someCondition)
				$result = mysql_query($OtherQuery); // NOK {{Avoid SQL request in loop}}
		}
	}

	private function WhileLoop($expectedNbOfRequest, $someCondition)
	{
		$nbOfRequest = 0;
		do{
			$result = mysql_query($this->Query); // NOK {{Avoid SQL request in loop}}
			// display result or work with it
			if($someCondition)
				$result = mysql_query($OtherQuery); // NOK {{Avoid SQL request in loop}}
			++$nbOfRequest;
		}while($expectedNbOfRequest > $nbOfRequest);
	}

	private function EmptyLoop()
    {
	    for ($i = 1, $j = 0; $i <= 10; $j += $i, print $i, $i++);
	}
}