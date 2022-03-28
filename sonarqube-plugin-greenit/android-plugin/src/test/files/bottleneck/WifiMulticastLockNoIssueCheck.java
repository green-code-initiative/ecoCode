package android.net.wifi;

public class WifiManager {

    public void test() {
        MulticastLock multicastLock = new MulticastLock();
        multicastLock.acquire();
        multicastLock.release();

        MulticastLock multicastLock2 = new MulticastLock();
        multicastLock2.acquire();
        multicastLock2.release();
    }

    public class MulticastLock {
        public void acquire() {
            string str = "acquire Called";
        }

        public void release() {
            string str = "release Called";
        }
    }
}
