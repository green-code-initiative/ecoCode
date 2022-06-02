package android.view;

class Surface {

    private static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    private static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
    private static final int CHANGE_FRAME_RATE_ALWAYS = 1;
    private static final int CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS = 0;

    public void  test() {
        Surface surface = new Surface();
        surface.setFrameRate(120.0f,0); // Noncompliant
//        surface.setFrameRate(90.0f,FRAME_RATE_COMPATIBILITY_FIXED_SOURCE);
    }

    public void	setFrameRate(float frameRate, int compatibility) {
        return;
    }

    public void setFrameRate(float frameRate, int compatibility, int changeFrameRateStrategy) {
        return;
    }

}