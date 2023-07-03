class MyClass {
    MyClass(MyClass mc) {
    }

    int foo1() {
        int counter = 0;
        return counter++; // Noncompliant {{Use ++i instead of i++}}
    }

    int foo11() {
        int counter = 0;
        return ++counter;
    }

    void foo2(int value) {
        int counter = 0;
        counter++; // Noncompliant {{Use ++i instead of i++}}
    }

    void foo22(int value) {
        int counter = 0;
        ++counter;
    }

    void foo3(int value) {
        int counter = 0;
        counter = counter + 197845 ;
    }

    void foo4(int value) {
        int counter =0;
        counter = counter + 35 + 78 ;
    }

    void foo50(int value) {
        for (int i=0; i < 10; i++) { // Noncompliant {{Use ++i instead of i++}}
            System.out.println(i);
        }
    }

    void foo51(int value) {
        for (int i=0; i < 10; ++i) {
            System.out.println(i);
        }
    }
}