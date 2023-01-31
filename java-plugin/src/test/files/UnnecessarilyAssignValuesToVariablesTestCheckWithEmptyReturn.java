package fr.greencodeinitiative.java.checks;

class UnnecessarilyAssignValuesToVariablesTestCheck {
    UnnecessarilyAssignValuesToVariablesTestCheck(UnnecessarilyAssignValuesToVariablesTestCheck mc) {
    }

    public void testSwitchCase() {

        ArrayList<String> lst = new ArrayList(0);
        if (lst == null) {
            return;
        }
        System.out.println(lst);
    }

}