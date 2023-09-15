package fr.greencodeinitiative.java.checks.enums;

import java.math.BigDecimal;
import java.util.Set;

import org.sonar.plugins.java.api.semantic.MethodMatchers;
import static org.sonar.plugins.java.api.semantic.MethodMatchers.create;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import static org.sonar.plugins.java.api.tree.Tree.Kind.BOOLEAN_LITERAL;
import static org.sonar.plugins.java.api.tree.Tree.Kind.CHAR_LITERAL;
import static org.sonar.plugins.java.api.tree.Tree.Kind.DOUBLE_LITERAL;
import static org.sonar.plugins.java.api.tree.Tree.Kind.FLOAT_LITERAL;
import static org.sonar.plugins.java.api.tree.Tree.Kind.INT_LITERAL;
import static org.sonar.plugins.java.api.tree.Tree.Kind.LONG_LITERAL;
import static org.sonar.plugins.java.api.tree.Tree.Kind.STRING_LITERAL;
import static org.sonar.plugins.java.api.tree.Tree.Kind.TYPE_CAST;
import org.sonar.plugins.java.api.tree.TypeCastTree;

public enum ConstOrLiteralDeclare {

    BOOLEAN {
        @Override
        String className() {
            return Boolean.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return Set.of("TRUE", "FALSE");
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    BYTE {
        @Override
        String className() {
            return Byte.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return NUMBER_DEFAULT_MEMBERS;
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    SHORT {
        @Override
        String className() {
            return Short.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return NUMBER_DEFAULT_MEMBERS;
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    INTEGER {
        @Override
        String className() {
            return Integer.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return NUMBER_DEFAULT_MEMBERS;
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    LONG {
        @Override
        String className() {
            return Long.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return NUMBER_DEFAULT_MEMBERS;
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    FLOAT {
        @Override
        String className() {
            return Float.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return NUMBER_DEFAULT_MEMBERS;
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    DOUBLE {
        @Override
        String className() {
            return Double.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return NUMBER_DEFAULT_MEMBERS;
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    CHARACTER {
        @Override
        String className() {
            return Character.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return NUMBER_DEFAULT_MEMBERS;
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    },

    BIGDECIMAL {
        @Override
        String className() {
            return BigDecimal.class.getName();
        }

        @Override
        Set<String> publicMembers() {
            return Set.of("ZERO", "ONE", "TEN");
        }

        @Override
        MethodMatchers methodMatchers() {
            return DEFAULT_METHOD_MATCHERS;
        }
    };

    public boolean isPublicMember(MemberSelectExpressionTree tree) {

        return className().equals(tree.expression().symbolType().fullyQualifiedName()) //strong check
                && publicMembers().contains(tree.identifier().toString());
    }

    public boolean isLiteralDeclare(MethodInvocationTree tree) {

        return methodMatchers().matches(tree)
                && tree.arguments().stream().allMatch(ConstOrLiteralDeclare::isLiteral);
    }

    abstract String className();

    abstract Set<String> publicMembers();

    abstract MethodMatchers methodMatchers();

    private static final Set<String> NUMBER_DEFAULT_MEMBERS = Set.of("MIN_VALUE", "MAX_VALUE");

    private static final MethodMatchers DEFAULT_METHOD_MATCHERS = create()
            .ofSubTypes(Number.class.getName(), Boolean.class.getName(), Character.class.getName()).names("valueOf")
            .addParametersMatcher(args -> !args.isEmpty()).build();

    public static final boolean isLiteral(Tree arg) {
        if (arg.is(TYPE_CAST)) {
            arg = ((TypeCastTree) arg).expression();
        }
        return arg.is(BOOLEAN_LITERAL) ||
                arg.is(INT_LITERAL) ||
                arg.is(LONG_LITERAL) ||
                arg.is(FLOAT_LITERAL) ||
                arg.is(DOUBLE_LITERAL) ||
                arg.is(STRING_LITERAL) ||
                arg.is(CHAR_LITERAL);
    }
}