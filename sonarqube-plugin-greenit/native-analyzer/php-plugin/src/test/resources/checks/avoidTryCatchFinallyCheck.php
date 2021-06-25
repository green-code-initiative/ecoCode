
<?php

class SpecificException extends Exception {}

function test() {
    throw new SpecificException('Oopsie');
}

try // NOK
{
  $picture = PDF_open_image_file($PDF, "jpeg", $imgFile, "", 0); // This is the original statement, this works on PHP4
}
catch(Exception $ex)
{
  $msg = "Error opening $imgFile for Product $row['Identifier']";
  throw new Exception($msg);
}

try {// NOK
    throw new \Exception("Hello");
} catch(\Exception $e) {
    echo $e->getMessage()." catch in\n";
    throw $e;
} finally {
    echo $e->getMessage()." finally \n";
    throw new \Exception("Bye");
}
?>
