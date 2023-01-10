
<?php
/* exemple 1 */

for ($i = 1; $i <= 10; $i++) {
    echo $i;
}

/* exemple 2 */

for ($i = 1; ; $i++) {
    if ($i > 10) {
        break;
    }
    echo $i;
}

/* exemple 3 */

$i = 1;
for (; ; ) {
    if ($i > 10) {
        break;
    }
    echo $i;
    $i++;
}

/* exemple 4 */

for ($i = 1, $j = 0; $i <= 10; $j += $i, print $i, $i++); // NOK {{Do not call a function in for-type loop declaration}}


function somewhat_calcMax()
{
    return 500;
}


for ($i = 0; $i <= somewhat_calcMax(); $i++) { // NOK {{Do not call a function in for-type loop declaration}}
  somewhat_doSomethingWith($i);
}

$maxI = somewhat_calcMax();
for ($i = 0; $i <= $maxI; $i++) {
  somewhat_doSomethingWith($i);
}

?>
