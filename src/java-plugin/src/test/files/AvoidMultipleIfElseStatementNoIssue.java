package fr.cnumr.java.checks;

class AvoidMultipleIfElseStatementNoIssueCheck {
    AvoidMultipleIfElseStatementNoIssueCheck (AvoidMultipleIfElseStatementNoIssueCheck mc) {
    }

    public void methodWithOneIfElseIf() {
        int nb1 = 0;
        int nb2 = 10;

        if (nb1 == 1) {
            nb1 = 1;
        } else if (nb1 == nb2) {
            //
        } else {
            //
        }
        nb1 = nb2;
    }

    public void methodWithOneIfElse() {
        int nb1 = 0;
        int nb2 = 10;

        if (nb1 == 1) {
            nb1 = 1;
        } else {
            //
        }
    }
}