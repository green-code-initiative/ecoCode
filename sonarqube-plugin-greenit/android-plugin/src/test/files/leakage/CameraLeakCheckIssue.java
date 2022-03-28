package android.hardware;

public class Camera {

    public void test() {
        open();// Noncompliant {{Failing to call android.hardware.Camera#release() can drain the battery in just a few hours.}}
    }

    public boolean open() {
        return true;
    }

    public void release() {
    }
}