public class Openclass {
    public static double price = 15.24; // Noncompliant
    public static long pages = 1053; // Noncompliant

    public static void main(String[] args) {
        double newPrice = Openclass.price;
        long newPages = Openclass.pages;
        System.out.println(newPrice);
        System.out.println(newPages);
        static long years = 3000; // Noncompliant
    }
    static{ // Noncompliant
        int a = 4;
    }

    public void printingA() {
        System.out.println(a);
    }

}