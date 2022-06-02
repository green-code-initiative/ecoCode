package android.app;

public final class NotificationChannel {

    public void test() {
        NotificationChannel notifManag = new NotificationChannel();
        notifManag.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notifManag.setVibrationPattern(null);
        notifManag.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), Notification.AUDIO_ATTRIBUTES_DEFAULT);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notifManag.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notifManag.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notifManag.setSound(null, null);
    }

    private NotificationChannel setVibrationPattern(long[] pattern) {
        return null;
    }

    private NotificationChannel setSound(Uri uri, Notification audio) {
        return null;
    }
}
