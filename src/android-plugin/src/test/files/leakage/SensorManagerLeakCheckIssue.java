package android.hardware;

public class SensorManager {

    public SensorManager() {
        unregisterListener();
    }

    public void test() {
        registerListener();
    }

    public static boolean registerListener() {
        return true;
    }

    public static void unregisterListener() {
    }
}

public class SensorManager2 {
    public void test() {
        unregisterListener();
    }
}

