<?php
$a = 1;
$b = 2;
function somme() { // NOK {{Prefer local variables to globals}}
    $GLOBALS['b'] = $GLOBALS['a'] + $GLOBALS['b'];
}
somme();
echo $b;

function somme2() { // NOK {{Prefer local variables to globals}}
    global $a, $b;
    $b = $a + $b;
}
somme2();
echo $b;
?>
