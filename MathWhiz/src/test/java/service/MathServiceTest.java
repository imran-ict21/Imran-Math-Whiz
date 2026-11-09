package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** TASK 09 - unit tests for the mathematical functions. Run: mvn test */
class MathServiceTest {

    // ---- Prime
    @Test void primeSeven()        { assertEquals(1, MathService.isPrime(7)); }
    @Test void primeEight()        { assertEquals(0, MathService.isPrime(8)); }
    @Test void primeOne()          { assertEquals(0, MathService.isPrime(1)); }
    @Test void primeZeroNegative() { assertEquals(0, MathService.isPrime(0)); assertEquals(0, MathService.isPrime(-7)); }
    @Test void primeTwoAndLarge()  { assertEquals(1, MathService.isPrime(2)); assertEquals(1, MathService.isPrime(97)); assertEquals(0, MathService.isPrime(100)); }

    // ---- Modulus
    @Test void modBasic()          { assertEquals(2, MathService.calculateMod(17, 5)); }
    @Test void modNegative()       { assertEquals(-2, MathService.calculateMod(-17, 5)); assertEquals(2, MathService.calculateMod(17, -5)); }
    @Test void modZeroDividend()   { assertEquals(0, MathService.calculateMod(0, 5)); }
    @Test void modZeroDivisor()    { assertThrows(IllegalArgumentException.class, () -> MathService.calculateMod(10, 0)); }

    // ---- Trigonometry
    @Test void sin30()             { assertEquals(0.5, MathService.calculateSin(30), 1e-9); }
    @Test void cos60()             { assertEquals(0.5, MathService.calculateCos(60), 1e-9); }
    @Test void tan45()             { assertEquals(1.0, MathService.calculateTan(45), 1e-9); }
    @Test void sin90cos0()         { assertEquals(1.0, MathService.calculateSin(90), 1e-9); assertEquals(1.0, MathService.calculateCos(0), 1e-9); }
    @Test void pythagoras()        { double s = MathService.calculateSin(37), c = MathService.calculateCos(37); assertEquals(1.0, s * s + c * c, 1e-9); }
    @Test void tanUndefined()      {
        assertThrows(ArithmeticException.class, () -> MathService.calculateTan(90));
        assertThrows(ArithmeticException.class, () -> MathService.calculateTan(270));
        assertThrows(ArithmeticException.class, () -> MathService.calculateTan(-90));
    }

    // ---- Tolerance
    @Test void tolerance()         { assertTrue(MathService.isClose(0.866, 0.87)); assertFalse(MathService.isClose(0.866, 0.9)); }
}
