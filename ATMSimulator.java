import java.util.Scanner;

public class ATMSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double balance = 0; // Initial balance
        boolean continueBanking = true;

        System.out.println("Welcome to the ATM Simulator!");

        while (continueBanking) {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Check balance
                    System.out.println("Your current balance is: $" + balance);
                    break;
                case 2: // Deposit money
                    System.out.print("Enter the amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    if (depositAmount > 0) {
                        balance += depositAmount;
                        System.out.println("Successfully deposited $" + depositAmount);
                        System.out.println("Your new balance is: $" + balance);
                    } else {
                        System.out.println("Invalid amount. Please enter a positive value.");
                    }
                    break;
                case 3: // Withdraw money
                    System.out.print("Enter the amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    if (withdrawAmount > 0 && withdrawAmount <= balance) {
                        balance -= withdrawAmount;
                        System.out.println("Successfully withdrew $" + withdrawAmount);
                        System.out.println("Your new balance is: $" + balance);
                    } else if (withdrawAmount > balance) {
                        System.out.println("Insufficient balance. Transaction declined.");
                    } else {
                        System.out.println("Invalid amount. Please enter a positive value.");
                    }
                    break;
                case 4: // Exit
                    System.out.println("Thank you for using the ATM Simulator. Goodbye!");
                    continueBanking = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose an option between 1 and 4.");
            }
        }

        scanner.close();
    }
}
