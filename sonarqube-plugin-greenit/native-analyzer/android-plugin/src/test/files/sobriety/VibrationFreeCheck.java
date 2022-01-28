package android.content;

public final class Context {

    public void test() {
        Service service = (Service) getSystemService((String) Sensor.VIBRATOR_SERVICE);// Noncompliant {{Prefer to avoid using getSystemService(Context.VIBRATOR_SERVICE) to use less energy.}}
        Service service = (Service) getSystemService("vibrator");// Noncompliant {{Prefer to avoid using getSystemService(Context.VIBRATOR_SERVICE) to use less energy.}}
    }

    private Service getSystemService(String string) {
        return null;
    }

    public final class Sensor {
        public static final String VIBRATOR_SERVICE = "vibrator";
    }
}