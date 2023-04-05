import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

class TestClass {

	public void NOK_CookieCreation() {
		// create objet cookie
		Cookie C = new Cookie("id","674684641");
		// set the validity
		C.setMaxAge(24*3600);
	}
}