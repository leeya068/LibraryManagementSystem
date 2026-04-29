package datastructures;

import model.BorrowRecord;
import java.util.EmptyStackException;

/**
 * Custom Stack implementation for borrowing history
 * LIFO (Last In, First Out) - Most recent borrow on top
 * From: Smart Library Project - Borrowing History
 * 
 * Time Complexity: O(1) for push, pop, peek
 */
public class Stack {
    private static class StackNode {
        BorrowRecord data;
        StackNode next;
        
        StackNode(BorrowRecord data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private StackNode top;
    private int size;
    
    public Stack() {
        top = null;
        size = 0;
    }
    
    /**
     * Push a borrow record onto stack (most recent borrow)
     */
    public void push(BorrowRecord record) {
        StackNode newNode = new StackNode(record);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    /**
     * Pop the most recent borrow record
     */
    public BorrowRecord pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        BorrowRecord data = top.data;
        top = top.next;
        size--;
        return data;
    }
    
    /**
     * Peek at most recent borrow without removing
     */
    public BorrowRecord peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int getSize() {
        return size;
    }
    
    /**
     * Display all borrowing history (LIFO order)
     * Most recent borrow appears first
     */
    public void displayHistory() {
        if (isEmpty()) {
            System.out.println("\n[No borrowing history]");
            return;
        }
        
        System.out.println("\n" + "=".repeat(85));
        System.out.println("             BORROWING HISTORY (Most Recent First)");
        System.out.println("=".repeat(85));
        System.out.printf("| %-5s | %-35s | %-20s | %-20s | %-12s |\n", 
                         "ID", "Title", "Author", "Borrow Time", "Due Date");
        System.out.println("-".repeat(85));
        
        // Display from top to bottom (LIFO order)
        StackNode current = top;
        while (current != null) {
            current.data.printRecord();
            current = current.next;
        }
        System.out.println("=".repeat(85));
    }
    
    /**
     * Clear entire history
     */
    public void clear() {
        top = null;
        size = 0;
    }
}