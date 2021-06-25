import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class AvoidTryCatchFinallyCheck {
    AvoidTryCatchFinallyCheck(AvoidTryCatchFinallyCheck mc) {
    }

    public void openFileWithFInally() {
        FileReader reader = null;
        try { // Noncompliant
            reader = new FileReader("someFile");
            int i = 0;
            while (i != -1) {
                i = reader.read();
                System.out.println((char) i);
            }
        } catch (IOException e) {
            //do something clever with the exception
        } finally {
            if (reader != null) {
                try { // Noncompliant
                    reader.close();
                } catch (IOException e) {
                    //do something clever with the exception
                }
            }
            System.out.println("--- File End ---");
        }
    }


    public void openFileWithoutFInally() {
        FileReader reader = null;
        try { // Noncompliant
            reader = new FileReader("someFile");
            int i = 0;
            while (i != -1) {
                i = reader.read();
                System.out.println((char) i);
            }
        } catch (IOException e) {
            //do something clever with the exception
        }
    }


    public void openFileWithoutTryCatch() throws IOException {
        FileReader reader = null;
        reader = new FileReader("someFile");
        int i = 0;
        while (i != -1) {
            i = reader.read();
            System.out.println((char) i);
        }

    }

}