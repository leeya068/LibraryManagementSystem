package datastructures;

import model.Book;

/**
 * Node for Binary Search Tree
 * From: Smart Library Project - Catalogue Architect
 * BST indexed by ISBN (bid)
 */
public class BSTNode {
    private Book book;
    private BSTNode left;
    private BSTNode right;
    
    public BSTNode(Book book) {
        this.book = book;
        this.left = null;
        this.right = null;
    }
    
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    
    public BSTNode getLeft() { return left; }
    public void setLeft(BSTNode left) { this.left = left; }
    
    public BSTNode getRight() { return right; }
    public void setRight(BSTNode right) { this.right = right; }
    
    // Check if node is leaf
    public boolean isLeaf() {
        return left == null && right == null;
    }
}