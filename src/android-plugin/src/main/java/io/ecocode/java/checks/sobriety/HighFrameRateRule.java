package io.ecocode.java.checks.sobriety;

import io.ecocode.java.checks.helpers.SpecificMethodCheck;
import org.sonar.check.Rule;

@Rule(key = "ESOB014", name= "ecoCodeHighFrameRate")
public class HighFrameRateRule extends SpecificMethodCheck {

    public HighFrameRateRule() {
        super("android.view.Surface","setFrameRate");
    }

    @Override
    public String getMessage() {
        return "Not overriding setFrameRate default behavior is recommanded to avoid higher battery usage.";
    }
}