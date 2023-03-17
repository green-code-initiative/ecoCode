<?php

min(4, 2); // NOK {{Use of methods for basic operations}}
minWithBasic(4, 2);

minWithAutoImplement(4,2);

class Obj {
    function minWithBasic($a, $b) {
        return min($a, $b); // NOK {{Use of methods for basic operations}}
    }

    function minWithAutoImplement($a, $b) {
        return ($a < $b) ? $a : $b;
    }
};

?>