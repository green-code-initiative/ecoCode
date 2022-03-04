package android.net.wifi;

public class WifiManager {

    public void test() {
        MulticastLock multicastLock = new MulticastLock();
        multicastLock.acquire();// Noncompliant {{Failing to call WifiManager.MulticastLock#release() can cause a noticeable battery drain.}}
    }

    public class MulticastLock {
        public void acquire() {
            String str = "acquire Called";
        }

        public void release() {
            String str = "release Called";
        }
    }
}
