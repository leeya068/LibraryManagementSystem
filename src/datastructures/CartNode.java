package datastructures;

import model.Book;

/**
 * Node for singly linked list cart
 * From: Grocery Store Assignment - CartNode
 */
public class CartNode {
    private Book book;
    private int quantity;
    private CartNode next;
    
    public CartNode(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
        this.next = null;
    }
    
    public Book getBook() { return book; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public CartNode getNext() { return next; }
    public void setNext(CartNode next) { this.next = next; }
}