package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rule(key = "EC7",
        name = "Developpement",
        description = AvoidReturningSpringEntityInRestController.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidReturningSpringEntityInRestController extends IssuableSubscriptionVisitor {

    protected static final String RULE_MESSAGE = "Avoid returning a persistence Entity in a Spring RestController";
    private static final String SPRING_REST_CONTROLLER = "org.springframework.web.bind.annotation.RestController";
    private static final String JPA_ENTITY = "javax.persistence.Entity";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        var annotations = classTree.symbol().metadata().annotations();
        if (containsAnnotation(annotations, SPRING_REST_CONTROLLER))
            reportMethodsReturningEntity(classTree);
    }

    private boolean containsAnnotation(List<SymbolMetadata.AnnotationInstance> annotations,
                                       String annotationClass) {
        return annotations != null
                && !annotations.isEmpty()
                && annotations.stream().anyMatch(annotation ->
                annotation.symbol().type().is(annotationClass));
    }

    private void reportMethodsReturningEntity(ClassTree classTree) {
        List<MethodTree> methods = classTree.members()
                .stream()
                .filter(member -> member.is(Tree.Kind.METHOD))
                .map(MethodTree.class::cast)
                .filter(method -> method.symbol().isPublic())
                .collect(Collectors.toList());

        methods.stream().filter(methodTree -> {
            var returnType = methods.get(0).returnType().symbolType();
            var parameterizedTypes = returnType.typeArguments();
            return Stream.concat(Stream.of(returnType), parameterizedTypes.stream())
                    .anyMatch(type -> containsAnnotation(type.symbol().metadata().annotations(), JPA_ENTITY));
        }).forEach(methodTree -> reportIssue(methodTree.returnType(), RULE_MESSAGE));
    }
}
