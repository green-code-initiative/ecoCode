package fr.cnumr.java.checks;


import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import java.util.Arrays;
import java.util.List;

@Rule(
        key = "GRSP0028",
        name = "Developpement",
        description = OptimizeReadFileExceptions.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class OptimizeReadFileExceptions extends IssuableSubscriptionVisitor {

	protected static final String MESSAGERULE = "Optimize Read File Exceptions";
    private boolean isFileNotFoundException = false;
	
    @Override
    public List<Kind> nodesToVisit() {
        return  Arrays.asList(Kind.TRY_STATEMENT, Kind.NEW_CLASS);
    }

    @Override
    public void visitNode(Tree tree) {

        if(tree.kind().getAssociatedInterface().equals(NewClassTree.class) && this.isFileNotFoundException){
            NewClassTree newClassTree = (NewClassTree) tree;
            if(newClassTree.identifier().symbolType().toString().equals("FileInputStream")){
                reportIssue(tree, MESSAGERULE);
            }
        }else{
            TryStatementTree tryStatementTree = (TryStatementTree) tree;
            List<CatchTree> catchTreeList = tryStatementTree.catches();
            this.isFileNotFoundException = catchTreeList.stream().anyMatch(catchTree -> catchTree.parameter().type().symbolType().toString().equals("FileNotFoundException"));
        }
        return;
    }
}
