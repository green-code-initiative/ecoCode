package fr.greencodeinitiative.java.checks;

import java.util.regex.Pattern;

public class ValidRegexPattern3 {

    private final Pattern pattern;

    public ValidRegexPattern3() {
        pattern = Pattern.compile("foo"); // Compliant
    }

    public boolean foo() {
        return pattern.matcher("foo").find();
    }
}
