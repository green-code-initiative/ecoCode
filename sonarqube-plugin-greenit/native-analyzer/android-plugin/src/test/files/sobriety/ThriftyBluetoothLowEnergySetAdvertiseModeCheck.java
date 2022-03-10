package android.bluetooth.le;

public final class AdvertiseSettings {

    public static final int ADVERTISE_MODE_LOW_POWER = 0;
    public static final int ADVERTISE_MODE_BALANCED = 1;
    public static final int ADVERTISE_MODE_LOW_LATENCY = 2;
    public static final long ADVERTISE_MODE_LOW_LATENCY_LONG = 2L;

    public void test() {
        Builder builder = new Builder();
        builder.setAdvertiseMode(ADVERTISE_MODE_LOW_LATENCY); // Noncompliant {{You should call AdvertiseSettings.Builder.setAdvertiseMode(ADVERTISE_MODE_LOW_POWER) to optimize battery usage.}}
        builder.setAdvertiseMode(ADVERTISE_MODE_BALANCED); // Noncompliant {{You should call AdvertiseSettings.Builder.setAdvertiseMode(ADVERTISE_MODE_LOW_POWER) to optimize battery usage.}}
        builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED); // Noncompliant {{You should call AdvertiseSettings.Builder.setAdvertiseMode(ADVERTISE_MODE_LOW_POWER) to optimize battery usage.}}
        builder.setAdvertiseMode((int) ADVERTISE_MODE_LOW_LATENCY_LONG); // Noncompliant {{You should call AdvertiseSettings.Builder.setAdvertiseMode(ADVERTISE_MODE_LOW_POWER) to optimize battery usage.}}
        builder.setAdvertiseMode(1); // Noncompliant {{You should call AdvertiseSettings.Builder.setAdvertiseMode(ADVERTISE_MODE_LOW_POWER) to optimize battery usage.}}
        builder.setAdvertiseMode(ADVERTISE_MODE_LOW_POWER);
        builder.setAdvertiseMode(0);
        builder.setAdvertiseMode((int) 0L);
    }

    public static final class Builder {
        public Builder() {
        }

        public Builder setAdvertiseMode(int advertiseMode) {
            return new Builder();
        }
    }
}
