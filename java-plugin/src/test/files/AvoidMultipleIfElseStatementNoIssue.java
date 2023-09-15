package fr.greencodeinitiative.java.checks;

class AvoidMultipleIfElseStatementCheckNoIssue {

    // inital RULES : please see HTML description file of this rule (resources directory)

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
    public int shouldBeCompliantBecauseVariablesUsedMaximumTwiceAndDifferentsVariablesUsed()
    {
        int nb1 = 0;
        int nb2 = 0;
        int nb3 = 0;

        if (nb3 != 1 && nb1 > 1) {
            nb1 = 1;
        } else {
            nb2 = 2;
        }

        if (nb2 == 2) {
            nb1 = 3;
        } else {
            nb1 = 4;
        }

        return nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if a variable is used maximum twice on several IF / ELSE statements
    // at the same level AND no problem with several IF staments at the same level using different variables
    public int shouldBeCompliantBecauseVariablesUsedMaximumTwiceAndDifferentsVariablesUsedAtDiffLevels()
    {
        int nb1 = 0;
        int nb2 = 0;
        int nb3 = 0;

        if (nb1 < 1) {
            if (nb2 == 2) {
                nb3 = 3;
            } else {
                nb3 = 4;
            }
        } else {
            nb2 = 2;
        }

        if (nb3 >= 1) {
            if (nb2 == 2) {
                nb1 = 3;
            } else {
                nb1 = 4;
            }
        } else {
            nb1 = 2;
        }

        return nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if a variable is used maximum twice on several IF / ELSE statements
    // at the same level AND no problem with several IF staments at the same level using different variables
    public int shouldBeCompliantBecauseVariablesUsedMaximumTwiceAndDiffVariablesUsedAtDiffLevelsScenario2()
    {
        int nb1 = 0;
        int nb2 = 0;
        int nb3 = 0;

        if (nb1 <= 1) {
            if (nb2 == 2) {
                if (nb3 == 2) {
                    nb3 = 3;
                } else {
                    nb3 = 4;
                }
            } else {
                nb3 = 4;
            }
        } else {
            nb2 = 2;
        }

        if (nb3 == 1) {
            if (nb2 == 2) {
                nb1 = 3;
            } else {
                nb1 = 4;
            }
        } else {
            nb1 = 2;
        }

        return nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if one variable is used maximum twice in different IF statements
    public int shouldBeCompliantBecauseVariableUsedMaximumTwiceInIfStatements()
    {
        int nb1 = 0;

        if (nb1 == 1) {
            nb1 = 1;
        }

        if (nb1 == 2) {
            nb1 = 3;
        }

        return nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if following is OK :
    // - two uses of the same variable
    // - usage of the same variable on different levels of IF statements
    public int shouldBeCompliantBecauseSereralVariablesUsedMaximumTwiceInComposedElseStatements()
    {
        int nb1 = 0;
        int nb2 = 0;
        int nb3 = 0;

        if (nb1 == 1) {
            nb1 = 2;
        } else {
            if (nb2 == 2) {
                nb1 = 1;
            } else {
                if (nb3 == 4) {
                    nb1 = 3;
                } else {
                    nb1 = 6;
                }
            }
        }

        return nb1;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if following is OK :
    // - two uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public int shouldBeCompliantBecauseVariableUsedMaximumTwiceInIfOrElseIfStatements() // Compliant
    {
        int nb1 = 0;
        int nb2 = 10;

        if (nb1 == 1) {
            nb2 = 1;
        } else if (nb1 == nb2) {
            nb2 = 2;
        }

        return nb2;
    }

    // COMPLIANT
    // USE CASE : compliant use case to check if following is OK :
    // - two uses of the same variable
    // - usage of the same variable on different kind of test statements (IF and ELSEIF)
    public int shouldBeCompliantBecauseSeveralVariablesUsedMaximumTwiceInIfOrElseIfStatements() // Compliant
    {
        int nb1 = 0;
        int nb2 = 10;
        int nb3 = 3;
        int nb4 = 1;
        int nb5 = 2;

        if (nb1 == 1) {
            nb2 = 1;
        } else if (nb3 == nb2) {
            nb2 = 2;
        } else if (nb4 == nb5) {
            nb2 = 4;
        } else {
            nb2 = 3;
        }

        return nb2;
    }

}
