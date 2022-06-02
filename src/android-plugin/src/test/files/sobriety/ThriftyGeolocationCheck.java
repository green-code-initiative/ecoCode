package android.location;

public final class LocationManager {

    private static final int MIN_DISTANCE = 0;
    private static final int MIN_TIME = 0;

    public void test() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                "providerString",
                0,// Noncompliant {{Location updates should be done with a time greater than 0.}}
                0,// Noncompliant {{Location updates should be done with a distance interval greater than 0.}}
                null);
        locationManager.requestLocationUpdates("",
                0,// Noncompliant {{Location updates should be done with a time greater than 0.}}
                0,// Noncompliant {{Location updates should be done with a distance interval greater than 0.}}
                null);
        locationManager.requestLocationUpdates(null,
                0,// Noncompliant {{Location updates should be done with a time greater than 0.}}
                0,// Noncompliant {{Location updates should be done with a distance interval greater than 0.}}
                null);
        locationManager.requestLocationUpdates("",
                0.f, // Noncompliant {{Location updates should be done with a time greater than 0.}}
                0.f,// Noncompliant {{Location updates should be done with a distance interval greater than 0.}}
                null);
        locationManager.requestLocationUpdates("",
                0L, // Noncompliant {{Location updates should be done with a time greater than 0.}}
                0L,// Noncompliant {{Location updates should be done with a distance interval greater than 0.}}
                null);
        locationManager.requestLocationUpdates(0,// Noncompliant {{Location updates should be done with a time greater than 0.}}
                0,// Noncompliant {{Location updates should be done with a distance interval greater than 0.}}
                null,
                null);
        locationManager.requestLocationUpdates(MIN_DISTANCE,// Noncompliant {{Location updates should be done with a time greater than 0.}}
                MIN_TIME,// Noncompliant {{Location updates should be done with a distance interval greater than 0.}}
                null,
                null);

        locationManager.requestLocationUpdates(12147483647, 12.15487265142556, null, null);
        locationManager.requestLocationUpdates(1L, 12.f, null, null);
        locationManager.requestLocationUpdates(1, 12, null, null);
        locationManager.requestLocationUpdates(null, null, null);
    }

    public Sensor getDefaultSensor(int type) {
        return null;
    }

    public void requestLocationUpdates(String provider, long minTime, float minDistance, LocationListener listener) {
    }

    public void requestLocationUpdates(@NonNull String provider, long minTime, float minDistance,
                                       @NonNull LocationListener listener, @Nullable Looper looper) {
    }

    public void requestLocationUpdates(
            @NonNull String provider,
            long minTime,
            float minDistance,
            @NonNull @CallbackExecutor Executor executor,
            @NonNull LocationListener listener) {
    }

    public void requestLocationUpdates(long minTime, float minDistance,
                                       @NonNull Criteria criteria, @NonNull LocationListener listener,
                                       @Nullable Looper looper) {
    }

    public void requestLocationUpdates(
            long minTime,
            float minDistance,
            @NonNull Criteria criteria,
            @NonNull @CallbackExecutor Executor executor,
            @NonNull LocationListener listener) {
    }

    public void requestLocationUpdates(@NonNull String provider, long minTime, float minDistance,
                                       @NonNull PendingIntent pendingIntent) {
    }

    public void requestLocationUpdates(long minTime, float minDistance,
                                       @NonNull Criteria criteria, @NonNull PendingIntent pendingIntent) {
    }

    public void requestLocationUpdates(
            @Nullable LocationRequest locationRequest,
            @NonNull LocationListener listener,
            @Nullable Looper looper) {
    }

    public void requestLocationUpdates(
            @Nullable LocationRequest locationRequest,
            @NonNull @CallbackExecutor Executor executor,
            @NonNull LocationListener listener) {
    }

    public void requestLocationUpdates(
            @Nullable LocationRequest locationRequest,
            @NonNull PendingIntent pendingIntent) {
    }
}
