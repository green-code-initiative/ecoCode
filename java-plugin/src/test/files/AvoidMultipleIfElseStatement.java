package fr.greencodeinitiative.java.checks;

class AvoidMultipleIfElseStatementCheck {
    AvoidMultipleIfElseStatementCheck(AvoidMultipleIfElseStatementCheck mc) {
    }

    public void methodWithMultipleIfElseIf() {
        int nb1 = 0;
        int nb2 = 10;

        if (nb1 == 1) { // Noncompliant {{Using a switch statement instead of multiple if-else if possible}}
            nb1 = 1;
        } else if (nb1 == nb2) {
            //
        } else if (nb2 == nb1) {
            //
        } else {
            //
        }
        nb1 = nb2;
    }

    public void methodWithMultipleIfElse() {
        int nb1 = 0;
        int nb2 = 10;

        if (nb1 == 1) { // Noncompliant {{Using a switch statement instead of multiple if-else if possible}}
            nb1 = 1;
        } else {
            //
        }
        if (nb1 == 1) { // Noncompliant {{Using a switch statement instead of multiple if-else if possible}}
            nb1 = 1;
        } else {
            //
        }
    }
}