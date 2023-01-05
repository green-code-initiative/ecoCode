
<?php

class SpecificException extends Exception {}

function test() {
    throw new SpecificException('Oopsie');
}

try // NOK {{Avoid using try-catch-finally}}
{
  $picture = PDF_open_image_file($PDF, "jpeg", $imgFile, "", 0); // This is the original statement, this works on PHP4
}
catch(Exception $ex)
{
  $msg = "Error opening $imgFile for Product $row['Identifier']";
  throw new Exception($msg);
}

try {// NOK {{Avoid using try-catch-finally}}
    throw new \Exception("Hello");
} catch(\Exception $e) {
    echo $e->getMessage()." catch in\n";
    throw $e;
} finally {
    echo $e->getMessage()." finally \n";
    throw new \Exception("Bye");
}
//FAILS with this RULE
/*try {
    throw new \Exception("Hello");
} catch(\Exception $e) {
    echo $e->getMessage()." catch in\n";
    throw $e;
}*/
?>
