package android.view;

class Surface {

    private static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    private static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
    private static final int CHANGE_FRAME_RATE_ALWAYS = 1;
    private static final int CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS = 0;
    private static final float FRAME_RATE_0 = 0.0f;
    private static final float FRAME_RATE_60 = 60.0f;
    private static final float FRAME_RATE_90 = 90.0f;
    private static final float FRAME_RATE_120 = 120.0f;

    public void test() {
        Surface surface = new Surface();
        surface.setFrameRate(FRAME_RATE_0, FRAME_RATE_COMPATIBILITY_DEFAULT);
        surface.setFrameRate(FRAME_RATE_60, FRAME_RATE_COMPATIBILITY_DEFAULT);
        surface.setFrameRate(FRAME_RATE_90, FRAME_RATE_COMPATIBILITY_DEFAULT); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
        surface.setFrameRate(FRAME_RATE_120, FRAME_RATE_COMPATIBILITY_DEFAULT); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}

        // Test with compatibility fixed source
        surface.setFrameRate(0.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE);
        surface.setFrameRate(60.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE);
        surface.setFrameRate(90.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
        surface.setFrameRate(120.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
        // Test with 3 args
        surface.setFrameRate(0.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS);
        surface.setFrameRate(60.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS);
        surface.setFrameRate(90.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
        surface.setFrameRate(120.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}

        surface.setFrameRate(0.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ALWAYS);
        surface.setFrameRate(60.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ALWAYS);
        surface.setFrameRate(90.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ALWAYS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
        surface.setFrameRate(120.0f, FRAME_RATE_COMPATIBILITY_DEFAULT, CHANGE_FRAME_RATE_ALWAYS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}

        surface.setFrameRate(0.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ALWAYS);
        surface.setFrameRate(60.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ALWAYS);
        surface.setFrameRate(90.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ALWAYS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
        surface.setFrameRate(120.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ALWAYS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}

        surface.setFrameRate(0.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS);
        surface.setFrameRate(60.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS);
        surface.setFrameRate(90.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
        surface.setFrameRate(120.0f, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS); // Noncompliant {{To optimize content refresh and save energy, frame rate should be set at maximum 60Hz.}}
    }

    public void	setFrameRate(float frameRate, int compatibility) {
        return;
    }

    public void setFrameRate(float frameRate, int compatibility, int changeFrameRateStrategy) {
        return;
    }

}