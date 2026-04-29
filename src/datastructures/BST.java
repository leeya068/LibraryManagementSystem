package datastructures;

import model.Book;
import java.util.ArrayList;

/**
 * Binary Search Tree implementation for book catalogue
 * Indexed by ISBN (bid)
 * From: Smart Library Project - Catalogue Architect + Record Finder (Recursive Search)
 * 
 * Time Complexity:
 * - Search: O(log n) average, O(n) worst case
 * - Insert: O(log n) average, O(n) worst case
 */
public class BST {
    private BSTNode root;
    private int size;
    
    public BST() {
        root = null;
        size = 0;
    }
    
    // ========== INSERT OPERATION ==========
    
    /**
     * Insert a book into BST by ID (ISBN)
     */
    public void insert(Book book) {
        root = insertRecursive(root, book);
        size++;
    }
    
    private BSTNode insertRecursive(BSTNode node, Book book) {
        if (node == null) {
            return new BSTNode(book);
        }
        
        if (book.getId() < node.getBook().getId()) {
            node.setLeft(insertRecursive(node.getLeft(), book));
        } else if (book.getId() > node.getBook().getId()) {
            node.setRight(insertRecursive(node.getRight(), book));
        } else {
            // Duplicate ID - update existing book
            node.setBook(book);
            size--;
        }
        
        return node;
    }
    
    // ========== RECURSIVE SEARCH (from Smart Library) ==========
    
    /**
     * RECURSIVE search by ISBN - O(log n) complexity
     * From: Smart Library Project - Record Finder
     */
    public Book searchRecursive(int isbn) {
        return searchRecursiveHelper(root, isbn);
    }
    
    private Book searchRecursiveHelper(BSTNode node, int isbn) {
        // Base case: node is null
        if (node == null) {
            return null;
        }
        
        // Base case: found
        if (node.getBook().getId() == isbn) {
            return node.getBook();
        }
        
        // Recursive cases
        if (isbn < node.getBook().getId()) {
            return searchRecursiveHelper(node.getLeft(), isbn);
        } else {
            return searchRecursiveHelper(node.getRight(), isbn);
        }
    }
    
    /**
     * ITERATIVE search for comparison
     */
    public Book searchIterative(int isbn) {
        BSTNode current = root;
        
        while (current != null) {
            if (current.getBook().getId() == isbn) {
                return current.getBook();
            } else if (isbn < current.getBook().getId()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null;
    }
    
    // ========== TRAVERSALS ==========
    
    /**
     * In-order traversal (sorted by ISBN)
     */
    public ArrayList<Book> inorderTraversal() {
        ArrayList<Book> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private void inorderHelper(BSTNode node, ArrayList<Book> result) {
        if (node != null) {
            inorderHelper(node.getLeft(), result);
            result.add(node.getBook());
            inorderHelper(node.getRight(), result);
        }
    }
    
    /**
     * Pre-order traversal
     */
    public ArrayList<Book> preorderTraversal() {
        ArrayList<Book> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }
    
    private void preorderHelper(BSTNode node, ArrayList<Book> result) {
        if (node != null) {
            result.add(node.getBook());
            preorderHelper(node.getLeft(), result);
            preorderHelper(node.getRight(), result);
        }
    }
    
    /**
     * Post-order traversal
     */
    public ArrayList<Book> postorderTraversal() {
        ArrayList<Book> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }
    
    private void postorderHelper(BSTNode node, ArrayList<Book> result) {
        if (node != null) {
            postorderHelper(node.getLeft(), result);
            postorderHelper(node.getRight(), result);
            result.add(node.getBook());
        }
    }
    
    // ========== DELETE OPERATION ==========
    
    /**
     * Delete book by ISBN
     */
    public boolean delete(int isbn) {
        if (searchRecursive(isbn) == null) {
            return false;
        }
        root = deleteRecursive(root, isbn);
        size--;
        return true;
    }
    
    private BSTNode deleteRecursive(BSTNode node, int isbn) {
        if (node == null) {
            return null;
        }
        
        if (isbn < node.getBook().getId()) {
            node.setLeft(deleteRecursive(node.getLeft(), isbn));
        } else if (isbn > node.getBook().getId()) {
            node.setRight(deleteRecursive(node.getRight(), isbn));
        } else {
            // Node to be deleted found
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            
            // Node with two children: get inorder successor
            node.setBook(findMin(node.getRight()));
            node.setRight(deleteRecursive(node.getRight(), node.getBook().getId()));
        }
        return node;
    }
    
    private Book findMin(BSTNode node) {
        BSTNode current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getBook();
    }
    
    // ========== UTILITY METHODS ==========
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public int getSize() {
        return size;
    }
    
    public void displayInOrder() {
        System.out.println("\n" + "=".repeat(85));
        System.out.printf("| %-5s | %-35s | %-20s | %-15s | %-10s |\n", 
                         "ID", "Title", "Author", "Category", "Status");
        System.out.println("-".repeat(85));
        
        ArrayList<Book> books = inorderTraversal();
        for (Book book : books) {
            book.printDetails();
        }
        System.out.println("=".repeat(85));
        System.out.println("Total books: " + size);
    }
    
    /**
     * Get the height of BST
     */
    public int getHeight() {
        return getHeightRecursive(root);
    }
    
    private int getHeightRecursive(BSTNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(getHeightRecursive(node.getLeft()), 
                           getHeightRecursive(node.getRight()));
    }
    
    /**
     * Search by title (linear scan - not efficient for large data)
     * For demonstration only
     */
    public ArrayList<Book> searchByTitle(String keyword) {
        ArrayList<Book> results = new ArrayList<>();
        searchByTitleRecursive(root, keyword.toLowerCase(), results);
        return results;
    }
    
    private void searchByTitleRecursive(BSTNode node, String keyword, ArrayList<Book> results) {
        if (node != null) {
            searchByTitleRecursive(node.getLeft(), keyword, results);
            if (node.getBook().getTitle().toLowerCase().contains(keyword)) {
                results.add(node.getBook());
            }
            searchByTitleRecursive(node.getRight(), keyword, results);
        }
    }
}