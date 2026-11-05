/**
 * Expense.java
 *
 * Represents ONE expense, like "$12.50 on lunch, category Food".
 *
 * @author Christophe Guirguis
 */
public class Expense {

    // The data every expense carries (called "fields")
    private int id; // unique number from the database (0 = not saved yet)
    private String description; // e.g. "Lunch"
    private double amount; // e.g. 12.50
    private String category; // e.g. "Food"

    // Constructor for a NEW expense that isn't in the database yet
    public Expense(String description, double amount, String category) {
	this(0, description, amount, category);
    }

    // Constructor for an expense that already has an id (loaded from the database)
    public Expense(int id, String description, double amount, String category) {
	this.id = id;
	this.description = description;
	this.amount = amount;
	this.category = formatCategory(category); // tidy the category first
    }

    // Makes "food", "FOOD", and " Food " all become "Food"
    private static String formatCategory(String text) {
	text = text.trim(); // remove extra spaces at the ends
	if (text.isEmpty()) {
	    return text;
	}
	return text.substring(0, 1).toUpperCase() // first letter -> capital
		+ text.substring(1).toLowerCase(); // the rest -> lowercase
    }

    // Getters: let other classes READ the fields
    public int getId() {
	return id;
    }

    public String getDescription() {
	return description;
    }

    public double getAmount() {
	return amount;
    }

    public String getCategory() {
	return category;
    }

    // toString: how an Expense looks when printed
    @Override
    public String toString() {
	return String.format("%s | $%.2f | %s", description, amount, category);
    }
}
