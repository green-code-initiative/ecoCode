package fr.greencodeinitiative.java.checks;

import java.util.regex.Pattern;

public class ValidRegexPattern2 {

    private final Pattern pattern = Pattern.compile("foo"); // Compliant

    public boolean foo() {
        return pattern.matcher("foo").find();
    }
}
