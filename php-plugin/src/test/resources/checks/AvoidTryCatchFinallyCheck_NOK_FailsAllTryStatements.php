<?php

class SpecificException extends Exception
{
}

function test()
{
    throw new SpecificException('Oopsie');
}

try { // NOK {{Avoid using try-catch}}
    $file = 'file';
    $picture = PDF_open_image_file(
        pdf_new(),
        "jpeg",
        $file,
        "",
        0
    ); // This is the original statement, this works on PHP4
} catch (Exception $e) {
    echo "Error opening $file : " . $e->getMessage();
}

try { // NOK {{Avoid using try-catch}}
    throw new SpecificException("Hello");
} catch (SpecificException $e) {
    echo $e->getMessage() . " catch in\n";
} finally {
    echo $e->getMessage() . " finally \n";
}

try { // NOK {{Avoid using try-catch}}
    throw new \Exception("Hello");
} catch (\Exception $e) {
    echo $e->getMessage() . " catch in\n";
}
