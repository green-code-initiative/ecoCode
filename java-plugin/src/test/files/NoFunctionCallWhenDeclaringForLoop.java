class NoFunctionCallWhenDeclaringForLoop {
    NoFunctionCallWhenDeclaringForLoop(NoFunctionCallWhenDeclaringForLoop mc) {
    }

    public int getMyValue() {
        return 6;
    }

    public int incrementeMyValue(int i) {
        return i + 100;
    }

    public void test1() {
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test2() {
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        for (String i : cars) {
            System.out.println(i);
        }

    }

    public void test3() {
        for (int i = getMyValue(); i < 20; i++) {  // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test4() {
        for (int i = 0; i < getMyValue(); i++) {  // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test5() {
        for (int i = 0; i < getMyValue(); incrementeMyValue(i)) {  // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

    public void test6() {
        for (int i = getMyValue(); i < getMyValue(); i++) { // Noncompliant {{Do not call a function when declaring a for-type loop}}
            System.out.println(i);
            boolean b = getMyValue() > 6;
        }
    }

}