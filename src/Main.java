import managers.*;
import datastructures.*;
import model.*;
import undo.UndoStack;
import java.io.*;
import java.util.*;

/**
 * INTEGRATED LIBRARY MANAGEMENT SYSTEM
 * 
 * Uses the actual books.csv dataset with 61 books
 * Combines: ArrayList | BST | Stack | Singly Linked List | Generics
 */
public class Main {
    private static InventoryManager inventoryManager;
    private static BST bstCatalogue;
    private static CartList borrowCart;
    private static BorrowingHistoryManager historyManager;
    private static UndoStack undoStack;
    private static Scanner scanner;
    
    private static final String DATA_FILE = "books.csv";
    
    public static void main(String[] args) {
        initializeSystem();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter choice: ", 0, 15);
            
            switch (choice) {
                case 1:  displayAllBooks(); break;
                case 2:  searchByISBN_BST(); break;
                case 3:  searchByTitle(); break;
                case 4:  searchByAuthor(); break;
                case 5:  searchByCategory(); break;
                case 6:  searchByStatus(); break;
                case 7:  displayCategories(); break;
                case 8:  borrowBook(); break;
                case 9:  viewCart(); break;
                case 10: removeFromCart(); break;
                case 11: undoLastCartAddition(); break;
                case 12: checkout(); break;
                case 13: viewBorrowingHistory(); break;
                case 14: displayBSTStats(); break;
                case 15: displayGenericDemo(); break;
                case 0:  running = saveAndExit(); break;
                default: System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
    
    private static void initializeSystem() {
        inventoryManager = new InventoryManager();
        bstCatalogue = new BST();
        borrowCart = new CartList();
        historyManager = new BorrowingHistoryManager();
        undoStack = new UndoStack();
        scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(70));
        System.out.println("   📚 INTEGRATED LIBRARY MANAGEMENT SYSTEM");
        System.out.println("   ======================================");
        System.out.println("   Data Structures Implemented:");
        System.out.println("   ✓ ArrayList (Inventory Management)");
        System.out.println("   ✓ Binary Search Tree (Fast ISBN Search - O(log n))");
        System.out.println("   ✓ Singly Linked List (Borrowing Cart)");
        System.out.println("   ✓ Stack (Borrowing History - LIFO)");
        System.out.println("   ✓ Stack (Undo Feature)");
        System.out.println("   ✓ Generics (BookManager<T>)");
        System.out.println("=".repeat(70));
        
        // Load data from CSV
        try {
            inventoryManager.loadFromCSV(DATA_FILE);
            
            // Build BST from inventory (for O(log n) search)
            for (int i = 0; i < inventoryManager.getSize(); i++) {
                Book book = inventoryManager.getBookAtIndex(i);
                if (book != null) {
                    bstCatalogue.insert(book);
                }
            }
            System.out.println("\n✓ BST catalogue built with " + bstCatalogue.getSize() + " books.");
            System.out.println("✓ BST Height: " + bstCatalogue.getHeight());
            
            // Display quick stats
            long available = inventoryManager.getAvailableBooks().size();
            long issued = inventoryManager.getIssuedBooks().size();
            System.out.printf("\n📊 LIBRARY STATS:\n");
            System.out.printf("   Total Books: %d\n", inventoryManager.getSize());
            System.out.printf("   Available:   %d\n", available);
            System.out.printf("   Issued:      %d\n", issued);
            
        } catch (IOException e) {
            System.out.println("⚠️ Warning: Could not load " + DATA_FILE);
            System.out.println("   Starting with empty database.");
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("                         MAIN MENU");
        System.out.println("═".repeat(70));
        
        System.out.println("\n📚 CATALOGUE OPERATIONS:");
        System.out.println("   1.  Display all books");
        System.out.println("   2.  Search by ISBN (BST - O(log n) RECURSIVE) ⭐");
        System.out.println("   3.  Search by title");
        System.out.println("   4.  Search by author");
        System.out.println("   5.  Search by category");
        System.out.println("   6.  Search by status (available/issued)");
        System.out.println("   7.  Show all categories");
        
        System.out.println("\n🛒 BORROWING CART (Singly Linked List):");
        System.out.println("   8.  Borrow book (add to cart)");
        System.out.println("   9.  View cart");
        System.out.println("   10. Remove from cart");
        System.out.println("   11. UNDO last cart addition (Stack)");
        
        System.out.println("\n💰 CHECKOUT & HISTORY:");
        System.out.println("   12. Checkout / Confirm borrow");
        System.out.println("   13. View borrowing history (LIFO Stack) ⭐");
        
        System.out.println("\n📊 SYSTEM INFO:");
        System.out.println("   14. Display BST statistics");
        System.out.println("   15. Demo: Generic BookManager<T>");
        
        System.out.println("\n   0.  Save & Exit");
        System.out.println("═".repeat(70));
    }
    
    private static void displayAllBooks() {
        System.out.println("\n📚 ALL BOOKS IN LIBRARY (ArrayList based)");
        inventoryManager.displayAllBooks();
    }
    
    private static void searchByISBN_BST() {
        System.out.println("\n🔍 BST SEARCH BY ISBN (Recursive - O(log n))");
        int isbn = getIntInput("Enter Book ID (1-61): ", 1, 100);
        
        long startTime = System.nanoTime();
        Book book = bstCatalogue.searchRecursive(isbn);
        long endTime = System.nanoTime();
        
        if (book != null) {
            System.out.println("\n✅ BOOK FOUND:");
            System.out.println("╔" + "═".repeat(60) + "╗");
            System.out.printf("║ %-58s ║\n", "Book Details");
            System.out.println("╠" + "═".repeat(60) + "╣");
            System.out.printf("║ %-15s: %-42s ║\n", "ID", book.getId());
            System.out.printf("║ %-15s: %-42s ║\n", "Title", truncate(book.getTitle(), 42));
            System.out.printf("║ %-15s: %-42s ║\n", "Author", truncate(book.getAuthor(), 42));
            System.out.printf("║ %-15s: %-42s ║\n", "Category", book.getCategory());
            System.out.printf("║ %-15s: %-42s ║\n", "Status", book.getStatus());
            System.out.printf("║ %-15s: $%-41.2f ║\n", "Late Fee/Day", book.calculateLateFee(1));
            System.out.println("╚" + "═".repeat(60) + "╝");
            System.out.printf("⏱️ Search time: %.2f µs\n", (endTime - startTime) / 1000.0);
        } else {
            System.out.println("❌ Book with ID " + isbn + " not found.");
        }
    }
    
    private static void searchByTitle() {
        System.out.print("\n📖 Enter title keyword: ");
        String keyword = scanner.nextLine();
        
        ArrayList<Book> results = inventoryManager.searchByTitle(keyword);
        
        if (results.isEmpty()) {
            System.out.println("❌ No books found with title containing: " + keyword);
        } else {
            System.out.println("\n✅ Found " + results.size() + " book(s):");
            System.out.println("=".repeat(95));
            System.out.printf("| %-5s | %-45s | %-22s | %-15s |\n", "ID", "Title", "Author", "Status");
            System.out.println("-".repeat(95));
            for (Book book : results) {
                System.out.printf("| %-5d | %-45s | %-22s | %-10s |\n",
                    book.getId(),
                    truncate(book.getTitle(), 45),
                    truncate(book.getAuthor(), 22),
                    book.getStatus());
            }
            System.out.println("=".repeat(95));
        }
    }
    
    private static void searchByAuthor() {
        System.out.print("\n✍️ Enter author name (or partial): ");
        String author = scanner.nextLine();
        
        ArrayList<Book> results = inventoryManager.searchByAuthor(author);
        
        if (results.isEmpty()) {
            System.out.println("❌ No books found by author: " + author);
        } else {
            System.out.println("\n✅ Found " + results.size() + " book(s) by " + author + ":");
            for (Book book : results) {
                System.out.println("   • ID " + book.getId() + ": " + book.getTitle() + " (" + book.getStatus() + ")");
            }
        }
    }
    
    private static void searchByCategory() {
        System.out.println("\n📂 Available Categories:");
        Set<String> categories = inventoryManager.getAllCategories();
        List<String> sortedCategories = new ArrayList<>(categories);
        Collections.sort(sortedCategories);
        for (int i = 0; i < sortedCategories.size(); i++) {
            System.out.printf("   %2d. %s\n", i + 1, sortedCategories.get(i));
        }
        
        System.out.print("\nEnter category name: ");
        String category = scanner.nextLine();
        
        ArrayList<Book> results = inventoryManager.searchByCategory(category);
        
        if (results.isEmpty()) {
            System.out.println("❌ No books found in category: " + category);
        } else {
            System.out.println("\n✅ Found " + results.size() + " book(s) in " + category + ":");
            System.out.println("=".repeat(95));
            System.out.printf("| %-5s | %-45s | %-22s | %-10s |\n", "ID", "Title", "Author", "Status");
            System.out.println("-".repeat(95));
            for (Book book : results) {
                System.out.printf("| %-5d | %-45s | %-22s | %-10s |\n",
                    book.getId(),
                    truncate(book.getTitle(), 45),
                    truncate(book.getAuthor(), 22),
                    book.getStatus());
            }
            System.out.println("=".repeat(95));
        }
    }
    
    private static void searchByStatus() {
        System.out.println("\n🔍 Search by status:");
        System.out.println("   1. Available");
        System.out.println("   2. Issued");
        int choice = getIntInput("Enter choice: ", 1, 2);
        
        String status = (choice == 1) ? "available" : "issued";
        ArrayList<Book> results = inventoryManager.searchByStatus(status);
        
        System.out.println("\n✅ Found " + results.size() + " " + status + " book(s):");
        for (Book book : results) {
            System.out.println("   • " + book);
        }
    }
    
    private static void displayCategories() {
        System.out.println("\n📂 ALL BOOK CATEGORIES:");
        Set<String> categories = inventoryManager.getAllCategories();
        List<String> sortedCategories = new ArrayList<>(categories);
        Collections.sort(sortedCategories);
        
        System.out.println("═".repeat(40));
        for (String category : sortedCategories) {
            int count = inventoryManager.searchByCategory(category).size();
            System.out.printf("   • %-20s : %2d books\n", category, count);
        }
        System.out.println("═".repeat(40));
        System.out.println("Total categories: " + sortedCategories.size());
    }
    
    private static void borrowBook() {
        System.out.println("\n🛒 BORROW BOOK");
        System.out.println("-".repeat(40));
        
        // Show available books first
        ArrayList<Book> availableBooks = inventoryManager.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("❌ No books available for borrowing!");
            return;
        }
        
        System.out.println("\n📖 Available books:");
        for (Book book : availableBooks) {
            System.out.println("   • ID " + book.getId() + ": " + book.getTitle());
        }
        
        int id = getIntInput("\nEnter book ID to borrow: ", 1, 100);
        
        // Use BST for fast lookup
        Book book = bstCatalogue.searchRecursive(id);
        
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }
        
        if (!book.getStatus().equalsIgnoreCase("available")) {
            System.out.println("❌ Book is currently ISSUED. Not available for borrowing.");
            return;
        }
        
        int quantity = getIntInput("Enter quantity (max 3): ", 1, 3);
        
        // Add to cart (singly linked list)
        borrowCart.addItem(book, quantity);
        
        // Push to undo stack
        undoStack.push(book, quantity, id);
        
        // Temporarily reserve the book
        inventoryManager.updateBookStatus(id, "reserved");
        System.out.println("✓ Book reserved in cart. Complete checkout to borrow.");
    }
    
