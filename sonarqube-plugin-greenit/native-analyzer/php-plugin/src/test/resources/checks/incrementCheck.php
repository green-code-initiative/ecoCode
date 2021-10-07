<?php


class Obj {
    function foo() {
        $counter = 0;
        return $counter++; // NOK {{Remove the usage of $i++. prefer ++$i}}
    }

    function bar() {
        $counter = 0;
        return ++$counter;
    }
};

?>