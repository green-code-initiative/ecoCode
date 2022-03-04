package android.opengl;

public class GLSurfaceView {

    public final static int RENDERMODE_WHEN_DIRTY = 0;
    public final static int RENDERMODE_CONTINUOUSLY = 1;

    public void test() {
        GLSurfaceView surface = new GLSurfaceView();
        surface.setRenderMode(RENDERMODE_CONTINUOUSLY); // Noncompliant {{Using RENDERMODE_WHEN_DIRTY instead of RENDERMODE_CONTINUOUSLY can improve battery life.}}
        surface.setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void setRenderMode(int renderMode) {
    }

}
