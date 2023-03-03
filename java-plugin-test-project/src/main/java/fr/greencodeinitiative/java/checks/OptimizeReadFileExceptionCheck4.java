package fr.greencodeinitiative.java.checks;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

class OptimizeReadFileExceptionCheck4 {

    Logger logger = Logger.getLogger("");

    OptimizeReadFileExceptionCheck4(OptimizeReadFileExceptionCheck4 readFile) {
    }

    public void readPreferences(String filename) {
        //...
        try (InputStream in = new FileInputStream(filename)) { // Noncompliant
            logger.info("my log");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        //...
    }
}