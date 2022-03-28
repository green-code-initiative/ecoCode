package android.content;

public final class SyncAdapter extends AbstractThreadedSyncAdapter {

    public void test() {
        SyncAdapter syncAdapter = new SyncAdapter();
        Account account = null;
        Bundle extras = null;
        String authority = "administrator";
        ContentProviderClient contentProviderClient = null;
        SyncResult syncResult = null;

        syncAdapter.getSyncAdapterBinder(); // Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}
        syncAdapter.onPerformSync(account, extras, authority, contentProviderClient, syncResult); // Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}
    }

    @Override
    private void onPerformSync(Account account,
                               Bundle extras,
                               String authority,
                               ContentProviderClient contentProviderClient,
                               SyncResult syncResult) {
    }
}

public abstract class AbstractThreadedSyncAdapter {

    private void getSyncAdapterBinder() {
        return null;
    }

    private abstract void onPerformSync(Account account,
                                        Bundle extras,
                                        String authority,
                                        ContentProviderClient contentProviderClient,
                                        SyncResult syncResult) {
    }
}