package android.app;

public class AlarmManager {

    public void test() {
        AlarmManager alarm = new AlarmManager();
        alarm.setExact(1, 1, null); // Noncompliant {{Using exact alarms unnecessarily reduces the OS's ability to minimize battery use (i.e. Doze Mode).}}
        alarm.setExact(1, 1, "tag", null, null); // Noncompliant {{Using exact alarms unnecessarily reduces the OS's ability to minimize battery use (i.e. Doze Mode).}}
        alarm.setExactAndAllowWhileIdle(1, 1, null); // Noncompliant {{Using exact alarms unnecessarily reduces the OS's ability to minimize battery use (i.e. Doze Mode).}}
        alarm.setRepeating(1, 1, 1, ""); // Noncompliant {{Using exact alarms unnecessarily reduces the OS's ability to minimize battery use (i.e. Doze Mode).}}
    }

    public AlarmManager() {
    }

    public void setExact(@AlarmType int type, long triggerAtMillis, PendingIntent operation) {
    }

    public void setExact(@AlarmType int type, long triggerAtMillis, String tag, OnAlarmListener listener, Handler targetHandler) {
    }

    public void setExactAndAllowWhileIdle(@AlarmType int type, long triggerAtMillis, PendingIntent operation) {
    }

    public void setRepeating(int alarmType, long triggerAtMillis, long intervalMillis, Object operation) {
    }

}
