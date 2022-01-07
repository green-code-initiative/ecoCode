package android.hardware;

public final class SensorManager {

    public void test() {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        sensorManager.getDefaultSensor(11); // Noncompliant {{Prefer using TYPE_GEOMAGNETIC_ROTATION_VECTOR instead of TYPE_ROTATION_VECTOR to use less energy.}}
        sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        sensorManager.getDefaultSensor(((int) Sensor.TYPE_ROTATION_VECTOR)); // Noncompliant {{Prefer using TYPE_GEOMAGNETIC_ROTATION_VECTOR instead of TYPE_ROTATION_VECTOR to use less energy.}}
    }

    public Sensor getDefaultSensor(int type) {
        return null;
    }
}

public final class Sensor {
    public static final int TYPE_ROTATION_VECTOR = 11;
    public static final int TYPE_GEOMAGNETIC_ROTATION_VECTOR = 20;
}
