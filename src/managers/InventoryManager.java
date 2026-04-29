package managers;

import model.Book;
import java.io.*;
import java.util.*;

/**
 * Manages book inventory using ArrayList
 * Loads from the provided books.csv file
 */
public class InventoryManager {
    private ArrayList<Book> inventory;
    
    public InventoryManager() {
        inventory = new ArrayList<>();
    }
    
    /**
     * Load books from CSV file - specifically for your books.csv format
     * Format: bid,title,author,category,status
     */
    public void loadFromCSV(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        boolean isFirstLine = true;
        inventory.clear();
        
        while ((line = reader.readLine()) != null) {
            // Skip header if present
            if (isFirstLine && line.startsWith("bid")) {
                isFirstLine = false;
                continue;
            }
            
            // Parse CSV line (handles quoted fields)
            String[] parts = parseCSVLine(line);
            
            if (parts.length >= 5) {
                try {
                    int bid = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    String category = parts[3].trim();
                    String status = parts[4].trim();
                    
                    // Remove any quotes from title or author if present
                    title = title.replace("\"", "");
                    author = author.replace("\"", "");
                    
                    Book book = new Book(bid, title, author, category, status);
                    inventory.add(book);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        }
        reader.close();
        System.out.println("✓ Loaded " + inventory.size() + " books from " + filename);
    }
    
    /**
     * Parse CSV line handling quoted fields (like "Guns,Germs,and Steel")
     */
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        
        return result.toArray(new String[0]);
    }
    
    /**
     * Save inventory to CSV file
     */
    public void saveToCSV(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        writer.println("bid,title,author,category,status");
        
        for (Book book : inventory) {
            writer.println(book.toCSVString());
        }
        writer.close();
        System.out.println("✓ Saved " + inventory.size() + " books to " + filename);
    }
    
    /**
     * Display all books in inventory (table format)
     */
    public void displayAllBooks() {
        if (inventory.isEmpty()) {
            System.out.println("\n[No books in inventory]");
            return;
        }
        
        System.out.println("\n" + "=".repeat(95));
        System.out.printf("| %-5s | %-45s | %-22s | %-15s | %-10s |\n", 
                         "ID", "Title", "Author", "Category", "Status");
        System.out.println("-".repeat(95));
        
        for (Book book : inventory) {
            book.printDetails();
        }
        System.out.println("=".repeat(95));
        System.out.println("Total books in library: " + inventory.size());
        
        // Count available vs issued
        long available = inventory.stream().filter(b -> b.getStatus().equalsIgnoreCase("available")).count();
        long issued = inventory.size() - available;
        System.out.printf("📊 Available: %d | Issued: %d\n", available, issued);
    }
    
    /**
     * Search book by ID (linear scan - O(n))
     */
    public Book searchByIdLinear(int id) {
        for (Book book : inventory) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
    
    /**
     * Search books by title (partial match, case-insensitive)
     */
    public ArrayList<Book> searchByTitle(String keyword) {
        ArrayList<Book> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Book book : inventory) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword)) {
                results.add(book);
            }
        }
        return results;
    }
    
    /**
     * Search books by author (partial match, case-insensitive)
     */
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> results = new ArrayList<>();
        String lowerAuthor = author.toLowerCase();
        
        for (Book book : inventory) {
            if (book.getAuthor().toLowerCase().contains(lowerAuthor)) {
                results.add(book);
            }
        }
        return results;
    }
    
    /**
     * Search books by category (exact match, case-insensitive)
     */
    public ArrayList<Book> searchByCategory(String category) {
        ArrayList<Book> results = new ArrayList<>();
        String lowerCategory = category.toLowerCase();
        
        for (Book book : inventory) {
            if (book.getCategory().toLowerCase().equals(lowerCategory)) {
                results.add(book);
            }
        }
        return results;
    }
    
    /**
     * Get all unique categories from inventory
     */
    public Set<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        for (Book book : inventory) {
            categories.add(book.getCategory());
        }
        return categories;
    }
    
    /**
     * Get available books only
     */
    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> available = new ArrayList<>();
        for (Book book : inventory) {
            if (book.getStatus().equalsIgnoreCase("available")) {
                available.add(book);
            }
        }
        return available;
    }
    
    /**
     * Get issued books only
     */
    public ArrayList<Book> getIssuedBooks() {
        ArrayList<Book> issued = new ArrayList<>();
        for (Book book : inventory) {
            if (book.getStatus().equalsIgnoreCase("issued")) {
                issued.add(book);
            }
        }
        return issued;
    }
    
    /**
     * Update book status
     */
    public boolean updateBookStatus(int id, String newStatus) {
        Book book = searchByIdLinear(id);
        if (book != null) {
            String oldStatus = book.getStatus();
            book.setStatus(newStatus);
            System.out.println("✓ Book ID " + id + " status changed: " + oldStatus + " → " + newStatus);
            return true;
        }
        return false;
    }
    
    /**
     * Add new book to inventory
     */
    public boolean addBook(Book book) {
        if (searchByIdLinear(book.getId()) != null) {
            System.out.println("❌ Book ID " + book.getId() + " already exists!");
            return false;
        }
        inventory.add(book);
        System.out.println("✓ Added new book: " + book.getTitle());
        return true;
    }
    
    /**
     * Remove book from inventory
     */
    public boolean removeBook(int id) {
        Book book = searchByIdLinear(id);
        if (book != null) {
            inventory.remove(book);
            System.out.println("✓ Removed book: " + book.getTitle());
            return true;
        }
        System.out.println("❌ Book ID " + id + " not found!");
        return false;
    }
    
    /**
     * Search books by status (available/issued)
     */
    public ArrayList<Book> searchByStatus(String status) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : inventory) {
            if (book.getStatus().equalsIgnoreCase(status)) {
                results.add(book);
            }
        }
        return results;
    }
    
    public int getSize() {
        return inventory.size();
    }
    
    public Book getBookAtIndex(int index) {
        if (index >= 0 && index < inventory.size()) {
            return inventory.get(index);
        }
        return null;
    }
}