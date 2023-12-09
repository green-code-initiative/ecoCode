/*
 * ecoCode - Java language - Provides rules to reduce the environmental footprint of your Java programs
 * Copyright © 2023 Green Code Initiative (https://www.ecocode.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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

    // COMPLIANT
    // USE CASE : Compliant use case to check if following is OK :
    // - usage of the same variable on different levels of IF statements but with incompatible type for a switch
    public float shouldBeCompliantBecauseVariableHasNotCompatibleTypeFloatForSwitch()
    {
        float nb1 = 0.0f;

        if (nb1 > 1) {
            nb1 = 2.1f;
        } else {
            if (nb1 > 2) {
                nb1 = 1.1f;
            }
        }

        return nb1;
    }

    // COMPLIANT
    // USE CASE : Compliant use case to check if following is OK :
    // - usage of the same variable on different levels of IF statements but with incompatible type for a switch
    public double shouldBeCompliantBecauseVariableHasNotCompatibleTypeDoubleForSwitch()
    {
        double nb1 = 0.0;

        if (nb1 > 1) {
            nb1 = 2.1;
        } else {
            if (nb1 > 2) {
                nb1 = 1.1;
            }
        }

        return nb1;
    }

    // COMPLIANT
    // USE CASE : Compliant use case to check if following is OK :
    // - usage of the same variable on different levels of IF statements but with instanceof keys
    // - with a variable used 4 times
    public int shouldBeCompliantBecauseVariableUsed4TimesWithInstanceOfKeys()
    {
        int nb1 = 0;
        Object obj = new Object();

        if (obj instanceof String) {
            nb1 = 1;
        } else {
            if (obj instanceof Integer) {
                nb1 = 2;
            } else {
                if (obj instanceof Double) {
                    nb1 = 3;
                } else {
                    nb1 = 4;
                }
            }
        }

        return nb1;
    }


}
