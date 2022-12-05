package fr.cnumr.java.checks;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
class AvoidGettingSizeCollectionInLoopGood {
    AvoidGettingSizeCollectionInLoopGood(AvoidGettingSizeCollectionInLoopGood obj) {

    }

    public void goodLoop()
    {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);

        int size = numberList.size();
        for (int i = 0; i < size; i++) { // Compliant
            System.out.println("numberList.size()");
        }
    }
}