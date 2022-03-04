package android.media;

public class MediaRecorder {

    public void test() {
        MediaRecorder mr = new MediaRecorder(); // Noncompliant {{Failing to call release() on a Media Recorder may lead to continuous battery consumption.}}
    }

    public MediaRecorder() {
    }

    public void release() {
    }
}