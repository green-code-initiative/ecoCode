package fr.greencodeinitiative.java.checks;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

class AvoidGettingSizeCollectionInForLoopGood {
    AvoidGettingSizeCollectionInForLoopGood(AvoidGettingSizeCollectionInForLoopGood obj) {

    }

    public void goodForLoop() {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);

        int size = numberList.size();
        for (int i = 0; i < size; i++) { // Compliant
            System.out.println("numberList.size()");
            int size = numberList.size(); // Compliant with this rule
        }
    }
}