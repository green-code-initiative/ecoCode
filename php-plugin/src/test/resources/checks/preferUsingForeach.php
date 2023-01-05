<?php
    for ($x = 0; $x <= 100; $x+=10) {  // NOK {{Prefer using foreach}}
      echo "The number is: $x <br>";
    }

    $arr = array(1, 2, 3, 4);
    foreach ($arr as &$value) {
        $value = $value * 2;
    }
    // $arr vaut maintenant array(2, 4, 6, 8)
    unset($value); // Détruit la référence sur le dernier élément
?>