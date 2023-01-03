package fr.cnumr.java.checks;

import java.util.Arrays;
import java.util.List;

class ReadFile {
	ReadFile(ReadFile readFile) {
	}
	public void readPreferences(String filename) {
		//...
		InputStream in = null;
		try {
			in = new FileInputStream(filename); // Noncompliant
		} catch (FileNotFoundException e) {
			logger.log(e);
		}
		//...
	}
}