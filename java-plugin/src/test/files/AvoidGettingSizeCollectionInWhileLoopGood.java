package fr.greencodeinitiative.java.checks;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

class AvoidGettingSizeCollectionInWhileLoopGood {
    AvoidGettingSizeCollectionInWhileLoopGood(AvoidGettingSizeCollectionInWhileLoopGood obj) {

    }

    public void goodWhileLoop() {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);

        int size = numberList.size();
        int i = 0;
        while (i < size) { // Compliant
            System.out.println("numberList.size()");
            int size2 = numberList.size(); // Compliant with this rule
            i++;
        }
    }
}