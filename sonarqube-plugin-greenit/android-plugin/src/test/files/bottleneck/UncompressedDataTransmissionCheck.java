public abstract class OutputStream {

    public void test() {
        OutputStream stream = new OutputStream() {
            public void test() {
            }
        };

        OutputStream olalala = stream.getOutputStream();

        stream.method(olalala);
        java.net.URLConnection con = new java.net.URLConnection();
        java.net.HttpURLConnection httpCon = new java.net.HttpURLConnection();

        java.io.OutputStream stream = ((java.io.OutputStream) con.getOutputStream());// Noncompliant {{Prefer using GzipOutputStream instead of OutputStream to improve energy efficiency.}}
        java.io.OutputStream stream = (java.io.OutputStream) con.getOutputStream();// Noncompliant {{Prefer using GzipOutputStream instead of OutputStream to improve energy efficiency.}}
        java.io.OutputStream stream = ((java.io.DataOutputStream) con.getOutputStream());// Noncompliant {{Prefer using GzipOutputStream instead of OutputStream to improve energy efficiency.}}
        java.io.OutputStream stream = (java.io.DataOutputStream) con.getOutputStream();// Noncompliant {{Prefer using GzipOutputStream instead of OutputStream to improve energy efficiency.}}
        java.io.OutputStream stream = (java.io.DataOutputStream) httpCon.getOutputStream();// Noncompliant {{Prefer using GzipOutputStream instead of OutputStream to improve energy efficiency.}}
        java.io.OutputStream stream = ((java.io.DataOutputStream) httpCon.getOutputStream());// Noncompliant {{Prefer using GzipOutputStream instead of OutputStream to improve energy efficiency.}}

        java.io.OutputStream stream = new java.io.FileOutputStream("myfile.zip");
        java.io.OutputStream stream = new java.io.BufferedOutputStream(new java.io.FileOutputStream("myfile.zip"));
        java.io.OutputStream stream = new java.io.BufferedOutputStream(con.getOutputStream());// Noncompliant {{Prefer using GzipOutputStream instead of OutputStream to improve energy efficiency.}}

        java.util.zip.GZIPOutputStream stream = new java.io.GZIPOutputStream(con.getOutputStream());
        java.io.OutputStream stream = new java.io.GZIPOutputStream(con.getOutputStream());
    }
}
