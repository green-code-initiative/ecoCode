<?php

  require("avoidDoubleQuote.php"); // NOK {{Avoid using relative path, prefer using absolute path}}
  include("avoidDoubleQuote.php"); // NOK {{Avoid using relative path, prefer using absolute path}}

  include("../avoidDoubleQuote.php");// NOK {{Avoid using relative path, prefer using absolute path}}

  include("./avoidDoubleQuote.php");// NOK {{Avoid using relative path, prefer using absolute path}}

?>