package android.app;

import android.io.File;

public class Context {

    public void test() {
        Context context = new Context();
        context.getCacheDir().deleteRecursively();
    }

    public void	deleteRecursively() {
        return;
    }
}