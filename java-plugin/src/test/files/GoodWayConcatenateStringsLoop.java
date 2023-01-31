package fr.greencodeinitiative.java.utils;

public class GoodWayConcatenateStringsLoop {

    public String concatenateStrings(String[] strings) {
        StringBuilder result = new StringBuilder();

        for (String string : strings) {
            result.append(string);
        }
        return result.toString();
    }

    public void testConcateOutOfLoop() {
        String result = "";
        result += "another";
    }

    public void testConcateOutOfLoop2() {
        String result = "";
        result = result + "another";
    }

    public String changeValueStringInLoop() {
        String result3 = "";

        for (int i = 0; i < 1; ++i) {
            result3 = "another";
        }
        return result3;
    }

}
