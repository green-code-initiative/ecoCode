package android.media;

public class MediaPlayer {

    public void test() {
        MediaPlayer mp = new MediaPlayer(); // Noncompliant {{Failing to call release() on a Media Player may lead to continuous battery consumption.}}
    }

    public MediaPlayer() {
    }

    public void release() {
    }
}