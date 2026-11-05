# Budget Tracker

A command-line expense tracker written in Java. Log your spending, see where your money goes, and keep your data between sessions using a SQLite database.

## Features

- Add expenses with a description, amount, and category
- View all saved expenses
- See the total amount spent
- See the total spent per category (for example, Food vs. Transport)
- Input validation: rejects non-numeric or non-positive amounts and asks again
- Category names are normalized, so `food`, `FOOD`, and `Food` are treated as the same category
- Expenses are stored in a SQLite database and are still there the next time you run the program

## Built with

- **Java**: core application logic, object-oriented design
- **SQLite** with **JDBC**: persistent storage, using parameterized SQL queries (`PreparedStatement`)

## Project structure

| File | Purpose |
|---|---|
| `Main.java` | Text menu and user input |
| `Expense.java` | Represents one expense (description, amount, category) |
| `ExpenseDatabase.java` | Creates the table and saves/loads expenses using SQL |

## How to run

1. Install a JDK (Java 17 or newer).
2. Download the [SQLite JDBC driver](https://github.com/xerial/sqlite-jdbc) (`sqlite-jdbc-<version>.jar`) and `slf4j-api-1.7.36.jar`.
3. Put the three `.java` files in a project (for example, in Eclipse) and add both `.jar` files to the project's classpath.
4. Run `Main.java`. The database file `expenses.db` is created automatically the first time you add an expense.

## Example

```
=== Budget Tracker ===
1. Add an expense
2. View all expenses
3. Show total spent
4. Show total per category
5. Quit
Choose an option: 1
Description: Coffee
Amount: 4.25
Category: food
Expense added!
```

## Planned improvements

- Delete and edit expenses
- Monthly summaries
- Web version using Spring Boot and a REST API
- Unit tests

## Author

Christophe Guirguis
