package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

class UseCorrectForLoopCheck {
    UseCorrectForLoopCheck(UseCorrectForLoopCheck mc) {
    }

    private final Integer[] intArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final List<Integer> intList = Arrays.asList(intArray);

    public void testForEachLoop() {
        int dummy = 0;
        for (Integer i : intArray) { // Noncompliant {{Avoid the use of Foreach with Arrays}}
            dummy += i;
        }

        for (Integer i : intList) {
            dummy += i;
        }
        System.out.println(dummy);
	}
}