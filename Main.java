import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Main.java
 *
 * A simple menu: add, view, edit, and delete expenses, see the total, and see
 * the total for each category.
 *
 * @author Christophe Guirguis
 */
public class Main {

    public static void main(String[] args) {

	Scanner scanner = new Scanner(System.in); // reads what you type

	ExpenseDatabase.createTable(); // make sure the table exists
	ArrayList<Expense> expenses = ExpenseDatabase.getAllExpenses(); // load saved expenses
	boolean running = true;

	while (running) {
	    System.out.println();
	    System.out.println("=== Budget Tracker ===");
	    System.out.println("1. Add an expense");
	    System.out.println("2. View all expenses");
	    System.out.println("3. Show total spent");
	    System.out.println("4. Show total per category");
	    System.out.println("5. Edit an expense");
	    System.out.println("6. Delete an expense");
	    System.out.println("7. Quit");
	    System.out.print("Choose an option: ");

	    String choice = scanner.nextLine();

	    if (choice.equals("1")) {
		System.out.print("Description: ");
		String description = scanner.nextLine();

		double amount = readAmount(scanner);

		System.out.print("Category: ");
		String category = scanner.nextLine();

		ExpenseDatabase.addExpense(new Expense(description, amount, category));
		expenses = ExpenseDatabase.getAllExpenses(); // reload so every expense has its id
		System.out.println("Expense added!");

	    } else if (choice.equals("2")) {
		if (expenses.isEmpty()) {
		    System.out.println("No expenses yet.");
		} else {
		    printExpenses(expenses);
		}

	    } else if (choice.equals("3")) {
		double total = 0;
		for (Expense e : expenses) {
		    total += e.getAmount();
		}
		System.out.printf("Total spent: $%.2f%n", total);

	    } else if (choice.equals("4")) {
		// Maps each category name to its running total
		HashMap<String, Double> totals = new HashMap<>();

		for (Expense e : expenses) {
		    String category = e.getCategory();
		    double soFar = totals.getOrDefault(category, 0.0);
		    totals.put(category, soFar + e.getAmount());
		}

		if (totals.isEmpty()) {
		    System.out.println("No expenses yet.");
		}
		for (String category : totals.keySet()) {
		    System.out.printf("%s: $%.2f%n", category, totals.get(category));
		}

	    } else if (choice.equals("5")) {
		Expense chosen = pickExpense(scanner, expenses, "edit");

		if (chosen != null) {
		    System.out.println("Press Enter on any question to keep the current value.");

		    System.out.print("New description (currently \"" + chosen.getDescription() + "\"): ");
		    String description = scanner.nextLine();
		    if (description.trim().isEmpty()) {
			description = chosen.getDescription();
		    }

		    double amount = readAmountOrKeep(scanner, chosen.getAmount());

		    System.out.print("New category (currently \"" + chosen.getCategory() + "\"): ");
		    String category = scanner.nextLine();
		    if (category.trim().isEmpty()) {
			category = chosen.getCategory();
		    }

		    // Same id, new details
		    ExpenseDatabase.updateExpense(new Expense(chosen.getId(), description, amount, category));
		    expenses = ExpenseDatabase.getAllExpenses();
		    System.out.println("Expense updated!");
		}

	    } else if (choice.equals("6")) {
		Expense chosen = pickExpense(scanner, expenses, "delete");

		if (chosen != null) {
		    System.out.print("Delete \"" + chosen + "\"? (y/n): ");
		    String answer = scanner.nextLine();

		    if (answer.trim().equalsIgnoreCase("y")) {
			ExpenseDatabase.deleteExpense(chosen.getId());
			expenses = ExpenseDatabase.getAllExpenses();
			System.out.println("Expense deleted.");
		    } else {
			System.out.println("Cancelled.");
		    }
		}

	    } else if (choice.equals("7")) {
		running = false;
		System.out.println("Goodbye!");

	    } else {
		System.out.println("Please enter a number from 1 to 7.");
	    }
	}

	scanner.close();
    }

    // Prints the expenses as a numbered list: 1. Lunch | $12.50 | Food
    private static void printExpenses(ArrayList<Expense> expenses) {
	for (int i = 0; i < expenses.size(); i++) {
	    System.out.println((i + 1) + ". " + expenses.get(i));
	}
    }

    // Shows the list and asks which one. Returns null if cancelled or invalid.
    private static Expense pickExpense(Scanner scanner, ArrayList<Expense> expenses, String action) {
	if (expenses.isEmpty()) {
	    System.out.println("No expenses yet.");
	    return null;
	}

	printExpenses(expenses);
	System.out.print("Enter the number of the expense to " + action + " (0 to cancel): ");

	try {
	    int number = Integer.parseInt(scanner.nextLine().trim());

	    if (number == 0) {
		return null; // cancelled
	    }
	    if (number < 1 || number > expenses.size()) {
		System.out.println("There is no expense with that number.");
		return null;
	    }
	    return expenses.get(number - 1); // list positions start at 0
	} catch (NumberFormatException ex) {
	    System.out.println("Please enter a number.");
	    return null;
	}
    }

    // Keeps asking until the user types a valid positive number
    private static double readAmount(Scanner scanner) {
	while (true) {
	    System.out.print("Amount: ");
	    try {
		double amount = Double.parseDouble(scanner.nextLine());
		if (amount > 0) {
		    return amount;
		}
		System.out.println("Amount must be greater than 0.");
	    } catch (NumberFormatException ex) {
		System.out.println("Please enter a number, like 12.50");
	    }
	}
    }

    // Like readAmount, but pressing Enter keeps the current amount
    private static double readAmountOrKeep(Scanner scanner, double current) {
	while (true) {
	    System.out.printf("New amount (currently $%.2f): ", current);
	    String input = scanner.nextLine().trim();

	    if (input.isEmpty()) {
		return current;
	    }
	    try {
		double amount = Double.parseDouble(input);
		if (amount > 0) {
		    return amount;
		}
		System.out.println("Amount must be greater than 0.");
	    } catch (NumberFormatException ex) {
		System.out.println("Please enter a number, like 12.50");
	    }
	}
    }
}
