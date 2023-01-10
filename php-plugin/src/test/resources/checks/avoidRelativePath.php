<?php

  require("avoidDoubleQuote.php"); // NOK {{Avoid using relative path, prefer using absolute path}}
  include("avoidDoubleQuote.php"); // NOK {{Avoid using relative path, prefer using absolute path}}
  include("../avoidDoubleQuote.php");// NOK {{Avoid using relative path, prefer using absolute path}}
  include "./avoidDoubleQuote.php" ;// NOK {{Avoid using relative path, prefer using absolute path}}
  require_once("avoidDoubleQuote.php");// NOK {{Avoid using relative path, prefer using absolute path}}
  include_once("avoidDoubleQuote.php");// NOK {{Avoid using relative path, prefer using absolute path}}

  include "/avoidDoubleQuote.php";
  require_once("/avoidDoubleQuote.php");
  include( dirname(__FILE__) . "avoidDoubleQuote.php");
  include __DIR__ . "avoidDoubleQuote.php";

?>