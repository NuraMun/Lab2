package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
/**
 * Простой калькулятор математических выражений
 * Поддерживает: +, -, *, /, скобки, переменные и функции sin, cos, sqrt
 */
public class Calculator {
    Map<String, Double> variables = new HashMap<>();

    public double evaluate(String expression) throws IllegalArgumentException {
        try {
            // Сначала заменяем все переменные
            for (Map.Entry<String, Double> entry : variables.entrySet()) {
                expression = expression.replace(entry.getKey(), entry.getValue().toString());
            }

            // Затем вычисляем выражение
            return evaluateExpression(expression);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка вычисления: " + e.getMessage());
        }
    }

    private double evaluateExpression(String expr) {
        expr = expr.replaceAll("\\s+", "");
        Stack<Double> numbers = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (c == ' ') continue;

            // Обработка чисел
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    num.append(expr.charAt(i++));
                }
                i--;
                numbers.push(Double.parseDouble(num.toString()));
            }
            // Обработка функций
            else if (Character.isLetter(c)) {
                StringBuilder funcName = new StringBuilder();
                while (i < expr.length() && Character.isLetter(expr.charAt(i))) {
                    funcName.append(expr.charAt(i++));
                }

                if (i >= expr.length() || expr.charAt(i) != '(') {
                    throw new IllegalArgumentException("Неизвестная переменная: " + funcName);
                }

                // Пропускаем '('
                i++;
                int start = i;
                int parenCount = 1;
                while (i < expr.length() && parenCount > 0) {
                    if (expr.charAt(i) == '(') parenCount++;
                    if (expr.charAt(i) == ')') parenCount--;
                    if (parenCount > 0) i++;
                }

                if (parenCount != 0) {
                    throw new IllegalArgumentException("Несбалансированные скобки в функции " + funcName);
                }

                String argStr = expr.substring(start, i);
                double arg = evaluateExpression(argStr);

                // Вычисляем функцию
                switch (funcName.toString()) {
                    case "sin":
                        numbers.push(Math.sin(arg));
                        break;
                    case "cos":
                        numbers.push(Math.cos(arg));
                        break;
                    case "sqrt":
                        if (arg < 0) throw new IllegalArgumentException("Корень из отрицательного числа");
                        numbers.push(Math.sqrt(arg));
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестная функция: " + funcName);
                }
            }
            // Обработка операторов и скобок
            else if (c == '(') {
                ops.push(c);
            }
            else if (c == ')') {
                while (ops.peek() != '(') {
                    numbers.push(applyOp(ops.pop(), numbers.pop(), numbers.pop()));
                }
                ops.pop();
            }
            else if (isOperator(c)) {
                while (!ops.isEmpty() && hasPrecedence(c, ops.peek())) {
                    numbers.push(applyOp(ops.pop(), numbers.pop(), numbers.pop()));
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            numbers.push(applyOp(ops.pop(), numbers.pop(), numbers.pop()));
        }

        if (numbers.size() != 1) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        return numbers.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private double applyOp(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Деление на ноль");
                return a / b;
            default: throw new IllegalArgumentException("Неизвестный оператор: " + op);
        }
    }

    public void addVariable(String name, double value) {
        variables.put(name, value);
    }
}
