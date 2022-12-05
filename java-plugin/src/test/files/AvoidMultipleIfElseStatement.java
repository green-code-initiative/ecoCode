package fr.cnumr.java.checks;

class AvoidMultipleIfElseStatementCheck {
    AvoidMultipleIfElseStatementCheck (AvoidMultipleIfElseStatementCheck mc) {
    }

    public void methodWithMultipleIfElseIf() {
        int nb1 = 0;
        int nb2 = 10;

        if (nb1 == 1) { // Noncompliant
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

        if (nb1 == 1) { // Noncompliant
            nb1 = 1;
        } else {
            //
        }
        if (nb1 == 1) { // Noncompliant
            nb1 = 1;
        } else {
            //
        }
    }
}