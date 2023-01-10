package fr.cnumr.python.checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.symbols.Symbol;
import org.sonar.plugins.python.api.tree.AnnotatedAssignment;
import org.sonar.plugins.python.api.tree.ArgList;
import org.sonar.plugins.python.api.tree.AssertStatement;
import org.sonar.plugins.python.api.tree.AssignmentExpression;
import org.sonar.plugins.python.api.tree.AssignmentStatement;
import org.sonar.plugins.python.api.tree.AwaitExpression;
import org.sonar.plugins.python.api.tree.BinaryExpression;
import org.sonar.plugins.python.api.tree.CallExpression;
import org.sonar.plugins.python.api.tree.CompoundAssignmentStatement;
import org.sonar.plugins.python.api.tree.ComprehensionExpression;
import org.sonar.plugins.python.api.tree.ComprehensionFor;
import org.sonar.plugins.python.api.tree.ComprehensionIf;
import org.sonar.plugins.python.api.tree.ConditionalExpression;
import org.sonar.plugins.python.api.tree.DictCompExpression;
import org.sonar.plugins.python.api.tree.DictionaryLiteral;
import org.sonar.plugins.python.api.tree.ElseClause;
import org.sonar.plugins.python.api.tree.ExceptClause;
import org.sonar.plugins.python.api.tree.ExecStatement;
import org.sonar.plugins.python.api.tree.ExpressionList;
import org.sonar.plugins.python.api.tree.ExpressionStatement;
import org.sonar.plugins.python.api.tree.FileInput;
import org.sonar.plugins.python.api.tree.FinallyClause;
import org.sonar.plugins.python.api.tree.ForStatement;
import org.sonar.plugins.python.api.tree.FunctionDef;
import org.sonar.plugins.python.api.tree.IfStatement;
import org.sonar.plugins.python.api.tree.KeyValuePair;
import org.sonar.plugins.python.api.tree.LambdaExpression;
import org.sonar.plugins.python.api.tree.ListLiteral;
import org.sonar.plugins.python.api.tree.Name;
import org.sonar.plugins.python.api.tree.Parameter;
import org.sonar.plugins.python.api.tree.ParameterList;
import org.sonar.plugins.python.api.tree.ParenthesizedExpression;
import org.sonar.plugins.python.api.tree.PrintStatement;
import org.sonar.plugins.python.api.tree.RaiseStatement;
import org.sonar.plugins.python.api.tree.RegularArgument;
import org.sonar.plugins.python.api.tree.ReprExpression;
import org.sonar.plugins.python.api.tree.ReturnStatement;
import org.sonar.plugins.python.api.tree.SetLiteral;
import org.sonar.plugins.python.api.tree.StatementList;
import org.sonar.plugins.python.api.tree.SubscriptionExpression;
import org.sonar.plugins.python.api.tree.Tree;
import org.sonar.plugins.python.api.tree.TryStatement;
import org.sonar.plugins.python.api.tree.Tuple;
import org.sonar.plugins.python.api.tree.TupleParameter;
import org.sonar.plugins.python.api.tree.UnaryExpression;
import org.sonar.plugins.python.api.tree.UnpackingExpression;
import org.sonar.plugins.python.api.tree.WhileStatement;
import org.sonar.plugins.python.api.tree.YieldExpression;
import org.sonar.plugins.python.api.tree.YieldStatement;

