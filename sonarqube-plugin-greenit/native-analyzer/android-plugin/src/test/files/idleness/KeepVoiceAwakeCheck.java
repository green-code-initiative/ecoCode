package android.service.voice;

public class VoiceInteractionSession {

    public void test() {
        VoiceInteractionSession session = new VoiceInteractionSession();
        session.setKeepAwake(true);// Noncompliant {{VoiceInteractionSession.setKeepAwake(false) should be called to limit battery drain.}}
    }

    public VoiceInteractionSession() {
    }

    public void setKeepAwake(boolean type) {
    }
}
