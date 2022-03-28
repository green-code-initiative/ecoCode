package android.content;

public class IntentFilter {
    public static final String ACTION_BATTERY_LOW = "android.intent.action.BATTERY_LOW";
    public static final String ACTION_BATTERY_OKAY = "android.intent.action.BATTERY_OKAY";
    public static final String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    public static final String ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    public static final String ACTION_OTHER = "android.intent.action.ACTION_OTHER";
    private String mAction = "";

    public void test() {
        IntentFilter filter = new IntentFilter(ACTION_BATTERY_LOW); // Noncompliant {{Monitoring power changes and customizing behavior depending on battery level is a good practice.}}
        IntentFilter filter1 = new IntentFilter(ACTION_BATTERY_OKAY); // Noncompliant {{Monitoring power changes and customizing behavior depending on battery level is a good practice.}}
        IntentFilter filter2 = new IntentFilter(ACTION_POWER_CONNECTED); // Noncompliant {{Monitoring power changes and customizing behavior depending on battery level is a good practice.}}
        IntentFilter filter3 = new IntentFilter(ACTION_POWER_DISCONNECTED); // Noncompliant {{Monitoring power changes and customizing behavior depending on battery level is a good practice.}}
        IntentFilter filter4 = new IntentFilter(ACTION_OTHER);
        IntentFilter filter5 = new IntentFilter();

        IntentFilter.create(ACTION_BATTERY_LOW, ""); // Noncompliant {{Monitoring power changes and customizing behavior depending on battery level is a good practice.}}

        filter2.addAction(ACTION_BATTERY_LOW); // Noncompliant {{Monitoring power changes and customizing behavior depending on battery level is a good practice.}}
        filter2.addAction(ACTION_OTHER);
    }

    public IntentFilter(String str) {
        mAction = str;
    }

    public IntentFilter(String str, String dataType) {
    }

    public IntentFilter() {
    }

    public final void addAction(String action) {
        if (!mActions.contains(action)) {
            mActions.add(action.intern());
        }
    }

    public static IntentFilter create(String action, String dataType) {
        try {
            return new IntentFilter(action);
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("Bad MIME type", e);
        }
    }
}