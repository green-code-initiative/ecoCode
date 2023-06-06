<?php
class AvoidMultipleIfElseStatement
{
    public function methodWithMultipleIfElseIf()
    {
        $nb1 = 0;
        $nb2 = 10;

        if ($nb1 == 1) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb2 = 1;
        } elseif ($nb1 == $nb2) {
            $nb2 = 2;
        } elseif ($nb2 == $nb1) {
            $nb2 = 3;
        } else {
            $nb2 = 4;
        }

        return $nb2;
    }

    public function methodWithMultipleIfElse()
    {
        $nb1 = 0;

        if ($nb1 == 1) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb1 = 1;
        } else {
            //
        }
        if ($nb1 == 1) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb1 = 1;
        } else {
            $nb1 = 2;
        }

        return $nb1;
    }

    public function methodWithOneIfElseIf()
    {
        $nb1 = 0;
        $nb2 = 10;

        if ($nb1 == 1) {
            $nb2 = 1;
        } elseif ($nb1 == $nb2) {
            $nb2 = 2;
        } else {
            $nb2 = 3;
        }

        return $nb2;
    }

    public function methodWithOneIfElse()
    {
        $nb1 = 0;

        if ($nb1 == 1) {
            $nb1 = 1;
        } else {
            $nb1 = 2;
        }

        return $nb1;
    }
}
