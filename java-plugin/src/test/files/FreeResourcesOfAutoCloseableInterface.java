package fr.greencodeinitiative.java.checks;

import java.io.*;

class FreeResourcesOfAutoCloseableInterface {
    FreeResourcesOfAutoCloseableInterface(FreeResourcesOfAutoCloseableInterface mc) {

    }

    public void foo1() {
        String fileName = "./FreeResourcesOfAutoCloseableInterface.java";
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void foo2() {
        String fileName = "./FreeResourcesOfAutoCloseableInterface.java";
        try { // Noncompliant
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            System.out.printl(br.readLine());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (fr) {
                fr.close();
            }
            if (br) {
                br.close();
            }
        }
    }
}