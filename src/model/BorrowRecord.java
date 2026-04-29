package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Borrow record for stack history (LIFO)
 * From: Smart Library Project - Borrowing History
 */
public class BorrowRecord {
    private Book book;
    private LocalDateTime borrowTime;
    private int expectedReturnDays;
    
    public BorrowRecord(Book book, int expectedReturnDays) {
        this.book = book;
        this.borrowTime = LocalDateTime.now();
        this.expectedReturnDays = expectedReturnDays;
    }
    
    public Book getBook() { return book; }
    
    public LocalDateTime getBorrowTime() { return borrowTime; }
    
    public String getFormattedBorrowTime() {
        return borrowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public String getExpectedReturnDate() {
        return borrowTime.plusDays(expectedReturnDays)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public void printRecord() {
        System.out.printf("| %-5d | %-35s | %-20s | %-20s | %-12s |\n",
            book.getId(),
            book.getTitle().length() > 35 ? book.getTitle().substring(0, 32) + "..." : book.getTitle(),
            book.getAuthor().length() > 20 ? book.getAuthor().substring(0, 17) + "..." : book.getAuthor(),
            getFormattedBorrowTime(),
            getExpectedReturnDate());
    }
}