package fr.greencodeinitiative.java.checks;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

class OptimizeReadFileExceptionCheck3 {

    Logger logger = Logger.getLogger("");

    OptimizeReadFileExceptionCheck3(OptimizeReadFileExceptionCheck3 readFile) {
    }

    public void readPreferences(String filename) {
        //...
        try (InputStream in = new FileInputStream(filename)) { // Noncompliant
            logger.info("my log");
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        //...
    }
}