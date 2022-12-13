package fr.cnumr.java.checks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

class ReadFile {
	ReadFile(ReadFile readFile) {
	}
	
	protected String getMessageFrom(final String value) {
		final String string = new String(value);
		return string;
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