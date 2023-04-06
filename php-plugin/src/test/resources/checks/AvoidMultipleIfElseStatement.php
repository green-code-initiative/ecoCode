<?php
class AvoidMultipleIfElseStatement{
    public function methodWithMultipleIfElseIf() {
        $nb1 = 0;
        $nb2 = 10;

        if ($nb1 == 1) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb1 = 1;
        } elseif ($nb1 == $nb2) {
            //
        } elseif ($nb2 == $nb1) {
            //
        } else {
            //
        }
        $nb1 = $nb2;
    }

    public function methodWithMultipleIfElse() {
        $nb1 = 0;
        $nb2 = 10;

        if ($nb1 == 1) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb1 = 1;
        } else {
            //
        }
        if ($nb1 == 1) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb1 = 1;
        } else {
            //
        }
    }
    
    public function methodWithOneIfElseIf() {
        $nb1 = 0;
        $nb2 = 10;

        if ($nb1 == 1) {
            $nb1 = 1;
        } elseif ($nb1 == $nb2) {
            //
        } else {
            //
        }
        $nb1 = $nb2;
    }

    public function methodWithOneIfElse() {
        $nb1 = 0;
        $nb2 = 10;

        if ($nb1 == 1) {
            $nb1 = 1;
        } else {
            //
        }
    }
}
?>