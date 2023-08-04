<?php
class AvoidMultipleIfElseStatement
{

    // functional RULES : please see HTML description file of this rule (resources directory)

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // COMPLIANT use cases
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // COMPLIANT
    // USE CASE : compliant use case to check if a variable is used maximum twice on several IF / ELSE statements
    // at the same level AND no problem with several IF staments at the same level using different variables
    public function shouldBeCompliantBecauseVariablesUsedMaximumTwiceAndDifferentsVariablesUsed()
    {
        $nb1 = 0;
        $nb2 = 0;
        $nb3 = 0;

        if ($nb3 != 1 && $nb1 > 1) {
            $nb1 = 1;
        } else {
            $nb2 = 2;
        }

        if ($nb2 == 2) {
            $nb1 = 3;
        } else {
            $nb1 = 4;
        }

        return $nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if a variable is used maximum twice on several IF / ELSE statements
    // at the same level AND no problem with several IF staments at the same level using different variables
    public function shouldBeCompliantBecauseVariablesUsedMaximumTwiceAndDifferentsVariablesUsedAtDifferentLevels()
    {
        $nb1 = 0;
        $nb2 = 0;
        $nb3 = 0;

        if ($nb1 < 1) {
            if ($nb2 == 2) {
                $nb1 = 3;
            } else {
                $nb1 = 4;
            }
        } else {
            $nb2 = 2;
        }

        if ($nb3 >= 1) {
            if ($nb2 == 2) {
                $nb1 = 3;
            } else {
                $nb1 = 4;
            }
        } else {
            $nb2 = 2;
        }

        return $nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if a variable is used maximum twice on several IF / ELSE statements
    // at the same level AND no problem with several IF staments at the same level using different variables
    public function shouldBeCompliantBecauseVariablesUsedMaximumTwiceAndDifferentsVariablesUsedAtDifferentLevelsScenario2()
    {
        $nb1 = 0;
        $nb2 = 0;
        $nb3 = 0;

        if ($nb1 <= 1) {
            if ($nb2 == 2) {
                if ($nb3 == 2) {
                    $nb1 = 3;
                } else {
                    $nb1 = 4;
                }
            } else {
                $nb1 = 4;
            }
        } else {
            $nb2 = 2;
        }

        if ($nb3 == 1) {
            if ($nb2 == 2) {
                $nb1 = 3;
            } else {
                $nb1 = 4;
            }
        } else {
            $nb2 = 2;
        }

        return $nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if one variable is used maximum twice in different IF statements
    public function shouldBeCompliantBecauseVariableUsedMaximumTwiceInIfStatements()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            $nb1 = 1;
        }

        if ($nb1 == 2) {
            $nb1 = 3;
        }

        return $nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if following is OK :
    // - two uses of the same variable
    // - usage of the same variable on different levels of IF statements
    public function shouldBeCompliantBecauseSereralVariablesUsedMaximumTwiceInComposedElseStatements()
    {
        $nb1 = 0;
        $nb2 = 0;
        $nb3 = 0;

        if ($nb1 == 1) {
            $nb2 = 2;
        } else {
            if ($nb2 == 2) {
                $nb1 = 1;
            } else {
                if ($nb3 == 4) {
                    $nb1 = 3;
                } else {
                    $nb1 = 6;
                }
            }
        }

        return $nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if following is OK :
    // - two uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public function shouldBeCompliantBecauseVariableUsedMaximumTwiceInIfOrElseIfStatements() // Compliant
    {
        $nb1 = 0;
        $nb2 = 10;

        if ($nb1 == 1) {
            $nb2 = 1;
        } elseif ($nb1 == $nb2) {
            $nb2 = 2;
        }

        return $nb2;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if following is OK :
    // - two uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public function shouldBeCompliantBecauseSeveralVariablesUsedMaximumTwiceInIfOrElseIfStatements() // Compliant
    {
        $nb1 = 0;
        $nb2 = 10;
        $nb3 = 3;
        $nb4 = 1;
        $nb5 = 2;

        if ($nb1 == 1) {
            $nb2 = 1;
        } elseif ($nb3 == $nb2) {
            $nb2 = 2;
        } elseif ($nb4 == $nb5) {
            $nb2 = 4;
        } else {
            $nb2 = 3;
        }

        return $nb2;
    }

//     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     //
//     // NON COMPLIANT use cases
//     //
//     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // NON COMPLIANT
    // USE CASE : Non compliant use case to check if following is NON OK :
    // - two uses of the same variable
    // - usage of the same variable on different levels of IF statements
    public function shouldBeCompliantBecauseVariableUsedMaximumTwiceInComposedElseStatements()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            $nb2 = 2;
        } else {
            if ($nb1 == 2) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb1 = 1;
            }
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if a variable is not used maximum twice on several IF / ELSE statements
    // at the same level
    public function shouldBeNotCompliantBecauseVariablesUsedMaximumTwiceAndDifferentsVariablesUsed()
    {
        $nb1 = 0;
        $nb2 = 0;
        $nb3 = 0;

        if ($nb3 == 1 && $nb3 == 2 && $nb3 == 3) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb1 = 1;
        } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb2 = 2;
        }

        if ($nb2 == 2) {
            $nb1 = 3;
        } else {
            $nb1 = 4;
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT COMPLIANT :
    // one variable is used maximum in two IF / ELSE / ELSEIF statements
    public function shouldBeNotCompliantBecauseVariablesIsUsedMoreThanTwice()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            $nb1 = 1;
        } else {
            $nb2 = 2;
        }

        if ($nb1 == 2) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb2 = 3;
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - same variable used maximum twice : no compliant because 2 IFs and 1 ELSE
    public function shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInIfStatementsAtDifferentsLevels()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            if ($nb1 == 2) {
                $nb1 = 1;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb2 = 3;
            }
        } else {
            $nb2 = 2;
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public function shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatements()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            $nb2 = 2;
        } else {
            if ($nb1 == 2) {  // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb1 = 1;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb2 = 3;
            }
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public function shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatementsScenario2()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            if ($nb1 == 3) {
                $nb1 = 4;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb2 = 5;
            }
        } else {
            if ($nb1 == 2) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb1 = 1;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb2 = 3;
            }
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public function shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatementsScenario3()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            if ($nb1 == 3) {
                $nb1 = 4;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb2 = 5;
            }
        } elseif ($nb2 == 2) {
            if ($nb1 == 3) {
                $nb1 = 4;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb2 = 5;
            }
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public function shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatementsScenario4()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            if ($nb2 == 3) {
                $nb1 = 4;
            } else {
                $nb1 = 5;
            }
        } elseif ($nb2 == 2) {
            if ($nb1 == 3) {
                $nb1 = 4;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb2 = 5;
            }
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - the same variable must used maximum twice
    // - usage of the same variable on different levels of IF / ELSE statements
    public function shouldBeNotCompliantBecauseVariableUsedMaximumTwiceInComposedElseStatements()
    {
        $nb1 = 0;
        $nb2 = 0;

        if ($nb1 == 1) {
            $nb2 = 2;
        } else {
            if ($nb1 == 2) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                $nb1 = 1;
            } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                if ($nb1 == 3) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                    $nb1 = 4;
                } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
                    $nb2 = 5;
                }
            }
        }

        return $nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - more than twice uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public function shouldBeNotCompliantBecauseTheSameVariableIsUsedMoreThanTwice() // NOT Compliant
    {
        $nb1 = 0;
        $nb2 = 10;
        $nb3 = 11;

        if ($nb1 == 1) {
            $nb2 = 1;
        } elseif ($nb1 == $nb2) {
            $nb2 = 2;
        } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb2 = 4;
        }

        return $nb2;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - more than twice uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public function shouldBeNotCompliantBecauseTheSameVariableIsUsedManyTimes() // NOT Compliant
    {
        $nb1 = 0;
        $nb2 = 10;
        $nb3 = 11;

        if ($nb1 == 1) {
            $nb2 = 1;
        } elseif ($nb1 == $nb2) {
            $nb2 = 2;
        } elseif ($nb3 == $nb1) { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb2 = 3;
        } else { // NOK {{Use a switch statement instead of multiple if-else if possible}}
            $nb2 = 4;
        }

        return $nb2;
    }

}
