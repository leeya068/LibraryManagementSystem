package undo;

import model.Book;

/**
 * Stack for undo functionality in cart
 * From: Grocery Store Assignment - Undo Feature
 */
public class UndoStack {
    private static class UndoNode {
        Book book;
        int quantity;
        int bookId;
        UndoNode next;
        
        UndoNode(Book book, int quantity, int bookId) {
            this.book = book;
            this.quantity = quantity;
            this.bookId = bookId;
            this.next = null;
        }
    }
    
    private UndoNode top;
    private int size;
    
    public UndoStack() {
        top = null;
        size = 0;
    }
    
    public void push(Book book, int quantity, int bookId) {
        UndoNode newNode = new UndoNode(book, quantity, bookId);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    public UndoNode pop() {
        if (isEmpty()) return null;
        
        UndoNode popped = top;
        top = top.next;
        size--;
        return popped;
    }
    
    public UndoNode peek() {
        return top;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public void clear() {
        top = null;
        size = 0;
    }
    
    public int getSize() {
        return size;
    }
    
    // Helper class to return undo data
    public static class UndoAction {
        public Book book;
        public int quantity;
        public int bookId;
        
        public UndoAction(Book book, int quantity, int bookId) {
            this.book = book;
            this.quantity = quantity;
            this.bookId = bookId;
        }
    }
    
    public UndoAction getUndoAction() {
        if (isEmpty()) return null;
        return new UndoAction(top.book, top.quantity, top.bookId);
    }
}