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
        $this->emptyLoop();
    }
    private function init()
    {
        $this->connection = mysql_connect($this->dbHost, $this->dbUser, $this->dbPass) or die("Unable to Connect to '$dbhost'");
        mysql_select_db($this->dbName) or die("Could not open the db '$this->dbName'");
    }

    private function noLoop()
    {
        $result = mysql_query($this->query);
        echo $result;
    }

    private function forLoop($expectedNbOfRequest, $someCondition)
    {
        for ($index = 0; $expectedNbOfRequest > $index; ++$index) {
            $result = mysql_query($this->query); // NOK {{Avoid SQL request in loop}}

            if ($someCondition) {
                $result = mysql_query($this->otherQuery); // NOK {{Avoid SQL request in loop}}
            }

            echo $result;
        }
    }

    private function forEachLoop($arrayOfQuery, $someCondition)
    {
        foreach ($arrayOfQuery as $query) {
            $result = mysql_query($query); // NOK {{Avoid SQL request in loop}}

            if ($someCondition) {
                $result = mysql_query($this->otherQuery); // NOK {{Avoid SQL request in loop}}
            }

            echo $result;
        }
    }

    private function whileLoop($expectedNbOfRequest, $someCondition)
    {
        $nbOfRequest = 0;
        do {
            $result = mysql_query($this->query); // NOK {{Avoid SQL request in loop}}

            if($someCondition) {
                $result = mysql_query($this->otherQuery); // NOK {{Avoid SQL request in loop}}
            }

            echo $result;
            ++$nbOfRequest;
        } while ($expectedNbOfRequest > $nbOfRequest);
    }

    private function emptyLoop()
    {
        for ($i = 1, $j = 0; $i <= 10; $j += $i, print $i, $i++);
    }
}
