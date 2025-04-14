package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Простой калькулятор выражений");
        System.out.println("Поддерживает: +, -, *, /, скобки, переменные и функции sin, cos, sqrt");
        System.out.print("Введите выражение: ");
        String expr = scanner.nextLine();

        // Находим переменные (исключая функции)
        String varExpr = expr.replaceAll("sin\\(", "")
                .replaceAll("cos\\(", "")
                .replaceAll("sqrt\\(", "");

        for (int i = 0; i < varExpr.length(); i++) {
            if (Character.isLetter(varExpr.charAt(i))) {
                StringBuilder var = new StringBuilder();
                while (i < varExpr.length() && Character.isLetter(varExpr.charAt(i))) {
                    var.append(varExpr.charAt(i++));
                }
                if (!calculator.variables.containsKey(var.toString())) {
                    System.out.print("Введите значение для " + var + ": ");
                    double value = scanner.nextDouble();
                    calculator.addVariable(var.toString(), value);
                }
                i--;
            }
        }

        try {
            double result = calculator.evaluate(expr);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}

