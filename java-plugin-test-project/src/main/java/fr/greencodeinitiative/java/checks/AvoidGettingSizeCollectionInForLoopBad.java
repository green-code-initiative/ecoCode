package fr.greencodeinitiative.java.checks;

import java.util.ArrayList;
import java.util.List;

class AvoidGettingSizeCollectionInForLoopBad {
    AvoidGettingSizeCollectionInForLoopBad() {

    }

    public void badForLoop() {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);

        for (int i = 0; i < numberList.size(); i++) { // Noncompliant
            System.out.println("numberList.size()");
        }
    }
}