package rust.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.rust.RustGrammar;

import java.util.Collections;
import java.util.Set;

@Rule(
        key = "AvoidFullSQLRequest",
        name = "Developpement",
        description = AvoidFullSQLRequest.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidFullSQLRequest extends RustCheck{

    protected static final String MESSAGERULE = "Don't use the query SELECT * FROM";
    private static final String REGEXPSELECTFROM = "(?i).*select.*\\*.*from.*";

    @Override
    public Set<AstNodeType> subscribedKinds() {
        return Collections.singleton(RustGrammar.STRING_LITERAL);
    }

    @Override
    public void visitNode(AstNode node) {
        var isSelectFrom = false;

        if (node.is(RustGrammar.STRING_LITERAL)) {
            isSelectFrom = node.toString().matches(REGEXPSELECTFROM);
        }

        if (isSelectFrom) {
            raiseIssue(node);
        }
    }

    private void raiseIssue(AstNode node) {
        addIssue(MESSAGERULE, node);
    }


}
