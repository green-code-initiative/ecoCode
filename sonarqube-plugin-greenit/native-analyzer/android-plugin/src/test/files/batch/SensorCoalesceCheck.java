package android.hardware;

public final class SensorManager {

    private int SENSOR_DELAY_NORMAL = 210000;
    private SensorManager registerListener(SensorEventListener sensorEventListener, Sensor gyroscope, int delay) {return null;}
    private SensorManager registerListener(SensorEventListener sensorEventListener, Sensor gyroscope, int delay, Handler handler) {return null;}
    private SensorManager registerListener(SensorEventListener sensorEventListener, Sensor gyroscope, int delay, int latency) {return null;}
    private SensorManager registerListener(SensorEventListener sensorEventListener, Sensor gyroscope, int delay, int latency, Handler handler) {return null;}

    public void test() {

        final int positiveNumber = 200000;
        final long positiveNumberL = 200000L;
        final int zeroNumber = 0;
        final long zeroNumberL = 0L;
        final int negativeNumber = -1;
        final long negativeNumberL = -1L;

        private SensorManager sensorManager = new SensorManager();
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, handler); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}

        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, 0); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, (int)0L); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, -1); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, (int)-1L); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, 200000);
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, (int)200000L);
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, 0, handler); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, (int)0L, handler); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, -1, handler); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, (int)-1L, handler); // Noncompliant {{Avoid using registerListener without a fourth parameter maxReportLatencyUs}}
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, 200000, handler);
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, (int)200000L, handler);

        // TODO: 16/02/2022 Local and Global variable not working yet
    }
}