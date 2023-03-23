package fr.greencodeinitiative.java.checks;

import java.util.Arrays;
import java.util.List;

class ReadFile {
    ReadFile(ReadFile readFile) {
    }

    public void readPreferences(String filename) {
        //...
        try (InputStream in = new FileInputStream(filename)) { // Noncompliant {{Optimize Read File Exceptions}}
            logger.log("my log");
        } catch (FileNotFoundException e) {
            logger.log(e);
        }
        //...
    }
}