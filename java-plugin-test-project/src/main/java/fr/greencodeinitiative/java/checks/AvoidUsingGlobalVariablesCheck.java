package fr.greencodeinitiative.java.checks;

public class AvoidUsingGlobalVariablesCheck {
    public static double price = 15.24; // Noncompliant
    public static long pages = 1053; // Noncompliant

    public static void main(String[] args) {
        double newPrice = AvoidUsingGlobalVariablesCheck.price;
        long newPages = AvoidUsingGlobalVariablesCheck.pages;
        System.out.println(newPrice);
        System.out.println(newPages);
    }
    static{ // Noncompliant
        int a = 4;
    }

    public void printingA() {
        System.out.println("a");
    }

}