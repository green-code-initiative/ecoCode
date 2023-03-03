package fr.greencodeinitiative.java.checks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class AvoidGettingSizeCollectionInWhileLoopIgnored {
    AvoidGettingSizeCollectionInWhileLoopIgnored() {

    }

    public void badWhileLoop() {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);

        Iterator it = numberList.iterator();
        int i = 0;
        while (it.hasNext()) { // Ignored => compliant
            it.next();
            System.out.println("numberList.size()");
        }
    }
}