@Rule(
        key = AvoidGlobalVariableInFunctionCheck.RULE_KEY,
        name = "Developpement",
        description = AvoidGlobalVariableInFunctionCheck.DESCRIPTION,
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidGlobalVariableInFunctionCheck extends PythonSubscriptionCheck {

    public static final String RULE_KEY = "D4";
    public static final String DESCRIPTION = "Use local variable (function/class scope) instead of global variable (application scope)";

    private List<String> globalVariables;
    private List<String> definedLocalVariables;
    private Map<Tree, String> usedLocalVariables;

    @Override
    public void initialize(SubscriptionCheck.Context context) {
        globalVariables = new ArrayList<>();
        context.registerSyntaxNodeConsumer(Tree.Kind.FILE_INPUT, this::visitFileInput);
        context.registerSyntaxNodeConsumer(Tree.Kind.FUNCDEF, this::visitFuncDef);
    }

    public void visitFileInput(SubscriptionContext ctx) {
        FileInput fileInput = (FileInput) ctx.syntaxNode();
        fileInput.globalVariables().stream().filter(v -> v.is(Symbol.Kind.OTHER)).forEach(v -> this.globalVariables.add(v.name()));
    }

    void visitFuncDef(SubscriptionContext ctx) {
        this.definedLocalVariables = new ArrayList<>();
        this.usedLocalVariables = new HashMap<>();

        FunctionDef functionDef = (FunctionDef) ctx.syntaxNode();

        ParameterList parameterList = functionDef.parameters();
        if (parameterList != null) {
            parameterList.nonTuple().forEach(p -> extractVariablesFromExpression(p, true));
        }

        functionDef.body().statements()
                .forEach(s -> extractVariablesFromExpression(s, false));

        this.usedLocalVariables.entrySet().stream()
                .filter(e -> !this.definedLocalVariables.contains(e.getValue()) && this.globalVariables.contains(e.getValue()))
                .forEach(e -> ctx.addIssue(e.getKey(), DESCRIPTION));
    }

    void extractVariablesFromExpression(Tree element, boolean isAssigned) {
        if (element == null) {
            return;
        }

        switch (element.getKind()) {
            case REGULAR_ARGUMENT:
                extractVariablesFromExpression(((RegularArgument) element).expression(), isAssigned);
                break;
            case ARG_LIST:
                ((ArgList) element).arguments().forEach(a -> extractVariablesFromExpression(a, isAssigned));
                break;
            case ANNOTATED_ASSIGNMENT:
                AnnotatedAssignment annotatedAssignment = (AnnotatedAssignment) element;
                extractVariablesFromExpression(annotatedAssignment.variable(), true);
                extractVariablesFromExpression(annotatedAssignment.assignedValue(), false);
                break;
            case ASSERT_STMT:
                AssertStatement assertStatement = (AssertStatement) element;
                extractVariablesFromExpression(assertStatement.condition(), false);
                extractVariablesFromExpression(assertStatement.message(), false);
                break;
            case ASSIGNMENT_STMT:
                AssignmentStatement assignmentStatement = (AssignmentStatement) element;
                assignmentStatement.lhsExpressions().forEach(e -> extractVariablesFromExpression(e, true));
                extractVariablesFromExpression(assignmentStatement.assignedValue(), false);
                break;
            case CALL_EXPR:
                extractVariablesFromExpression(((CallExpression) element).argumentList(), isAssigned);
                break;
//            TODO : Check if usefull or not
//            case CLASSDEF:
//                (ClassDef) element;
//                break;
            case CONDITIONAL_EXPR:
                ConditionalExpression conditionalExpression = (ConditionalExpression) element;
                extractVariablesFromExpression(conditionalExpression.trueExpression(), isAssigned);
                extractVariablesFromExpression(conditionalExpression.falseExpression(), false);
                extractVariablesFromExpression(conditionalExpression.condition(), isAssigned);
                break;
            case COMPOUND_ASSIGNMENT:
                CompoundAssignmentStatement compoundAssignmentStatement = (CompoundAssignmentStatement) element;
                extractVariablesFromExpression(compoundAssignmentStatement.lhsExpression(), true);
                extractVariablesFromExpression(compoundAssignmentStatement.rhsExpression(), false);
                break;
            case DICTIONARY_LITERAL:
                ((DictionaryLiteral) element).elements().forEach(e -> extractVariablesFromExpression(e, false));
                break;
            case ELSE_CLAUSE:
                extractVariablesFromExpression(((ElseClause) element).body(), isAssigned);
                break;
            case EXCEPT_CLAUSE:
                extractVariablesFromExpression(((ExceptClause) element).body(), isAssigned);
                break;
            case EXEC_STMT:
                ExecStatement execStatement = (ExecStatement) element;
                extractVariablesFromExpression(execStatement.expression(), isAssigned);
                extractVariablesFromExpression(execStatement.globalsExpression(), isAssigned);
                extractVariablesFromExpression(execStatement.localsExpression(), isAssigned);
                break;
            case EXPRESSION_LIST:
                ((ExpressionList) element).expressions().forEach(e -> extractVariablesFromExpression(e, isAssigned));
                break;
            case EXPRESSION_STMT:
                ((ExpressionStatement) element).expressions().forEach(e -> extractVariablesFromExpression(e, isAssigned));
                break;
            case FILE_INPUT:
                extractVariablesFromExpression(((FileInput) element).statements(), isAssigned);
                break;
            case FINALLY_CLAUSE:
                extractVariablesFromExpression(((FinallyClause) element).body(), isAssigned);
                break;
            case FOR_STMT:
                ForStatement forStatement = ((ForStatement) element);
                forStatement.expressions().forEach(e -> extractVariablesFromExpression(e, true));
                forStatement.testExpressions().forEach(e -> extractVariablesFromExpression(e, false));
                extractVariablesFromExpression(forStatement.body(), isAssigned);
                extractVariablesFromExpression(forStatement.elseClause(), isAssigned);
                break;
            case IF_STMT:
                IfStatement ifStatement = (IfStatement) element;
                extractVariablesFromExpression(ifStatement.condition(), false);
                extractVariablesFromExpression(ifStatement.body(), isAssigned);
                extractVariablesFromExpression(ifStatement.elseBranch(), isAssigned);
                ifStatement.elifBranches().forEach(b -> extractVariablesFromExpression(b, isAssigned));
                break;
            case LAMBDA:
                extractVariablesFromExpression(((LambdaExpression) element).expression(), isAssigned);
                break;
            case LIST_LITERAL:
                extractVariablesFromExpression(((ListLiteral) element).elements(), false);
                break;
            case NAME:
                if (isAssigned) {
                    this.definedLocalVariables.add(((Name) element).name());
                } else {
                    this.usedLocalVariables.put(element, ((Name) element).name());
                }
                break;
            case PRINT_STMT:
                ((PrintStatement) element).expressions().forEach(e -> extractVariablesFromExpression(e, false));
                break;
            case RAISE_STMT:
                RaiseStatement raiseStatement = (RaiseStatement) element;
                extractVariablesFromExpression(raiseStatement.fromExpression(), false);
                raiseStatement.expressions().forEach(e -> extractVariablesFromExpression(e, false));
                break;
            case REPR:
                extractVariablesFromExpression(((ReprExpression) element).expressionList(), isAssigned);
                break;
            case RETURN_STMT:
                ((ReturnStatement) element).expressions().forEach(e -> extractVariablesFromExpression(e, false));
                break;
            case SET_LITERAL:
                ((SetLiteral) element).elements().forEach(e -> extractVariablesFromExpression(e, false));
                break;
            case STATEMENT_LIST:
                ((StatementList) element).statements().forEach(s -> extractVariablesFromExpression(s, isAssigned));
                break;
            case TRY_STMT:
                TryStatement tryStatement = (TryStatement) element;
                extractVariablesFromExpression(tryStatement.body(), isAssigned);
                tryStatement.exceptClauses().forEach(c -> extractVariablesFromExpression(c, isAssigned));
                extractVariablesFromExpression(tryStatement.elseClause(), isAssigned);
                extractVariablesFromExpression(tryStatement.finallyClause(), isAssigned);
                break;
            case PARAMETER:
                Parameter parameter = (Parameter) element;
                extractVariablesFromExpression(parameter.name(), true);
                extractVariablesFromExpression(parameter.defaultValue(), false);
                break;
            case TUPLE_PARAMETER:
                ((TupleParameter) element).parameters().forEach(p -> extractVariablesFromExpression(p, isAssigned));
                break;
            case PARAMETER_LIST:
                ((ParameterList) element).all().forEach(a -> extractVariablesFromExpression(a, true));
                break;
            case WHILE_STMT:
                WhileStatement whileStatement = (WhileStatement) element;
                extractVariablesFromExpression(whileStatement.condition(), false);
                extractVariablesFromExpression(whileStatement.body(), isAssigned);
                extractVariablesFromExpression(whileStatement.elseClause(), isAssigned);
                break;
            case YIELD_EXPR:
                ((YieldExpression) element).expressions().forEach(e -> extractVariablesFromExpression(e, isAssigned));
                break;
            case YIELD_STMT:
                extractVariablesFromExpression(((YieldStatement) element).yieldExpression(), isAssigned);
                break;
            case PARENTHESIZED:
                extractVariablesFromExpression(((ParenthesizedExpression) element).expression(), isAssigned);
                break;
            case UNPACKING_EXPR:
                extractVariablesFromExpression(((UnpackingExpression) element).expression(), isAssigned);
                break;
            case AWAIT:
                extractVariablesFromExpression(((AwaitExpression) element).expression(), false);
                break;
            case TUPLE:
                ((Tuple) element).elements().forEach(e -> extractVariablesFromExpression(e, isAssigned));
                break;
            case DICT_COMPREHENSION:
                extractVariablesFromExpression(((DictCompExpression) element).comprehensionFor(), false);
                break;
            case LIST_COMPREHENSION:
            case SET_COMPREHENSION:
            case GENERATOR_EXPR:
                extractVariablesFromExpression(((ComprehensionExpression) element).resultExpression(), false);
                extractVariablesFromExpression(((ComprehensionExpression) element).comprehensionFor(), true);
                break;
            case COMP_FOR:
                extractVariablesFromExpression(((ComprehensionFor) element).loopExpression(), true);
                extractVariablesFromExpression(((ComprehensionFor) element).iterable(), false);
                break;
            case COMP_IF:
                extractVariablesFromExpression(((ComprehensionIf) element).condition(), false);
                break;
            case SUBSCRIPTION:
                extractVariablesFromExpression(((SubscriptionExpression) element).object(), false);
                extractVariablesFromExpression(((SubscriptionExpression) element).subscripts(), false);
                break;
            case PLUS:
            case MINUS:
            case MULTIPLICATION:
            case DIVISION:
            case FLOOR_DIVISION:
            case MODULO:
            case MATRIX_MULTIPLICATION:
            case SHIFT_EXPR:
            case BITWISE_AND:
            case BITWISE_OR:
            case BITWISE_XOR:
            case AND:
            case OR:
            case COMPARISON:
            case POWER:
                BinaryExpression binaryExpression = (BinaryExpression) element;
                extractVariablesFromExpression(binaryExpression.leftOperand(), false);
                extractVariablesFromExpression(binaryExpression.rightOperand(), false);
                break;
            case UNARY_PLUS:
            case UNARY_MINUS:
            case BITWISE_COMPLEMENT:
            case NOT:
                extractVariablesFromExpression(((UnaryExpression) element).expression(), false);
                break;
            case ASSIGNMENT_EXPRESSION:
                AssignmentExpression assignmentExpression = (AssignmentExpression) element;
                extractVariablesFromExpression(assignmentExpression.lhsName(), true);
                extractVariablesFromExpression(assignmentExpression.expression(), false);
                break;
            case KEY_VALUE_PAIR:
                KeyValuePair keyValuePair = (KeyValuePair) element;
                extractVariablesFromExpression(keyValuePair.key(), true);
                extractVariablesFromExpression(keyValuePair.value(), false);
                break;
        }
    }
}
