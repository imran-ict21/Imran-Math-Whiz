package service;

/**
 * Pure mathematical functions of the game (TASK 03, 04, 05).
 * Every answer in the game is calculated here - nothing is hard-coded.
 */
public final class MathService {

    /** Allowed difference when comparing decimal (trigonometric) answers. */
    public static final double TOLERANCE = 0.01;

    private MathService() { }

    /**
     * TASK 03 - Prime number check.
     * @return 1 if the number is prime, otherwise 0 (numbers below 2 are not prime)
     */
    public static int isPrime(int number) {
        if (number < 2) {
            return 0;
        }
        for (long i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * TASK 04 - Modulus (remainder of integer division).
     * @throws IllegalArgumentException if the divisor is zero
     */
    public static int calculateMod(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor must not be zero.");
        }
        return a % b;
    }

    /** TASK 05 - sine of an angle given in DEGREES. */
    public static double calculateSin(double degree) {
        return Math.sin(Math.toRadians(degree));
    }

    /** TASK 05 - cosine of an angle given in DEGREES. */
    public static double calculateCos(double degree) {
        return Math.cos(Math.toRadians(degree));
    }

    /**
     * TASK 05 - tangent of an angle given in DEGREES.
     * @throws ArithmeticException where tangent is undefined (90, 270, -90 ... degrees)
     */
    public static double calculateTan(double degree) {
        double normalized = ((degree % 180) + 180) % 180;   // bring into [0, 180)
        if (Math.abs(normalized - 90) < 1e-9) {
            throw new ArithmeticException("tan(" + degree + "\u00B0) is undefined.");
        }
        return Math.tan(Math.toRadians(degree));
    }

    /** Compare two decimal numbers using a tolerance. */
    public static boolean isClose(double expected, double actual) {
        return Math.abs(expected - actual) <= TOLERANCE;
    }
}
