package model;

/**
 * Book entity implementing LibraryItemADT
 */
public class Book implements LibraryItemADT {
    private int bid;
    private String title;
    private String author;
    private String category;
    private String status;
    
    // Late fee rates per category (polymorphism)
    private static final double LATE_FEE_RATES = 0.50;
    
    public Book(int bid, String title, String author, String category, String status) {
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
    }
    
    @Override
    public int getId() { return bid; }
    
    @Override
    public String getTitle() { return title; }
    
    @Override
    public String getAuthor() { return author; }
    
    @Override
    public String getCategory() { return category; }
    
    @Override
    public String getStatus() { return status; }
    
    @Override
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public double calculateLateFee(int daysLate) {
        // Different categories have different late fees (polymorphism)
        switch (category.toLowerCase()) {
            case "education":
                return daysLate * 0.25;      // Educational books: discounted
            case "fiction":
                return daysLate * 0.75;
            case "autobiography":
            case "biography":
                return daysLate * 0.50;
            case "philosophy":
                return daysLate * 1.00;       // Premium books
            case "economics":
                return daysLate * 0.80;
            case "history":
                return daysLate * 0.60;
            default:
                return daysLate * LATE_FEE_RATES;
        }
    }
    
    @Override
    public void printDetails() {
        String displayTitle = title.length() > 45 ? title.substring(0, 42) + "..." : title;
        String displayAuthor = author.length() > 22 ? author.substring(0, 19) + "..." : author;
        String displayCategory = category.length() > 15 ? category.substring(0, 12) + "..." : category;
        
        System.out.printf("| %-5d | %-45s | %-22s | %-15s | %-10s |\n",
            bid, displayTitle, displayAuthor, displayCategory, status);
    }
    
    @Override
    public String toCSVString() {
        // Handle commas in title/author by wrapping in quotes
        String safeTitle = title.contains(",") ? "\"" + title + "\"" : title;
        String safeAuthor = author.contains(",") ? "\"" + author + "\"" : author;
        return bid + "," + safeTitle + "," + safeAuthor + "," + category + "," + status;
    }
    
    @Override
    public String toString() {
        return String.format("[ID: %3d] %-40s by %-20s (%s)", bid, 
            title.length() > 40 ? title.substring(0, 37) + "..." : title,
            author.length() > 20 ? author.substring(0, 17) + "..." : author,
            status);
    }
}