package rust.checks;

import org.junit.Test;

import java.io.File;

public class AvoidFullSQLRequestTest {

    @Test
    public void test() {
        RustCheckVerifier.verify(new File("src/test/resources/checks/sql_full_query.rs"), new AvoidFullSQLRequest());
    }
}
