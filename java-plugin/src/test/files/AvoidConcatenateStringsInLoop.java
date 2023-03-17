package fr.greencodeinitiative.java.utils;

public class AvoidConcatenateStringsInLoop {

    public String concatenateStrings(String[] strings) {
        String result1 = "";

        for (String string : strings) {
            result1 += string; // Noncompliant {{Don't concatenate Strings in loop, use StringBuilder instead.}}
        }
        return result1;
    }

    public String concatenateStrings2() {
        String result2 = "";

        for (int i = 0; i < 1000; ++i) {
            result2 += "another"; // Noncompliant {{Don't concatenate Strings in loop, use StringBuilder instead.}}
        }
        return result2;
    }

    public String concatenateStrings3() {
        String result3 = "";

        for (int i = 0; i < 1000; ++i) {
            result3 = result3 + "another"; // Noncompliant {{Don't concatenate Strings in loop, use StringBuilder instead.}}
        }
        return result3;
    }

}
