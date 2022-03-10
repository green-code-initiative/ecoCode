package android.location;

public class LocationManager {

    public void test() {
        requestLocationUpdates();// Noncompliant {{Failing to call android.location.LocationManager#removeUpdates() can drain the battery in just a few hours.}}
    }

    public boolean requestLocationUpdates() {
        return true;
    }

    public void removeUpdates() {
    }
}