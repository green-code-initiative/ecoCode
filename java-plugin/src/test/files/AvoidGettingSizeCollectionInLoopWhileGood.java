package fr.cnumr.java.checks;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

class AvoidGettingSizeCollectionInLoopWhileBad {
    AvoidGettingSizeCollectionInLoopBad() {

    }

    public void badLoop()
    {
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);
        int i = 0;
        int size = numberList.size();
        while (i < size) { // compliant
            System.out.println("numberList.size()");
            i++;
        }
    }
}