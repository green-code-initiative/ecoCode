package fr.greencodeinitiative.java.checks;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

class AvoidGettingSizeCollectionInForEachLoopIgnored {
    AvoidGettingSizeCollectionInForEachLoopIgnored(AvoidGettingSizeCollectionInForEachLoopIgnored obj) {

    }

    public void ignoredLoop() {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);

        for (Integer i : numberList) { // Ignored
            int size = numberList.size(); // Compliant with this rule
            System.out.println("numberList.size()");
        }
    }
}