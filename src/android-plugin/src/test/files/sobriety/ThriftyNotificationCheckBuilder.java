package android.app;

public final class Notification {

    public void test() {
        Notification.Builder notificationBuilder = new Notification.Builder();
        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notificationBuilder.setVibrate(null);
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), Notification.AUDIO_ATTRIBUTES_DEFAULT);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notificationBuilder.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), Notification.STREAM_DEFAULT);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notificationBuilder.setSound(null, Notification.STREAM_DEFAULT);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null);// Noncompliant {{Avoid using vibration or sound when notifying the users to use less energy.}}
        notificationBuilder.setSound(null);
        notificationBuilder.setSound(null, null);
    }

    public static class Builder {

        private Builder setVibrate(long[] longueur) {
            return null;
        }

        private Builder setSound(Uri soundUrl, AudioAttributes audio) {
            return null;
        }

        private Builder setSound(Uri soundUrl) {
            return null;
        }

        private Builder setSound(Uri soundUrl, int streamType) {
            return null;
        }

    }
}