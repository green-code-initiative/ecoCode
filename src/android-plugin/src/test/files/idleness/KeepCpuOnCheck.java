package android.os;

public final class PowerManager {

    public void test() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        WakeLock manager = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getPackageName()); // Noncompliant {{Keeping the screen on should be avoided to avoid draining battery.}}
    }

    public WakeLock newWakeLock(int levelAndFlags, String tag) {
        validateWakeLockParameters(levelAndFlags, tag);
        return new WakeLock(levelAndFlags, tag, mContext.getOpPackageName());
    }
}
