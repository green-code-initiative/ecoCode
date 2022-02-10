package android.hardware.camera2;

public final class CameraManager {

    public void test() {
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null;
        try {
            cameraId = camManager.getCameraIdList()[0];
            camManager.setTorchMode(cameraId, true);  // Noncompliant {{Turning on the torch mode programmatically must absolutely be avoided.}}
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