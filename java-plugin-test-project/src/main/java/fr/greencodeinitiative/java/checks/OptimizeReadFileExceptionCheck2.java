package fr.greencodeinitiative.java.checks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

class OptimizeReadFileExceptionCheck2 {

    Logger logger = Logger.getLogger("");

    OptimizeReadFileExceptionCheck2(OptimizeReadFileExceptionCheck2 readFile) {
    }

    public void readPreferences(String filename) throws IOException {
        //...
        try (InputStream in = new FileInputStream(filename)) { // Noncompliant
            logger.info("my log");
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        }
        //...
    }
}