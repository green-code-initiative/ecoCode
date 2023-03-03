package fr.greencodeinitiative.java.checks;

import java.util.ArrayList;

class UnnecessarilyAssignValuesToVariablesTestCheckWithEmptyReturn {
    UnnecessarilyAssignValuesToVariablesTestCheckWithEmptyReturn(UnnecessarilyAssignValuesToVariablesTestCheckWithEmptyReturn mc) {
    }

    public void testSwitchCase() {

        ArrayList<String> lst = new ArrayList(0);
        if (lst == null) {
            return;
        }
        System.out.println(lst);
    }

}