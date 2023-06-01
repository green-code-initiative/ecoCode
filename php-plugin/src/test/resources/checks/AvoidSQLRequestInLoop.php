<?php

class AvoidFullSQLRequest
{
	private $dbUser = 'user';
	private $dbName = 'name';
	private $dbPass = 'pass';
	private $dbHost = 'host';
	private $query = 'SELECT * FROM Table';
	private $otherQuery = 'SELECT name FROM User';
	private $connection;

	public function launchSQLRequest($someCondition)
	{
		$expectedNbOfRequest = 5;
		$arrayOfQuery = array($this->query, $this->query, $this->query, $this->query, $this->query);
		$this->init();
		$this->noLoop();
		$this->forLoop($expectedNbOfRequest, $someCondition);
		$this->forEachLoop($arrayOfQuery, $someCondition);
		$this->whileLoop($expectedNbOfRequest, $someCondition);
	}
	private function init()
	{
		$this->connection = mysqli_connect($this->dbHost, $this->dbUser, $this->dbPass) or die("Unable to Connect to '$this->dbHost'");
		mysqli_select_db($this->connection, $this->dbName) or die("Could not open the db '$this->dbName'");
	}

	private function noLoop()
	{
		$result = mysqli_query($this->connection, $this->query);
		// display result or work with it
	}

	private function forLoop($expectedNbOfRequest, $someCondition)
	{
		for($index = 0; $expectedNbOfRequest > $index; ++$index){
			$result = mysqli_query($this->connection, $this->query); // NOK {{Avoid SQL request in loop}}
			// display result or work with it
			if($someCondition)
			{
				$result = mysqli_query($this->connection, $this->otherQuery); // NOK {{Avoid SQL request in loop}}
				$result = mysqli_query($this->connection, $this->otherQuery); // NOK {{Avoid SQL request in loop}}
			}
		}
	}

	private function forEachLoop($arrayOfQuery, $someCondition)
	{
		foreach($arrayOfQuery as $query){
			$result = mysqli_query($this->connection, $query); // NOK {{Avoid SQL request in loop}}
			// display result or work with it
			if($someCondition) {
                $result = mysqli_query($this->connection, $query); // NOK {{Avoid SQL request in loop}}
            }
		}
	}

	private function whileLoop($expectedNbOfRequest, $someCondition)
	{
		$nbOfRequest = 0;
		do {
			$result = mysqli_query($this->connection, $this->query); // NOK {{Avoid SQL request in loop}}
			// display result or work with it
			if($someCondition) {
                $result = mysqli_query($this->connection, $this->otherQuery); // NOK {{Avoid SQL request in loop}}
            }
			++$nbOfRequest;
		} while($expectedNbOfRequest > $nbOfRequest);
	}

	private function emptyLoop()
    {
	    for ($i = 1, $j = 0; $i <= 10; $j += $i, print $i, $i++);
	}
}