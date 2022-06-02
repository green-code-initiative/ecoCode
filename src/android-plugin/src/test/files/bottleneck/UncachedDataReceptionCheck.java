package android.net.http;

public final class HttpResponseCache {

  public void test() {
    File file = new File("");
    HttpResponseCache.install(file, 1L);// Noncompliant {{Catching all of the application's HTTP responses to the filesystem.}}
  }

  public static synchronized HttpResponseCache install(File directory, long maxSize) throws IOException {
    throw new RuntimeException("Stub!");
  }
}
