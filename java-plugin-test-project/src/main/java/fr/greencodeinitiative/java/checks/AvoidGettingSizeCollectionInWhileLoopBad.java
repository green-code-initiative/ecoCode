package fr.greencodeinitiative.java.checks;

import java.util.ArrayList;
import java.util.List;

class AvoidGettingSizeCollectionInWhileLoopBad {
    AvoidGettingSizeCollectionInWhileLoopBad() {

    }

    public void badWhileLoop() {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);

        int i = 0;
        while (i < numberList.size()) { // Noncompliant
            System.out.println("numberList.size()");
            i++;
        }
    }
}