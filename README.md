**Budget Tracker**

A command-line expense tracker written in Java. Log your spending, fix mistakes, see where your money goes, and keep your data between sessions using a SQLite database.

Features
Add expenses with a description, amount, and category
View all saved expenses as a numbered list
Edit any saved expense (press Enter to keep a field unchanged)
Delete an expense, with a confirmation step
See the total amount spent
See the total per category (for example, Food vs. Transport)
Input validation: rejects non-numeric or non-positive amounts and asks again
Category names are normalized, so food, FOOD, and Food count as the same category
Data is stored in a SQLite database, so expenses are still there the next time you run the program
Built with
Java: application logic and object-oriented design
SQLite with JDBC: persistent storage using SQL (CREATE, INSERT, SELECT, UPDATE, DELETE) with parameterized queries (PreparedStatement)
Project structure
File	Purpose
Main.java	Text menu, user input, and input validation
Expense.java	Represents one expense (id, description, amount, category)
ExpenseDatabase.java	Creates the table and handles all database reads and writes
How to run
Install a JDK (Java 17 or newer).
Download the SQLite JDBC driver (sqlite-jdbc-<version>.jar) and slf4j-api-1.7.36.jar.
Put the three .java files in a Java project (for example, in Eclipse) and add both .jar files to the project's classpath.
Run Main.java. The database file expenses.db is created automatically the first time you run the program.
Example
=== Budget Tracker ===
1. Add an expense
2. View all expenses
3. Show total spent
4. Show total per category
5. Edit an expense
6. Delete an expense
7. Quit
Choose an option: 5
1. Lunch | $12.50 | Food
2. Bus pass | $40.00 | Transport
Enter the number of the expense to edit (0 to cancel): 1
Press Enter on any question to keep the current value.
New description (currently "Lunch"): Team lunch
New amount (currently $12.50):
New category (currently "Food"):
Expense updated!
Planned improvements
Monthly summaries
Unit tests
Web version using Spring Boot and a REST API
Author

Christophe Guirguis
