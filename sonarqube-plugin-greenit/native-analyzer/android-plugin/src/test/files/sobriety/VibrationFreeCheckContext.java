package android.content;

public final class Context {

    public void test() {
        Service service = (Service) getSystemService((String) Sensor.VIBRATOR_SERVICE);// Noncompliant {{Avoid using the device vibrator to use less energy.}}
        Service service = (Service) getSystemService("vibrator");// Noncompliant {{Avoid using the device vibrator to use less energy.}}
        Service service = (Service) getSystemService(((String) Sensor.VIBRATOR_MANAGER_SERVICE));// Noncompliant {{Avoid using the device vibrator to use less energy.}}
        Service service = (Service) getSystemService("vibrator_manager");// Noncompliant {{Avoid using the device vibrator to use less energy.}}
    }

    private Service getSystemService(String string) {
        return null;
    }

    public final class Sensor {
        public static final String VIBRATOR_MANAGER_SERVICE = "vibrator_manager";
        public static final String VIBRATOR_SERVICE = "vibrator";
    }
}