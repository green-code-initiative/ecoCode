package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

@Rule(
        key = "EC_CRJVM205",
        name = "Developpement",
        description = UseFetchTypeLazyRule.MESSAGE_RULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class UseFetchTypeLazyRule extends BaseTreeVisitor implements JavaFileScanner {
    protected static final String MESSAGE_RULE = "Avoid Using FetchType.EAGER instead of FetchType.LAZY on collections in JPA Entity";

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext javaFileScannerContext) {
        this.context = javaFileScannerContext;
        // The call to the scan method on the root of the tree triggers the visit of the AST by this visitor
        scan(context.getTree());
    }

    @Override
    public void visitAnnotation(AnnotationTree annotationTree) {
        String annotationName = ((IdentifierTree) annotationTree.annotationType()).name();
        if (annotationName.equals("OneToMany")
                || annotationName.equals("ManyToMany")) {
            boolean fetchExist = false;
            ExpressionTree fetchTypeArg = null;

            for (ExpressionTree argument : annotationTree.arguments()) {
                if (argument.is(Tree.Kind.ASSIGNMENT)) {
                    AssignmentExpressionTree assignmentInvocation = (AssignmentExpressionTree) argument;
                    if (assignmentInvocation.variable().toString().equals("fetch")) {
                        fetchExist = true;
                        fetchTypeArg = assignmentInvocation.expression();
                    }
                }
            }

            this.reportFetchTypeIssue(fetchExist,fetchTypeArg,annotationTree);
        }
        super.visitAnnotation(annotationTree);
    }

    private void reportFetchTypeIssue(boolean fetchExist, ExpressionTree fetchTypeArg,AnnotationTree annotationTree){
        if (!fetchExist) {
            context.reportIssue(this, annotationTree, "JPA annotation without FetchType detected");
        } else if (fetchTypeArg != null) {
            String fetchType = ((MemberSelectExpressionTree) fetchTypeArg).identifier().name();
            if (!fetchType.strip().equals("LAZY")) {
                context.reportIssue(this, annotationTree, MESSAGE_RULE);
            }
        }
    }
}
