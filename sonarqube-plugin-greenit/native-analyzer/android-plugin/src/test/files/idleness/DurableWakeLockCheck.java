package android.os;

public final class PowerManager {

    public void test() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        WakeLock manager = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getPackageName());
        manager.acquire(); // Noncompliant {{Prefer setting a timeout when acquiring a wake lock to avoid running down the device's battery excessively.}}
        manager.acquire(100);
    }

    public WakeLock newWakeLock(int levelAndFlags, String tag) {
        validateWakeLockParameters(levelAndFlags, tag);
        return new WakeLock(levelAndFlags, tag, mContext.getOpPackageName());
    }

    public final class WakeLock {
        public void acquire(long milis) {
            String toto;
        }

        public void acquire() {
            String toto;
        }
    }
}
