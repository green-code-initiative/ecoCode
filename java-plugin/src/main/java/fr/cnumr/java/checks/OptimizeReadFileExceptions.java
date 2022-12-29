package fr.cnumr.java.checks;


import java.util.Arrays;
import java.util.List;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TryStatementTree;

@Rule(
        key = "GRSP0028",
        name = "Developpement",
        description = OptimizeReadFileExceptions.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class OptimizeReadFileExceptions extends IssuableSubscriptionVisitor {

    protected static final String MESSAGERULE = "Optimize Read File Exceptions";
    private static final Logger LOGGER = Loggers.get(OptimizeReadFileExceptions.class);
    private boolean isFileNotFoundException = false;

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.TRY_STATEMENT, Kind.NEW_CLASS);
    }

    @Override
    public void visitNode(Tree tree) {

        LOGGER.debug("***** visitNode METHOD - BEGIN");
        LOGGER.debug("associated interface : {}", tree.kind().getAssociatedInterface());
        if (tree.kind().getAssociatedInterface().equals(NewClassTree.class)) {
            LOGGER.debug("interface NewClassTree found");
            NewClassTree newClassTree = (NewClassTree) tree;
            LOGGER.debug("identifier : " + newClassTree.identifier().symbolType());
            if (newClassTree.identifier().symbolType().toString().equals("FileInputStream")) {
                LOGGER.debug("identifier 'FileInputStream' found");
                if (this.isFileNotFoundException) {
                    LOGGER.debug("FileNotFoundException OR IOException found => launching 'reportIssue'");
                    reportIssue(tree, MESSAGERULE);
                } else {
                    LOGGER.debug("FileNotFoundException OR IOException NOT found");
                }
            } else {
                LOGGER.debug("identifier 'FileInputStream' NOT found => No issue");
            }
        } else {
            LOGGER.debug("interface NewClassTree NOT found => cast to TryStatementTree");
            TryStatementTree tryStatementTree = (TryStatementTree) tree;
            List<CatchTree> catchTreeList = tryStatementTree.catches();
            LOGGER.debug("display CatchTree List");
            for (CatchTree catchTree : catchTreeList) {
                LOGGER.debug("catchTree : " + catchTree.parameter().type().symbolType());
            }
            LOGGER.debug("compute isFileNotFoundException");
            this.isFileNotFoundException = isManagedException(catchTreeList);
            LOGGER.debug("isFileNotFoundException : " + isFileNotFoundException);
        }
        LOGGER.debug("***** visitNode METHOD - END");
    }

    private boolean isManagedException(List<CatchTree> catchTreeList) {
        return catchTreeList.stream().anyMatch(catchTree ->
                catchTree.parameter().type().symbolType().toString().equals("FileNotFoundException")
                        || catchTree.parameter().type().symbolType().toString().equals("IOException")
                        || catchTree.parameter().type().symbolType().toString().equals("Exception")
                        || catchTree.parameter().type().symbolType().toString().equals("Throwable")
        );
    }
}