    private static void viewCart() {
        System.out.println("\n🛒 YOUR BORROWING CART (Singly Linked List)");
        borrowCart.displayCart();
        
        if (!borrowCart.isEmpty()) {
            System.out.printf("\n💰 Total potential late fee per day: $%.2f\n", 
                            borrowCart.calculateTotalLateFeePerDay());
            System.out.println("   ⚠️ Late fee applies only if book is returned after due date");
        }
    }
    
    private static void removeFromCart() {
        if (borrowCart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        
        borrowCart.displayCart();
        int id = getIntInput("Enter book ID to remove from cart: ", 1, 100);
        
        CartNode node = borrowCart.findItem(id);
        if (node == null) {
            System.out.println("Book not found in cart.");
            return;
        }
        
        // Restore status to available
        inventoryManager.updateBookStatus(id, "available");
        
        if (borrowCart.removeItem(id)) {
            System.out.println("✓ Book removed from cart. Status restored to 'available'.");
        }
    }
    
    private static void undoLastCartAddition() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        
        UndoStack.UndoAction lastAction = undoStack.getUndoAction();
        if (lastAction == null) {
            System.out.println("Undo failed.");
            return;
        }
        
        if (borrowCart.removeLastItem()) {
            inventoryManager.updateBookStatus(lastAction.bookId, "available");
            undoStack.pop();
            System.out.println("✓ UNDO successful!");
            System.out.println("  Removed: " + lastAction.book.getTitle());
            System.out.println("  Stock status restored to 'available'.");
        } else {
            System.out.println("Undo failed.");
        }
    }
    
