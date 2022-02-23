package android.content;

public class IntentFilter {
    private static final String ACTION_BATTERY_CHANGED = "android.intent.action.BATTERY_CHANGED";

    private String mAction = "";

    public void test() {
        IntentFilter filter3 = new IntentFilter(ACTION_BATTERY_CHANGED); // Noncompliant {{Taking into account when the device is entering or exiting the power save mode is a good practice.}}

        IntentFilter.create(ACTION_BATTERY_CHANGED,"");// Noncompliant {{Taking into account when the device is entering or exiting the power save mode is a good practice.}}

        filter2.addAction(ACTION_BATTERY_CHANGED); // Test not passing in plugin test file, but works in android app
    }

    public IntentFilter(String str){
        mAction = str;
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