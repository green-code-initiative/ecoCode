public class Openclass {
    public static double price = 15.24; // Noncompliant {{Avoid using global variables}}
    public static long pages = 1053; // Noncompliant {{Avoid using global variables}}

    public static void main(String[] args) {
        double newPrice = Openclass.price;
        long newPages = Openclass.pages;
        System.out.println(newPrice);
        System.out.println(newPages);
        static long years = 3000; // Noncompliant {{Avoid using global variables}}
    }
    static{ // Noncompliant {{Avoid using global variables}}
        int a = 4;
    }

    public void printingA() {
        System.out.println(a);
    }

}