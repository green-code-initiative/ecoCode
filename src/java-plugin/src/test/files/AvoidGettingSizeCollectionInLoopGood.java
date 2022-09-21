package fr.cnumr.java.checks;

class AvoidGettingSizeCollectionInLoopGood {
    AvoidGettingSizeCollectionInLoopGood(AvoidGettingSizeCollectionInLoopGood obj) {

    }

    public void goodLoop()
    {
        List<int> numberList = [10, 20, 30, 40, 50];

        int size = numberList.size();
        for (int i = 0; i < size; i++) {
            System.out.println(numberList[i]);
        }
    }
}