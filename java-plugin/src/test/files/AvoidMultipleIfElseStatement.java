package fr.greencodeinitiative.java.checks;

class AvoidMultipleIfElseStatementCheck {

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
    public int shouldBeCompliantBecauseVariableUsedMaximumTwiceInComposedElseStatements()
    {
        int nb1 = 0;

        if (nb1 == 1) {
            nb1 = 2;
        } else {
            if (nb1 == 2) { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 1;
            }
        }

        return nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if a variable is not used max twice on several IF / ELSE statements
    // at the same level
    public int shouldBeNotCompliantBecauseVariablesUsedMaximumTwiceAndDifferentsVariablesUsed()
    {
        int nb1 = 0;
        int nb2 = 0;
        int nb3 = 0;

        if (nb3 == 1
                && nb3 == 2
                && nb3 == 3) { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
            nb1 = 1;
        } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
            nb2 = 2;
        }

        if (nb2 == 2) {
            nb1 = 3;
        } else {
            nb1 = 4;
        }

        return nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT COMPLIANT :
    // one variable is used maximum in two IF / ELSE / ELSEIF statements
    public int shouldBeNotCompliantBecauseVariablesIsUsedMoreThanTwice()
    {
        int nb1 = 0;

        if (nb1 == 1) {
            nb1 = 2;
        } else {
            nb1 = 3;
        }

        if (nb1 == 2) { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
            nb1 = 4;
        }

        return nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - same variable used maximum twice : no compliant because 2 IFs and 1 ELSE
    public int shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInIfStatementsAtDifferentsLevels()
    {
        int nb1 = 0;

        if (nb1 == 1) {
            if (nb1 == 2) {
                nb1 = 1;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 3;
            }
        } else {
            nb1 = 2;
        }

        return nb1;
    }


    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public int shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatements()
    {
        int nb1 = 0;

        if (nb1 == 1) {
            nb1 = 2;
        } else {
            if (nb1 == 2) {  // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 1;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 3;
            }
        }

        return nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public int shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatementsScenario2()
    {
        int nb1 = 0;

        if (nb1 == 1) {
            if (nb1 == 3) {
                nb1 = 4;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 5;
            }
        } else {
            if (nb1 == 2) { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 1;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 3;
            }
        }

        return nb1;
    }


    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public int shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatementsScenario3()
    {
        int nb1 = 0;
        int nb2 = 0;

        if (nb1 == 1) {
            if (nb1 == 3) {
                nb1 = 4;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 5;
            }
        } else if (nb2 == 2) {
            if (nb1 == 4) {
                nb1 = 5;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 6;
            }
        }

        return nb1;
    }

    // NON COMPLIANT
    // USE CASE : non compliant use case to check if following is NOT OK :
    // - two uses of the same variable : use thre times with 2 IFs and 1 ELSE
    // - usage of the same variable on different levels of IF statements
    public int shouldBeNotCompliantBecauseVariableUsedMoreThanTwiceInComposedElseStatementsScenario4()
    {
        int nb1 = 0;
        int nb2 = 0;

        if (nb1 == 1) {
            if (nb2 == 3) {
                nb1 = 4;
            } else {
                nb1 = 5;
            }
        } else if (nb2 == 2) {
            if (nb1 == 3) {
                nb1 = 4;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 5;
            }
        }

        return nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - the same variable must used maximum twice
    // - usage of the same variable on different levels of IF / ELSE statements
    public int shouldBeNotCompliantBecauseVariableUsedMaximumTwiceInComposedElseStatements()
    {
        int nb1 = 0;

        if (nb1 == 1) {
            nb1 = 2;
        } else {
            if (nb1 == 2) { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                nb1 = 1;
            } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                if (nb1 == 3) { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                    nb1 = 4;
                } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
                    nb1 = 5;
                }
            }
        }

        return nb1;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - more than twice uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public int shouldBeNotCompliantBecauseTheSameVariableIsUsedMoreThanTwice() // NOT Compliant
    {
        int nb1 = 0;
        int nb2 = 10;

        if (nb1 == 1) {
            nb2 = 1;
        } else if (nb1 == nb2) {
            nb2 = 2;
        } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
            nb2 = 4;
        }

        return nb2;
    }

    // NON COMPLIANT
    // USE CASE : NON compliant use case to check if following is NOT OK :
    // - more than twice uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public int shouldBeNotCompliantBecauseTheSameVariableIsUsedManyTimes() // NOT Compliant
    {
        int nb1 = 0;
        int nb2 = 10;
        int nb3 = 11;

        if (nb1 == 1) {
            nb2 = 1;
        } else if (nb1 == nb2) {
            nb2 = 2;
        } else if (nb3 == nb1) { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
            nb2 = 3;
        } else { // Noncompliant {{Use a switch statement instead of multiple if-else if possible}}
            nb2 = 4;
        }

        return nb2;
    }

}
