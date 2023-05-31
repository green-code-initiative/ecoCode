<?php

$array = array('orange', 'banana', 'apple', 'carrot', 'collard', 'pea');

/**
 * FOR STATEMENTS // RIGHT OPERAND
 */
for ($i = 0; $i < count($array); ++$i) {  // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
}

$size = count($array);
for ($i = 0; $i < $size; ++$i) {  // Compliant
    var_dump($array[$i]);
}

for ($i = 0; $i < sizeof($array); ++$i) {  // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
}

$size = sizeof($array);
for ($i = 0; $i < $size; ++$i) {  // Compliant
    var_dump($array[$i]);
}

for ($i = 0; $i < iterator_count(new ArrayIterator($array)); ++$i) {  // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
}

$size = iterator_count(new ArrayIterator($array));
for ($i = 0; $i < $size; ++$i) {  // Compliant
    var_dump($array[$i]);
}

/**
 * FOR STATEMENTS // LEFT OPERAND
 */
for ($i = 0; count($array) > $i; ++$i) {  // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
}

$size = count($array);
for ($i = 0; $size > $i; ++$i) {  // Compliant
    var_dump($array[$i]);
}

for ($i = 0; sizeof($array) > $i; ++$i) {  // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
}

$size = sizeof($array);
for ($i = 0; $size > $i; ++$i) {  // Compliant
    var_dump($array[$i]);
}

for ($i = 0; iterator_count(new ArrayIterator($array)) > $i; ++$i) {  // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
}

$size = iterator_count(new ArrayIterator($array));
for ($i = 0; $size > $i; ++$i) { // Compliant
    var_dump($array[$i]);
}

/**
 * WHILE STATEMENTS // RIGHT OPERAND
 */
$i = 0;
while ($i < count($array)) { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
$size = count($array);
while ($i < $size) { // Compliant
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
while ($i < sizeof($array)) { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
$size = sizeof($array);
while ($i < $size) { // Compliant
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
while ($i < iterator_count(new ArrayIterator($array))) { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
$size = iterator_count(new ArrayIterator($array));
while ($i < $size) { // Compliant
    var_dump($array[$i]);
    ++$i;
}

/**
 * WHILE STATEMENTS // LEFT OPERAND
 */
$i = 0;
while (count($array) > $i) { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
$size = count($array);
while ($size> $i) { // Compliant
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
while (sizeof($array) > $i) { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
$size = sizeof($array);
while ($size > $i) { // Compliant
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
while (iterator_count(new ArrayIterator($array)) > $i) { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
}

$i = 0;
$size = iterator_count(new ArrayIterator($array));
while ($size > $i) { // Compliant
    var_dump($array[$i]);
    ++$i;
}

/**
 * DO WHILE STATEMENTS // RIGHT OPERAND
 */
$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
} while ($i < count($array));

$i = 0;
$size = count($array);
do {
    var_dump($array[$i]);
    ++$i;
} while ($i < $size); // Compliant

$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
} while ($i < sizeof($array));

$i = 0;
$size = sizeof($array);
do {
    var_dump($array[$i]);
    ++$i;
} while ($i < $size); // Compliant

$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
} while ($i < iterator_count(new ArrayIterator($array)));

$i = 0;
$size = iterator_count(new ArrayIterator($array));
do {
    var_dump($array[$i]);
    ++$i;
} while ($i < $size); // Compliant

/**
 * DO WHILE STATEMENTS // LEFT OPERAND
 */
$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
} while (count($array) > $i);

$i = 0;
$size = count($array);
do {
    var_dump($array[$i]);
    ++$i;
} while ($size > $i); // Compliant

$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
} while (sizeof($array) > $i);

$i = 0;
$size = sizeof($array);
do {
    var_dump($array[$i]);
    ++$i;
} while ($size > $i); // Compliant

$i = 0;
do { // NOK {{Avoid getting the size of the collection in the loop}}
    var_dump($array[$i]);
    ++$i;
} while (iterator_count(new ArrayIterator($array)) > $i);

$i = 0;
$size = iterator_count(new ArrayIterator($array));
do {
    var_dump($array[$i]);
    ++$i;
} while ($size > $i); // Compliant
