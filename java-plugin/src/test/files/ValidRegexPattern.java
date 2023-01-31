package fr.greencodeinitiative.java.checks;

import java.util.regex.Pattern;

public class ValidRegexPattern {

    private static final Pattern pattern = Pattern.compile("foo"); // Compliant

    public boolean foo() {
        return pattern.matcher("foo").find();
    }
}
