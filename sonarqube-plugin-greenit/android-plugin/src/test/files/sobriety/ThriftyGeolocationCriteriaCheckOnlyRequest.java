package android.location;

public class LocationManager {

    public void test() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.Location_SERVICE);
        locationManager.requestLocationUpdates("", 1, 1f, null);// Noncompliant {{You should configure a location provider (LocationManager.getBestProvider(...)) to optimize battery usage.}}
    }

    public void requestLocationUpdates(String provider, long minTimeMs, float minDistanceM, LocationListener listener) {
    }

    public void getBestProvider(Criteria criteria, Boolean bool) {
    }
}

public class Criteria {
    public static final int NO_REQUIREMENT = 0;
    public static final int POWER_LOW = 1;
    public static final int POWER_MEDIUM = 2;
    public static final int POWER_HIGH = 3;
    private int mPowerRequirement = NO_REQUIREMENT;

    public void setPowerRequirement(int requirement) {
        mPowerRequirement = requirement;
    }
}
