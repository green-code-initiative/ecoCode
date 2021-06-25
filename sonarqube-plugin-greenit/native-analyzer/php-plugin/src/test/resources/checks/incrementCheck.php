<?php


class Obj {
    function foo() {
        $counter = 0;
        return $counter++; // NOK
    }

    function bar() {
        $counter = 0;
        return ++$counter;
    }
};

?>