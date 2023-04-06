/**
 * @author Mohamed Oussama A.
 * Created on 05/04/2023
 * @version 1.0
 */
public class AvoidBasicOperationsUsageMethods {

    private int x =0;

    /*
    * Below method must be reported by an other Rule of unnecessarily assignment of variable
    * (but not with this rule - SRP - SOLID )
    * */

    public static int getMin(int a, int b) {
        int c = a < b ? a : b; // Compliant
        return c;
    }

    public int getX() {
        return x; // Compliant
    }

    /*
     * Conditional expressions using ternanry operator
     */
    public static int getMin(int a, int b) {
        return a < b ? a : b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int calculateFormuleX(int a, int b) {
        return a / a * b < b ? a - b : b + a; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    /*
     * Binary Operations
     */

    //Using Arithmetic Operators

    public static int add(int a, int b) {
        return a + b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int substract(int a, int b) {
        return a - b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int multiply(int a, int b) {
        return a * b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int divide(int a, int b) {
        return a / b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int modulus(int a, int b) {
        return a % b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    //Using Logical Operators

    public static boolean compareAnd(int a, int b) {
        return (a > b) && (b > 0); // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static boolean compareOr(int a, int b) {
        return (a > b) || (b > 0); // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static boolean equalsTo(int a, int b) {
        return a == b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static boolean notEqualsTo(int a, int b) {
        return !(a == b); // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static boolean compare1(int a, int b) {
        return (a > b) && (b > 0); // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    // Using Bitwise operators

    public static boolean andBitwise(int a, int b) {
        return a & b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static boolean orBitwise(int a, int b) {
        return a | b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static boolean xorBitwise(int a, int b) {
        return a ^ b; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    /*
     * Unary Operations
     */

    public static int negate(int a) {
        return -a; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int preIncrement(int a) {
        return ++a; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int postDecrement(int a) {
        return a--; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int notBitwise(int a) {
        return ~a; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    // Using Bitwise operators

    public static int negate(int a) {
        return -a; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int preIncrement(int a) {
        return ++a; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int postDecrement(int a) {
        return a--; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int leftShift(int a) {
        return a << 1; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int rightShift(int a) {
        return a >> 1; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

    public static int zeroFillRightShift(int a) {
        return a >>> 1; // Noncompliant {{Avoid Method Usage For Only Basic Operations}}
    }

}