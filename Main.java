import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Main.java
 *
 * A simple menu: add expenses, view them, see the total, and see the total for
 * each category.
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
	    System.out.println("5. Quit");
	    System.out.print("Choose an option: ");

	    String choice = scanner.nextLine();

	    if (choice.equals("1")) {
		System.out.print("Description: ");
		String description = scanner.nextLine();

		// Keep asking until the user types a valid positive number
		double amount = 0;
		boolean validAmount = false;
		while (!validAmount) {
		    System.out.print("Amount: ");
		    try {
			amount = Double.parseDouble(scanner.nextLine());
			if (amount <= 0) {
			    System.out.println("Amount must be greater than 0.");
			} else {
			    validAmount = true;
			}
		    } catch (NumberFormatException ex) {
			System.out.println("Please enter a number, like 12.50");
		    }
		}

		System.out.print("Category: ");
		String category = scanner.nextLine();

		Expense newExpense = new Expense(description, amount, category);
		expenses.add(newExpense);
		ExpenseDatabase.addExpense(newExpense); // save it in the database
		System.out.println("Expense added!");

	    } else if (choice.equals("2")) {
		if (expenses.isEmpty()) {
		    System.out.println("No expenses yet.");
		}
		for (Expense e : expenses) {
		    System.out.println(e);
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
		running = false;
		System.out.println("Goodbye!");

	    } else {
		System.out.println("Please enter 1, 2, 3, 4, or 5.");
	    }
	}

	scanner.close();
    }
}