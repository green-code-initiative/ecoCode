package android.hardware.camera2;

import java.lang.Boolean;

public final class CameraManager {

    public static final boolean IS_ENABLED = true;
    public static final boolean IS_NOT_ENABLED = false;

    public static final Boolean IS_ENABLED_BOOL = new Boolean(true);
    public static final Boolean IS_NOT_ENABLED_BOOL = new Boolean(false);

    public void test() {
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null;
        try {
            cameraId = camManager.getCameraIdList()[0];
            camManager.setTorchMode(cameraId, true);  // Noncompliant {{Flashlight is one of the most energy-intensive component. Don't programmatically turn it on.}}
            camManager.setTorchMode(cameraId, false);

            boolean innerVariable = true;
            boolean innerVariableNot = false;

            camManager.setTorchMode(cameraId, IS_ENABLED);// Noncompliant {{Flashlight is one of the most energy-intensive component. Don't programmatically turn it on.}}
            camManager.setTorchMode(cameraId, IS_NOT_ENABLED);

            camManager.setTorchMode(cameraId, innerVariable); // TODO  Noncompliant
            camManager.setTorchMode(cameraId, innerVariableNot);

            camManager.setTorchMode(cameraId, IS_ENABLED_BOOL); // TODO Noncompliant
            camManager.setTorchMode(cameraId, IS_NOT_ENABLED_BOOL);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Service getSystemService(String string) {
        return null;
    }

    private String[] getCameraIdList() {
        return new String[]{"0", "1", "2"};
    }

    public void setTorchMode(String cameraId, boolean enabled) throws CameraAccessException {
        //torch manipulation
    }
}
