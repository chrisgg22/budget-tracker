/**
 * Expense.java
 *
 * Represents ONE expense, like "$12.50 on lunch, category Food".
 *
 * @author Christophe Guirguis
 */
public class Expense {

    // The data every expense carries (called "fields")
    private String description; // e.g. "Lunch"
    private double amount; // e.g. 12.50
    private String category; // e.g. "Food"

    // Constructor: runs when you create a new Expense
    public Expense(String description, double amount, String category) {
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