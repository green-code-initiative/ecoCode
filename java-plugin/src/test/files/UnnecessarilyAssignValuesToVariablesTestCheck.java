package fr.greencodeinitiative.java.checks;

class UnnecessarilyAssignValuesToVariablesTestCheck {
    UnnecessarilyAssignValuesToVariablesTestCheck(UnnecessarilyAssignValuesToVariablesTestCheck mc) {
    }

    public int testSwitchCase() throws Exception {
        int variableFor = 5;
        int variableIf = 5;
        int variableWhile = 5;
        int variableExp = 5;
        int variableReturn = 5;
        int variableCLass = 5;
        int[] intArray = {10, 20, 30, 40, 50};

        Exception variableException = new Exception("message");
        int variableNotUse = 5; // Noncompliant {{The variable is declared but not really used}}


        variableNotUse = 10;
        for (variableFor = 0; variableFor < 5; ++variableFor) {
            System.out.println(variableFor);
        }

        for (int ia : intArray) {
            System.out.println((char) ia);
        }

        if (variableIf > 10) {
            System.out.println(variableIf);
        }

        while (variableWhile > 10) {
            System.out.println(variableWhile);
        }

        variableExp += 1;
        variableNotUse = variableExp;
        TestClass testClass = new TestClass(variableCLass);
        if (testClass.isTrue()) {
            throw variableException;
        }
        return variableReturn;
    }

    private class TestClass {
        TestClass(int i) {
            ++i;
        }

        public boolean isTrue() {
            return true;
        }
    }


    private int getIntValue() {
        return 3;
    }

    public int testNonCompliantReturn() {
        int i = getIntValue(); // Noncompliant {{Immediately return this expression instead of assigning it to the temporary variable}}
        return i;
    }

    public int testCompliantReturn() {
        return getIntValue();
    }

    public void testNonCompliantThrow() throws Exception {
        Exception exception = new Exception("dummy"); // Noncompliant {{Immediately throw this expression instead of assigning it to the temporary variable}}
        throw exception;
    }

    public void testCompliantThrow() throws Exception {
        throw new Exception("dummy");
	}
}