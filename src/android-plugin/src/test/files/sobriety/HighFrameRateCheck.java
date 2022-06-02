package android.view;

class Surface {

    public static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    public static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
    public static final int CHANGE_FRAME_RATE_ALWAYS = 1;
    public static final int CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS = 0;

    public void  test() {
        Surface surface = new Surface();
    }

    public void	setFrameRate(float frameRate, int compatibility) {
        return;
    }

    public void setFrameRate(float frameRate, int compatibility, int changeFrameRateStrategy) {
        return;
    }

}