import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;

        System.out.println("Welcome to the Simple Calculator!");

        while (continueCalculation) {
            // Input numbers
            System.out.print("Enter the first number: ");
            double num1 = scanner.nextDouble();

            System.out.print("Enter the operator (+, -, *, /): ");
            char operator = scanner.next().charAt(0);

            System.out.print("Enter the second number: ");
            double num2 = scanner.nextDouble();

            // Perform calculation based on operator
            double result;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    System.out.println("The result is: " + result);
                    break;
                case '-':
                    result = num1 - num2;
                    System.out.println("The result is: " + result);
                    break;
                case '*':
                    result = num1 * num2;
                    System.out.println("The result is: " + result);
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                        System.out.println("The result is: " + result);
                    } else {
                        System.out.println("Error: Division by zero is not allowed.");
                    }
                    break;
                default:
                    System.out.println("Invalid operator! Please use +, -, *, or /.");
                    break;
            }

            // Ask if the user wants to perform another calculation
            System.out.print("Do you want to perform another calculation? (yes/no): ");
            String response = scanner.next();
            continueCalculation = response.equalsIgnoreCase("yes");
        }

        System.out.println("Thank you for using the Simple Calculator. Goodbye!");
        scanner.close();
    }
}
