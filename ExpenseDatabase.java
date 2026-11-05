import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * ExpenseDatabase.java
 *
 * Saves and loads expenses using a SQLite database (a single file:
 * expenses.db).
 *
 * @author Christophe Guirguis
 */
public class ExpenseDatabase {

    // Where the database lives. SQLite creates the file automatically.
    private static final String URL = "jdbc:sqlite:expenses.db";

    // Creates the "expenses" table if it doesn't exist yet
    public static void createTable() {
	String sql = "CREATE TABLE IF NOT EXISTS expenses (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ "description TEXT NOT NULL, " + "amount REAL NOT NULL, " + "category TEXT NOT NULL)";

	try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {
	    stmt.execute(sql);
	} catch (SQLException ex) {
	    System.out.println("Could not create table: " + ex.getMessage());
	}
    }

    // Adds ONE expense as a new row in the table
    public static void addExpense(Expense expense) {
	String sql = "INSERT INTO expenses (description, amount, category) VALUES (?, ?, ?)";

	try (Connection conn = DriverManager.getConnection(URL); PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setString(1, expense.getDescription()); // fills the first ?
	    stmt.setDouble(2, expense.getAmount()); // fills the second ?
	    stmt.setString(3, expense.getCategory()); // fills the third ?
	    stmt.executeUpdate();
	} catch (SQLException ex) {
	    System.out.println("Could not save expense: " + ex.getMessage());
	}
    }

    // Changes an existing row. The WHERE id = ? part picks WHICH row to change.
    public static void updateExpense(Expense expense) {
	String sql = "UPDATE expenses SET description = ?, amount = ?, category = ? WHERE id = ?";

	try (Connection conn = DriverManager.getConnection(URL); PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setString(1, expense.getDescription());
	    stmt.setDouble(2, expense.getAmount());
	    stmt.setString(3, expense.getCategory());
	    stmt.setInt(4, expense.getId());
	    stmt.executeUpdate();
	} catch (SQLException ex) {
	    System.out.println("Could not update expense: " + ex.getMessage());
	}
    }

    // Removes the row with this id
    public static void deleteExpense(int id) {
	String sql = "DELETE FROM expenses WHERE id = ?";

	try (Connection conn = DriverManager.getConnection(URL); PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, id);
	    stmt.executeUpdate();
	} catch (SQLException ex) {
	    System.out.println("Could not delete expense: " + ex.getMessage());
	}
    }

    // Reads every row from the table and returns them as a list of Expenses
    public static ArrayList<Expense> getAllExpenses() {
	ArrayList<Expense> expenses = new ArrayList<>();
	String sql = "SELECT id, description, amount, category FROM expenses ORDER BY id";

	try (Connection conn = DriverManager.getConnection(URL);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql)) {

	    while (rs.next()) { // moves to the next row; false when there are no more
		int id = rs.getInt("id");
		String description = rs.getString("description");
		double amount = rs.getDouble("amount");
		String category = rs.getString("category");
		expenses.add(new Expense(id, description, amount, category));
	    }
	} catch (SQLException ex) {
	    System.out.println("Could not load expenses: " + ex.getMessage());
	}

	return expenses;
    }
}
