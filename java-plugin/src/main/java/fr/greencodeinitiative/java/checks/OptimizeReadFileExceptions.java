package fr.greencodeinitiative.java.checks;


import java.util.Arrays;
import java.util.List;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonarsource.analyzer.commons.annotations.DeprecatedRuleKey;

@Rule(key = "EC28")
@DeprecatedRuleKey(repositoryKey = "greencodeinitiative-java", ruleKey = "GRSP0028")
public class OptimizeReadFileExceptions extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Optimize Read File Exceptions";
    private static final Logger LOGGER = Loggers.get(OptimizeReadFileExceptions.class);
    private boolean isExceptionFound = false;

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.TRY_STATEMENT, Kind.NEW_CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        LOGGER.debug("--------------------_____-----_____----- OptimizeReadFileExceptions.visitNode METHOD - BEGIN");
        if (tree.kind().getAssociatedInterface().equals(NewClassTree.class)) {
            LOGGER.debug("interface NewClassTree found");
            NewClassTree newClassTree = (NewClassTree) tree;
            if (newClassTree.identifier().symbolType().toString().equals("FileInputStream")) {
                LOGGER.debug("identifier 'FileInputStream' found");
                if (this.isExceptionFound) {
                    LOGGER.debug("exception found => launching 'reportIssue'");
                    reportIssue(tree, MESSAGERULE);
                } else {
                    LOGGER.debug("exception NOT found");
                }
            } else {
                LOGGER.debug("identifier 'FileInputStream' NOT found (real identifier : {}) => No issue launched", newClassTree.identifier().symbolType());
            }
        } else {
            LOGGER.debug("interface NewClassTree NOT found (real interface : {}) => casting to TryStatementTree", tree.kind().getAssociatedInterface());
            TryStatementTree tryStatementTree = (TryStatementTree) tree;
            List<CatchTree> catchTreeList = tryStatementTree.catches();

            LOGGER.debug("compute 'isExceptionFound'");
            this.isExceptionFound = computeIsExceptionFound(catchTreeList);
            LOGGER.debug("isExceptionFound : " + isExceptionFound);
        }
        LOGGER.debug("--------------------_____-----_____----- OptimizeReadFileExceptions.visitNode METHOD - END");
    }

    private boolean computeIsExceptionFound(List<CatchTree> catchTreeList) {
        return catchTreeList.stream().anyMatch(catchTree ->
                catchTree.parameter().type().symbolType().toString().equals("FileNotFoundException")
                        || catchTree.parameter().type().symbolType().toString().equals("IOException")
                        || catchTree.parameter().type().symbolType().toString().equals("Exception")
                        || catchTree.parameter().type().symbolType().toString().equals("Throwable")
        );
    }
}
