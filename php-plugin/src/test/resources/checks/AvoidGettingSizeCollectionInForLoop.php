<?php

$food = array('orange', 'banana', 'apple', 'carrot', 'collard', 'pea');

/**
 * FOR STATEMENTS
 */
 for ($i = 0; $i < count($food); $i++) {  // NOK {{Avoid getting the size of the collection in the loop}}
     var_dump($food[$i]);
 }

 $size = sizeof($food);
 for ($i = 0; $i < $size; $i++) {  // Compliant
     var_dump($food[$i]);
 }

 for ($i = 0; $i < sizeof($food); $i++) {  // NOK {{Avoid getting the size of the collection in the loop}}
     var_dump($food[$i]);
 }

 $size = count($food);
 for ($i = 0; $i < $size; $i++) {  // Compliant
     var_dump($food[$i]);
 }

/**
 * WHILE STATEMENTS
 */
$i = 0;
while($i < count($food)) // NOK {{Avoid getting the size of the collection in the loop}}
{
    var_dump($food[$i]);
    $i++;
}

$i = 0;
$size = count($food);
while($i < $size)
{
    var_dump($food[$i]);
    $i++;
}

$i = 0;
$size = sizeof($food);
while($i < $size)
{
    var_dump($food[$i]);
    $i++;
}

/**
 * DO WHILE STATEMENTS
 */
$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($food[$i]);
    $i++;
} while ($i < count($food));

$i = 0;
$size = count($food);
do {
    var_dump($food[$i]);
    $i++;
} while ($i < $size);

$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($food[$i]);
    $i++;
} while ($i < sizeof($food));

$i = 0;
$size = sizeof($food);
do {
    var_dump($food[$i]);
    $i++;
} while ($i < $size);