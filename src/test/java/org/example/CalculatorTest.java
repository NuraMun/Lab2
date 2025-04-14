package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        calculator.addVariable("x", 4.0);
        calculator.addVariable("y", 9.0);
        calculator.addVariable("pi", Math.PI);
    }

    // Тесты базовых операций
    @Test
    void testAddition() {
        assertEquals(5.0, calculator.evaluate("2 + 3"));
    }

    @Test
    void testSubtraction() {
        assertEquals(1.0, calculator.evaluate("3 - 2"));
    }

    @Test
    void testMultiplication() {
        assertEquals(6.0, calculator.evaluate("2 * 3"));
    }

    @Test
    void testDivision() {
        assertEquals(2.5, calculator.evaluate("5 / 2"));
    }

    // Тесты со скобками
    @Test
    void testParentheses() {
        assertEquals(20.0, calculator.evaluate("(1+3)*5"));
    }

    @Test
    void testNestedParentheses() {
        assertEquals(4.0, calculator.evaluate("((1+1))*(1+1)"));
    }

    // Тесты переменных
    @Test
    void testVariables() {
        assertEquals(13.0, calculator.evaluate("x + y"));
    }

    @Test
    void testVariableInComplexExpression() {
        assertEquals(17.0, calculator.evaluate("(x + y) + 4"));
    }

    // Тесты функций
    @Test
    void testSinFunction() {
        assertEquals(Math.sin(1.0), calculator.evaluate("sin(1)"));
    }

    @Test
    void testCosFunction() {
        assertEquals(Math.cos(1.0), calculator.evaluate("cos(1)"));
    }

    @Test
    void testSqrtFunction() {
        assertEquals(3.0, calculator.evaluate("sqrt(y)"));
    }

    @Test
    void testFunctionWithVariable() {
        assertEquals(Math.sin(4.0), calculator.evaluate("sin(x)"));
    }

    @Test
    void testNestedFunctions() {
        assertEquals(Math.sin(Math.PI/2), calculator.evaluate("sin(pi/2)"));
    }

    // Тесты ошибок
    @Test
    void testInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("2 + "));
    }

    @Test
    void testUnbalancedParentheses() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("(2 + 3"));
    }

    @Test
    void testDivisionByZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("5 / 0"));
    }
    @Test
    void testDivisionByZeroWithMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.evaluate("5 / 0"));

        assertTrue(exception.getMessage().contains("Деление на ноль"));
    }
    @Test
    void testNegativeSqrt() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("sqrt(-1)"));
    }

    @Test
    void testUnknownVariable() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("a + 1"));
    }

    @Test
    void testUnknownFunction() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("tan(1)"));
    }

    // Комплексные тесты
    @Test
    void testComplexExpression1() {
        assertEquals((4.0 + 9.0) * Math.sqrt(4.0), calculator.evaluate("(x + y) * sqrt(x)"));
    }

    @Test
    void testComplexExpression2() {
        double expected = Math.sin(Math.PI/2) + Math.cos(0) + Math.sqrt(16);
        assertEquals(expected, calculator.evaluate("sin(pi/2) + cos(0) + sqrt(16)"));
    }

    @Test
    void testExpressionWithSpaces() {
        assertEquals(6.0, calculator.evaluate(" 2   + 4 "));
    }
}