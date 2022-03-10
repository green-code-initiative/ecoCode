package android.view;

public interface WindowManager extends ViewManager {

    public static final float BRIGHTNESS_OVERRIDE_NONE = -1.0f;
    public static final float BRIGHTNESS_OVERRIDE_OFF = 0.0f;
    public static final float BRIGHTNESS_OVERRIDE_FULL = 1.0f;

    public static class LayoutParams {
        public float screenBrightness = BRIGHTNESS_OVERRIDE_NONE;

        public void InnerStaticClassMethod() {
            LayoutParams params;
            params.screenBrightness = BRIGHTNESS_OVERRIDE_FULL; // Noncompliant {{Forcing brightness to max value may cause useless energy consumption.}}
            params.screenBrightness = 1f; // Noncompliant {{Forcing brightness to max value may cause useless energy consumption.}}
            params.screenBrightness = 1; // Noncompliant {{Forcing brightness to max value may cause useless energy consumption.}}
            params.screenBrightness = 0.5f;
            params.screenBrightness = BRIGHTNESS_OVERRIDE_NONE;

            float blabla = 1f;
            params.screenBrightness = blabla; // TODO Noncompliant for VARIABLES
        }
    }
}
