package managers;

import datastructures.Stack;
import model.Book;
import model.BorrowRecord;

/**
 * Manages borrowing history using custom Stack
 * From: Smart Library Project - Borrowing History
 */
public class BorrowingHistoryManager {
    private Stack historyStack;
    
    public BorrowingHistoryManager() {
        historyStack = new Stack();
    }
    
    /**
     * Record a borrow action (push to stack)
     */
    public void recordBorrow(Book book, int expectedReturnDays) {
        BorrowRecord record = new BorrowRecord(book, expectedReturnDays);
        historyStack.push(record);
        System.out.println("✓ Borrow recorded in history. Most recent borrow: " + book.getTitle());
    }
    
    /**
     * Get most recent borrow (without removing)
     */
    public BorrowRecord getMostRecentBorrow() {
        return historyStack.peek();
    }
    
    /**
     * Undo last borrow (pop from stack) - for return functionality
     */
    public BorrowRecord undoLastBorrow() {
        if (historyStack.isEmpty()) {
            return null;
        }
        return historyStack.pop();
    }
    
    /**
     * Display full borrowing history (LIFO order)
     */
    public void displayHistory() {
        historyStack.displayHistory();
    }
    
    public boolean hasHistory() {
        return !historyStack.isEmpty();
    }
    
    public int getHistoryCount() {
        return historyStack.getSize();
    }
    
    public void clearHistory() {
        historyStack.clear();
    }
}