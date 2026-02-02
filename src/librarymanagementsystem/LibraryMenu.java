package src.librarymanagementsystem;

import java.util.Scanner;

public class LibraryMenu {

    private Library library;
    private Scanner scanner;

    public LibraryMenu(Library library) {
        this.library = library;
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice = 0;

        do {
            displayMenu();

            try {
                System.out.print("Enter your choice (1-5): ");
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        viewAllBooks();
                        break;

                    case 2:
                        searchBook();
                        break;

                    case 3:
                        borrowBook();
                        break;

                    case 4:
                        returnBook();
                        break;

                    case 5:
                        saveAndExit();
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }

        } while (choice != 5);
    }

    private void displayMenu() {
        System.out.println("\n====== LIBRARY MENU ======");
        System.out.println("1. View All Books");
        System.out.println("2. Search Book by ID");
        System.out.println("3. Borrow Book by ID");
        System.out.println("4. Return Book by ID");
        System.out.println("5. Exit");
    }

    private void viewAllBooks() {
        System.out.println("\n--- Library Books ---");
        library.displayBooks(); // Calls displayInfo() for each book
    }

    private void searchBook() {
        System.out.print("Enter Book ID to search: ");
        String bid = scanner.nextLine();

        Book book = library.searchBook(bid);

        if (book != null) {
            System.out.println("\nBook Found:");
            book.displayInfo();
        } else {
            System.out.println("Book not found.");
        }
    }

    private void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        String bid = scanner.nextLine();

        Book book = library.searchBook(bid);

        if (book != null) {
            try {
                book.borrowItem();
            } catch (BookNotAvailableException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    private void returnBook() {
        System.out.print("Enter Book ID to return: ");
        String bid = scanner.nextLine();

        Book book = library.searchBook(bid);

        if (book != null) {
            book.returnItem();
        } else {
            System.out.println("Book not found.");
        }
    }

    private void saveAndExit() {
        BookWriter.writeBooks("updated_books.csv", library.getBooksArray(), library.getCount());
        System.out.println("Library saved. Exiting program...");
    }
}
