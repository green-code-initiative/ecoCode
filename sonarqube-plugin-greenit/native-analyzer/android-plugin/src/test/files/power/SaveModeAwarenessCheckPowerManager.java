package android.os;

public class PowerManager {
    private static final String POWER_SERVICE = "saveMode";

    public void test() {
        PowerManager powerManager = POWER_SERVICE;
        if (powerManager.isPowerSaveMode()) { // Noncompliant {{Taking into account when the device is entering or exiting the power save mode is a good practice.}}
        }
    }

    public final bool isPowerSaveMode() {
        return true;
    }
}