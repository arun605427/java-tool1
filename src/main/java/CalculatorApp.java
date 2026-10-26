```java
import java.util.Objects;

public class CalculatorApp {
    private static class Display {
        String text = "0";
    }

    private static class CalculatorState {
        String currentInput = "";
        double firstNumber = 0;
        String operator = "";
        boolean startNewNumber = true;
    }

    public static void main(String[] args) {
        Display display = new Display();
        CalculatorState state = new CalculatorState();

        // Simulating UI logic in console mode for validation
        // Input loop to simulate button presses
        // Since this is Java SE (no JavaFX), we simulate with simple console commands

        // Example usage (command-line interaction simulation):
        // Press buttons in sequence like: '7', '8', '+', '3', '=' -> output: "81"
        // Instead of JavaFX, we'll simulate button presses via command line interaction:

        System.out.println("Calculator App (Console Mode)");
        System.out.println("Supported operations: +, -, *, /, %, ±, C");
        System.out.println("Commands: C (clear), Q (quit)");
        System.out.println("Enter expression (e.g., '7+3=') or button names one at a time.");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("Input: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("EXIT")) {
                break;
            }
            if (input.endsWith("=")) {
                String expr = input.substring(0, input.length() - 1);
                for (char c : expr.toCharArray()) {
                    processButton(String.valueOf(c), display, state);
                }
                processButton("=", display, state);
            } else {
                processButton(input, display, state);
            }
            System.out.println("Display: " + display.text);
        }
        System.out.println("Calculator exited.");
    }

    private static void processButton(String text, Display display, CalculatorState state) {
        switch (text) {
            case "C" -> {
                state.currentInput = "";
                state.firstNumber = 0;
                state.operator = "";
                state.startNewNumber = true;
                display.text = "0";
            }
            case "±" -> {
                if (!state.currentInput.isEmpty() && !state.currentInput.equals("0")) {
                    if (state.currentInput.startsWith("-")) {
                        state.currentInput = state.currentInput.substring(1);
                    } else {
                        state.currentInput = "-" + state.currentInput;
                    }
                    display.text = state.currentInput;
                }
            }
            case "%" -> {
                if (!state.currentInput.isEmpty()) {
                    double value = Double.parseDouble(state.currentInput) / 100;
                    state.currentInput = format(value);
                    display.text = state.currentInput;
                }
            }
            case "+", "−", "×", "÷" -> {
                if (!state.currentInput.isEmpty()) {
                    state.firstNumber = Double.parseDouble(state.currentInput);
                    state.operator = switchOperator(text);
                    state.startNewNumber = true;
                }
            }
            case "=" -> {
                if (!state.operator.isEmpty() && !state.currentInput.isEmpty()) {
                    double second = Double.parseDouble(state.currentInput);
                    double result = calculate(state.firstNumber, second, state.operator);
                    state.currentInput = format(result);
                    display.text = state.currentInput;
                    state.operator = "";
                    state.startNewNumber = true;
                }
            }
            case "." -> {
                if (state.startNewNumber) {
                    state.currentInput = "0.";
                    state.startNewNumber = false;
                } else if (!state.currentInput.contains(".")) {
                    state.currentInput += ".";
                }
                display.text = state.currentInput;
            }
            default -> {
                if (state.startNewNumber) {
                    state.currentInput = text;
                    state.startNewNumber = false;
                } else {
                    state.currentInput += text;
                }
                display.text = state.currentInput;
            }
        }
    }

    private static String switchOperator(String op) {
        return switch (op) {
            case "−" -> "-";
            case "×" -> "*";
            case "÷" -> "/";
            default -> op;
        };
    }

    private static double calculate(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> b == 0 ? 0 : a / b;
            default -> b;
        };
    }

    private static String format(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    // Utility for string representation of operations
    @Override
    public String toString() {
        return "CalculatorApp";
    }
}
```