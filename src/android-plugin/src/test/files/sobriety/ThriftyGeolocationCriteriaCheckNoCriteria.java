package android.location;

public class LocationManager {

    public void test() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.Location_SERVICE);

        locationManager.requestLocationUpdates(locationManager.getBestProvider(null, true), 1, 1f, null);// Noncompliant {{You should call Criteria.setPowerRequirement(POWER_LOW) to optimize battery usage.}}
    }

    public void requestLocationUpdates(String provider, long minTimeMs, float minDistanceM, LocationListener listener) {
    }

    public String getBestProvider(Criteria criteria, boolean bool) {
        return "";
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
