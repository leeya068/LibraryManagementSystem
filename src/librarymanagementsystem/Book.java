package src.librarymanagementsystem;

public class Book extends LibraryItem {

    private String author;

    public Book(String bid, String title, String author, String category) {
        super(bid, title, category);
        this.author = author;
    }

    @Override
    public void displayInfo() {
        System.out.println(bid + " | " + title + " | " + author + " | " +
                category + " | " +
                (isBorrowed ? "Borrowed" : "Available"));
    }

    // ===== Getters for BookWriter =====
    public String getBid() {
        return bid;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }
}
