package datastructures;

import model.Book;

/**
 * Self-implemented Singly Linked List for borrowing cart
 * From: Grocery Store Assignment - CartList
 * 
 * Operations: O(n) for search, O(1) for add to front/back
 */
public class CartList {
    private CartNode head;
    private int size;
    
    public CartList() {
        head = null;
        size = 0;
    }
    
    /**
     * Add book to cart (at end - FIFO order)
     */
    public void addItem(Book book, int quantity) {
        // Check if already in cart
        CartNode existing = findItem(book.getId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            System.out.println("Updated quantity for: " + book.getTitle());
            return;
        }
        
        CartNode newNode = new CartNode(book, quantity);
        
        if (head == null) {
            head = newNode;
        } else {
            CartNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
        System.out.println("Added to cart: " + book.getTitle() + " (x" + quantity + ")");
    }
    
    /**
     * Find item by book ID
     */
    public CartNode findItem(int bookId) {
        CartNode current = head;
        while (current != null) {
            if (current.getBook().getId() == bookId) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }
    
    /**
     * Remove item from cart completely
     */
    public boolean removeItem(int bookId) {
        if (head == null) return false;
        
        if (head.getBook().getId() == bookId) {
            head = head.getNext();
            size--;
            return true;
        }
        
        CartNode current = head;
        while (current.getNext() != null) {
            if (current.getNext().getBook().getId() == bookId) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
    
    /**
     * Update quantity of existing cart item
     */
    public boolean updateQuantity(int bookId, int newQuantity) {
        CartNode node = findItem(bookId);
        if (node != null && newQuantity > 0) {
            node.setQuantity(newQuantity);
            return true;
        }
        return false;
    }
    
    /**
     * Display cart contents
     */
    public void displayCart() {
        if (head == null) {
            System.out.println("\n[Cart is empty]");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("| %-5s | %-40s | %-20s | %-8s | %-10s |\n", 
                         "ID", "Title", "Author", "Quantity", "Fee/Day");
        System.out.println("-".repeat(80));
        
        CartNode current = head;
        while (current != null) {
            Book book = current.getBook();
            System.out.printf("| %-5d | %-40s | %-20s | %-8d | $%-9.2f |\n",
                book.getId(),
                book.getTitle().length() > 40 ? book.getTitle().substring(0, 37) + "..." : book.getTitle(),
                book.getAuthor().length() > 20 ? book.getAuthor().substring(0, 17) + "..." : book.getAuthor(),
                current.getQuantity(),
                book.calculateLateFee(1));
            current = current.getNext();
        }
        System.out.println("=".repeat(80));
    }
    
    /**
     * Calculate total late fee per day for all items in cart
     */
    public double calculateTotalLateFeePerDay() {
        double total = 0;
        CartNode current = head;
        while (current != null) {
            total += current.getBook().calculateLateFee(1) * current.getQuantity();
            current = current.getNext();
        }
        return total;
    }
    
    /**
     * Clear entire cart
     */
    public void clear() {
        head = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public int getSize() {
        return size;
    }
    
    /**
     * Get last added item (for undo)
     */
    public CartNode getLastAddedItem() {
        if (head == null) return null;
        
        CartNode current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }
    
    /**
     * Remove last added item (for undo)
     */
    public boolean removeLastItem() {
        if (head == null) return false;
        
        if (head.getNext() == null) {
            head = null;
        } else {
            CartNode current = head;
            while (current.getNext().getNext() != null) {
                current = current.getNext();
            }
            current.setNext(null);
        }
        size--;
        return true;
    }

    public CartNode getHeadForIteration() {
        return head;
    }
}