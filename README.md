# Library Management System (Java)

A robust, object-oriented library management application designed to handle complex library operations such as tracking multiple item types (Books, Magazines, DVDs) and diverse user categories (Students, Academicians, Guests) with specific borrowing rules.

## Key Features

- **Inheritance & Polymorphism:** Built with a clean class hierarchy where `LibraryItems` and `Users` serve as base classes for specific types.
- **Dynamic User Roles:** Different borrowing limits and access rights for Students (5 items), Academicians (3 items), and Guests (1 item).
- **Automated Penalty System:** Calculates and tracks penalties based on overdue dates.
- **Data Persistence Simulation:** Uses `Dictionary` and `Hashtable` to manage in-memory data efficiently during the session.
- **File-Based Execution:** Processes commands (borrow, return, pay, display) directly from text files and generates detailed reports.

## System Architecture

- **LibraryItems.java:** Manages the properties and status of books, magazines, and DVDs.
- **Users.java:** Defines user behavior, borrowing capacities, and access permissions.
- **LibraryManagement.java:** The core logic engine that processes commands and calculates dates/penalties.
- **Main.java:** The entry point of the application.

## Project Structure

The system expects four command-line arguments to function:
1. `items.txt`: Initial list of library items.
2. `users.txt`: Initial list of registered users.
3. `commands.txt`: Operations to perform (borrow, return, etc.).
4. `output.txt`: The file where results will be written.

## Usage

Compile and run the project using the following command:

```powershell
javac *.java
java Main items.txt users.txt commands.txt output.txt