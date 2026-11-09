package model;

/**
 * One question of the game. Only the question PARAMETERS are stored here,
 * never the answer - the answer is always calculated by MathService.
 */
public class Question {

    public enum Type { PRIME, MOD, SIN, COS, TAN }

    private final Type type;
    private final int a;          // number (prime) or dividend (mod)
    private final int b;          // divisor (mod)
    private final double degree;  // angle in degrees (trigonometry)
    private final int marks;

    public Question(Type type, int a, int b, double degree, int marks) {
        this.type = type;
        this.a = a;
        this.b = b;
        this.degree = degree;
        this.marks = marks;
    }

    public static Question prime(int number)           { return new Question(Type.PRIME, number, 0, 0, 2); }
    public static Question mod(int a, int b)           { return new Question(Type.MOD, a, b, 0, 2); }
    public static Question trig(Type t, double degree) { return new Question(t, 0, 0, degree, 3); }

    public Type getType()     { return type; }
    public int getA()         { return a; }
    public int getB()         { return b; }
    public double getDegree() { return degree; }
    public int getMarks()     { return marks; }

    public boolean isTrig() {
        return type == Type.SIN || type == Type.COS || type == Type.TAN;
    }

    public String getTopic() {
        switch (type) {
            case PRIME: return "Prime Number";
            case MOD:   return "Modulus Operation";
            default:    return "Trigonometry";
        }
    }

    public String getText() {
        switch (type) {
            case PRIME:
                return "Is " + a + " a prime number?";
            case MOD:
                return "Calculate the remainder: " + a + " % " + b + " = ?";
            case SIN:
                return "Calculate sin(" + (int) degree + "\u00B0)";
            case COS:
                return "Calculate cos(" + (int) degree + "\u00B0)";
            default:
                return "Calculate tan(" + (int) degree + "\u00B0)";
        }
    }

    public String getHint() {
        switch (type) {
            case PRIME: return "Enter 1 if prime, 0 if not prime.";
            case MOD:   return "Enter an integer.";
            default:    return "Angle is in degrees. Give your answer to 2-3 decimal places.";
        }
    }
}
