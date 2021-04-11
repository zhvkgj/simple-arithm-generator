import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final int MAX_SIZE = 10;
    private static final ThreadLocalRandom current = ThreadLocalRandom.current();
    private static final int MAX_DEPTH = 13;
    private static int CURRENT_DEPTH = 0;

    private static class ArithmeticExpression {
        static String build() {
            return Disjunction.build();
        }
    }

    private static class Disjunction {
        static String build() {
            int d = current.nextInt(1, MAX_SIZE);
            var sb = new StringBuilder(Conjunction.build());
            for (int i = 1; i < d; i++) {
                CURRENT_DEPTH++;
                sb.append(" || ").append(Conjunction.build());
            }
            return sb.toString();
        }
    }


    private static class Conjunction {
        static String build() {
            int d = current.nextInt(1, MAX_SIZE);
            var sb = new StringBuilder(Equality.build());
            for (int i = 1; i < d; i++) {
                CURRENT_DEPTH++;
                sb.append(" && ").append(Equality.build());
            }
            return sb.toString();
        }
    }

    private static class Equality {
        static String build() {
            int d = current.nextInt(1, MAX_SIZE);
            var sb = new StringBuilder(Comparison.build());
            for (int i = 1; i < d; i++) {
                CURRENT_DEPTH++;
                sb
                        .append(" ")
                        .append(EqualityOp.build())
                        .append(" ")
                        .append(Comparison.build());
            }
            return sb.toString();
        }
    }

    private static class Comparison {
        static String build() {
            int d = current.nextInt(1, MAX_SIZE);
            var sb = new StringBuilder(AdditiveExpression.build());
            for (int i = 1; i < d; i++) {
                CURRENT_DEPTH++;
                sb
                        .append(" ")
                        .append(ComparisonOp.build())
                        .append(" ")
                        .append(AdditiveExpression.build());
            }
            return sb.toString();
        }
    }

    private static class AdditiveExpression {
        static String build() {
            int d = current.nextInt(1, MAX_SIZE);
            var sb = new StringBuilder(MultiplicativeExpression.build());
            for (int i = 1; i < d; i++) {
                CURRENT_DEPTH++;
                sb
                        .append(" ")
                        .append(AdditiveOp.build())
                        .append(" ")
                        .append(MultiplicativeExpression.build());
            }
            return sb.toString();
        }
    }

    private static class MultiplicativeExpression {
        static String build() {
            int d = current.nextInt(1, MAX_SIZE);
            var sb = new StringBuilder(PrefixUnaryExpression.build());
            for (int i = 1; i < d; i++) {
                CURRENT_DEPTH++;
                sb
                        .append(" ")
                        .append(MultiplicativeOp.build())
                        .append(" ")
                        .append(PrefixUnaryExpression.build());
            }
            return sb.toString();
        }
    }

    private static class PrefixUnaryExpression {
        static String build() {
            CURRENT_DEPTH++;
            return current.nextInt(1, Integer.MAX_VALUE) % 2 == 0
                    ? PrefixUnaryOp.build() + PostfixUnaryExpression.build()
                    : PostfixUnaryExpression.build();
        }
    }

    private static class PostfixUnaryExpression {
        static String build() {
            CURRENT_DEPTH++;
            return current.nextInt(1, Integer.MAX_VALUE) % 2 == 0
                    ? PrimaryExpression.build() + PostfixUnaryOp.build()
                    : PrimaryExpression.build();
        }
    }

    private static class PrimaryExpression {
        static String build() {
            CURRENT_DEPTH++;
            if (current.nextInt(1, Integer.MAX_VALUE) % 2 == 0 && CURRENT_DEPTH <= MAX_DEPTH) {
                return parenthesizedExpression();
            } else {
                return literalConstant();
            }
        }

        private static String literalConstant() {
            switch (current.nextInt(0,4)) {
                case 0:
                    return IntLiteral.build();
                case 1:
                    return FloatLiteral.build();
                case 2:
                    return BooleanLiteral.build();
                default:
                    return CharLiteral.build();
            }
        }

        private static String parenthesizedExpression() {
            return "(" + ArithmeticExpression.build() + ")";
        }
    }

    private static class IntLiteral {
        static String build() {
            return String.valueOf(current.nextInt());
        }
    }

    private static class FloatLiteral {
        static String build() {
            return String.valueOf(current.nextFloat());
        }
    }

    private static class BooleanLiteral {
        static String build() {
            return String.valueOf(current.nextBoolean());
        }
    }

    private static class CharLiteral {
        static String build() {
            return String.valueOf((char) (current.nextInt('z' - 'a' + 1) + 'a'));
        }
    }

    private enum EqualityOp {
        Equal("=="),
        NotEqu("!=");

        private final String value;

        EqualityOp(String s) {
            value = s;
        }

        public static String build() {
            EqualityOp[] values = EqualityOp.values();
            int i = current.nextInt(0, values.length);
            return values[i].value;
        }
    }

    private enum ComparisonOp {
        LT(">"),
        GT("<"),
        LE(">="),
        GE("<=");

        public final String value;

        ComparisonOp(String s) {
            value = s;
        }

        public static String build() {
            ComparisonOp[] values = ComparisonOp.values();
            int i = current.nextInt(0, values.length);
            return values[i].value;
        }
    }

    private enum AdditiveOp {
        PLUS("+"),
        MINUS("-");

        public final String value;

        AdditiveOp(String s) {
            value = s;
        }

        public static String build() {
            AdditiveOp[] values = AdditiveOp.values();
            int i = current.nextInt(0, values.length);
            return values[i].value;
        }
    }

    private enum MultiplicativeOp {
        MUL("*"),
        DIV("/"),
        MOD("%");

        public final String value;

        MultiplicativeOp(String s) {
            value = s;
        }

        public static String build() {
            MultiplicativeOp[] values = MultiplicativeOp.values();
            int i = current.nextInt(0, values.length);
            return values[i].value;
        }
    }

    private enum PrefixUnaryOp {
        PR_UN_MINUS("--"),
        PR_UN_PLUS("++");

        public final String value;

        PrefixUnaryOp(String s) {
            value = s;
        }

        public static String build() {
            PrefixUnaryOp[] values = PrefixUnaryOp.values();
            int i = current.nextInt(0, values.length);
            return values[i].value;
        }
    }

    private enum PostfixUnaryOp {
        PO_UN_MINUS("--"),
        PO_UN_PLUS("++");

        public final String value;

        PostfixUnaryOp(String s) {
            value = s;
        }

        public static String build() {
            PostfixUnaryOp[] values = PostfixUnaryOp.values();
            int i = current.nextInt(0, values.length);
            return values[i].value;
        }
    }

    public static void main(String[] args) {
        System.out.println(ArithmeticExpression.build());
        CURRENT_DEPTH = 0;
    }
}