    private static void checkout() {
        if (borrowCart.isEmpty()) {
            System.out.println("Nothing to checkout. Cart is empty.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(70));
        System.out.println("                    CHECKOUT RECEIPT");
        System.out.println("═".repeat(70));
        
        borrowCart.displayCart();
        
        int daysToReturn = getIntInput("\n📅 Expected return days (default 14, 1-30): ", 1, 30);
        
        System.out.println("\n" + "─".repeat(70));
        System.out.println("  Checkout Summary:");
        System.out.println("  • Total items: " + borrowCart.getSize());
        System.out.printf("  • Late fee (if overdue 1 day): $%.2f\n", 
                         borrowCart.calculateTotalLateFeePerDay());
        System.out.println("  • Due date: " + getDueDate(daysToReturn));
        System.out.println("─".repeat(70));
        
        System.out.print("\nConfirm checkout? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            // Process checkout and record to history
            CartNode current = borrowCart.getHeadForIteration();
            while (current != null) {
                Book book = current.getBook();
                // Update status to issued permanently
                inventoryManager.updateBookStatus(book.getId(), "issued");
                // Record in borrowing history stack
                historyManager.recordBorrow(book, daysToReturn);
                current = current.getNext();
            }
            
            System.out.println("\n✅ CHECKOUT COMPLETE!");
            System.out.println("   Thank you for borrowing from our library!");
            System.out.println("   Please return books by " + getDueDate(daysToReturn));
            
            // Clear cart and undo stack
            borrowCart.clear();
            undoStack.clear();
            System.out.println("   Cart cleared.");
        } else {
            System.out.println("Checkout cancelled. Items remain in cart.");
        }
    }
    
    private static void viewBorrowingHistory() {
        System.out.println("\n📜 BORROWING HISTORY (Stack - LIFO Order)");
        System.out.println("   Most recent borrow appears FIRST\n");
        historyManager.displayHistory();
    }
    
    private static void displayBSTStats() {
        System.out.println("\n📊 BINARY SEARCH TREE STATISTICS");
        System.out.println("═".repeat(50));
        System.out.printf("  🌳 Total nodes:     %d\n", bstCatalogue.getSize());
        System.out.printf("  📏 Tree height:     %d\n", bstCatalogue.getHeight());
        System.out.printf("  ⭕ Empty?           %s\n", bstCatalogue.isEmpty() ? "Yes" : "No");
        System.out.printf("  ⚡ Search O(log n): %s\n", bstCatalogue.getHeight() > 0 ? "✓ Efficient" : "N/A");
        System.out.println("═".repeat(50));
        
        System.out.println("\n📖 Books in BST (In-order traversal - sorted by ID):");
        ArrayList<Book> sortedBooks = bstCatalogue.inorderTraversal();
        int count = 0;
        for (Book book : sortedBooks) {
            if (count++ < 15) {
                System.out.printf("  %3d. %-45s [%s]\n", book.getId(), 
                    truncate(book.getTitle(), 45), book.getStatus());
            }
        }
        if (sortedBooks.size() > 15) {
            System.out.println("  ... and " + (sortedBooks.size() - 15) + " more.");
        }
    }
    
    private static void displayGenericDemo() {
        System.out.println("\n🔧 GENERIC BOOK MANAGER DEMO");
        System.out.println("   BookManager<T extends LibraryItemADT>");
        System.out.println("═".repeat(60));
        
        BookManager<Book> bookManager = new BookManager<>();
        
        // Add some available books to the generic manager
        ArrayList<Book> availableBooks = inventoryManager.getAvailableBooks();
        int added = 0;
        System.out.println("\n📚 Adding first 5 available books to generic manager:");
        for (Book book : availableBooks) {
            if (added++ < 5) {
                bookManager.addItem(book);
            }
        }
        
        System.out.println("\n📚 Books in Generic Manager:");
        bookManager.printAllItems();
        
        Book highestLateFee = bookManager.getItemWithHighestLateFee();
        if (highestLateFee != null) {
            System.out.println("\n🏆 Book with HIGHEST late fee: " + highestLateFee.getTitle());
            System.out.printf("   💰 Late fee: $%.2f/day\n", highestLateFee.calculateLateFee(1));
        }
        
        System.out.println("\n✅ Generic class demonstrates:");
        System.out.println("   • <T extends LibraryItemADT> (bounded type parameter)");
        System.out.println("   • Polymorphic method calls (calculateLateFee varies by category)");
        System.out.println("   • Type safety at compile time");
        System.out.println("   • Reusable code for any LibraryItem type");
    }
    
    // ========== UTILITY METHODS ==========
    
    private static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    
    private static String getDueDate(int daysFromNow) {
        java.time.LocalDate dueDate = java.time.LocalDate.now().plusDays(daysFromNow);
        java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return dueDate.format(formatter);
    }
    
    private static int getIntInput(String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d.\n", min, max);
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            }
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
    }
    
    private static boolean saveAndExit() {
        try {
            // Sync any cart changes back to inventory before saving
            // For simplicity, we just save current state
            inventoryManager.saveToCSV(DATA_FILE);
            System.out.println("\n💾 Inventory saved to " + DATA_FILE);
            System.out.println("👋 Goodbye! Thank you for using the Library System.");
            return false;
        } catch (IOException e) {
            System.out.println("⚠️ Error saving: " + e.getMessage());
            System.out.print("Exit without saving? (y/n): ");
            String response = scanner.nextLine();
            return response.equalsIgnoreCase("y");
        }
    }
}