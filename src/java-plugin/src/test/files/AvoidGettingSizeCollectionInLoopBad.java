package fr.cnumr.java.checks;

class AvoidGettingSizeCollectionInLoopBad {
    AvoidGettingSizeCollectionInLoopBad(AvoidGettingSizeCollectionInLoopBad obj) {

    }

    public void badLoop()
    {
        Integer[] numbers = new Integer[] { 1, 2, 3 };
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(10);
        numberList.add(20);
        numberList.add(10);

        for (int i = 0; i < numberList.size(); i++) { // Noncompliant
            System.out.println(numberList[i]);
        }
    }
}