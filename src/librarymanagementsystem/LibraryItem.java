package src.librarymanagementsystem;

public class LibraryItem {

    protected String bid;
    protected String title;
    protected String category;
    protected boolean isBorrowed;

    public LibraryItem(String bid, String title, String category) {
        this.bid = bid;
        this.title = title;
        this.category = category;
        this.isBorrowed = false;
    }

   public void borrowItem() throws BookNotAvailableException {

    if (isBorrowed) {
        throw new BookNotAvailableException(
                "Sorry, \"" + title + "\" is already borrowed."
        );
    }

    isBorrowed = true;
    System.out.println("You have successfully borrowed \"" + title + "\".");
    }
    

    public void returnItem() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println(title + " has been returned.");
        } else {
            System.out.println(title + " was not borrowed.");
        }
    }

    // Polymorphic method
    public void displayInfo() {
        System.out.println(bid + " | " + title + " | " + category + " | " +
                (isBorrowed ? "Borrowed" : "Available"));
    }
}
