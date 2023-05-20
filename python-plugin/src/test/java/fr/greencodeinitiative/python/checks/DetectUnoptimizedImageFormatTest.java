package fr.greencodeinitiative.python.checks;

import org.junit.Test;
import org.sonar.python.checks.utils.PythonCheckVerifier;

public class DetectUnoptimizedImageFormatTest {

    @Test
    public void test() {
        PythonCheckVerifier.verify("src/test/resources/checks/detectUnoptimizedImageFormat.py", new DetectUnoptimizedImageFormat());
        PythonCheckVerifier.verifyNoIssue("src/test/resources/checks/detectUnoptimizedImageFormatCompliant.py", new DetectUnoptimizedImageFormat());
    }
}
