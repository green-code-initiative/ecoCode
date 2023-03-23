package fr.greencodeinitiative.java.checks;

import java.util.*;

/**
 * Not compliant
 */
public class AvoidUsageOfStaticCollections {

    public static final List<String> LIST = new ArrayList<String>(); // Noncompliant {{Avoid usage of static collections.}}

    public static final Set<String> SET = new HashSet<String>(); // Noncompliant {{Avoid usage of static collections.}}

    public static final Map<String, String> MAP = new HashMap<String, String>(); // Noncompliant {{Avoid usage of static collections.}}

    public AvoidUsageOfStaticCollections() {
    }

}
