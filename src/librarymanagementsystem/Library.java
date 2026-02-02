package src.librarymanagementsystem;

public class Library {

    private Book[] books;
    private int count;

    public Library(int size) {
        books = new Book[size];
        count = 0;
    }

    public Book[] getBooksArray() {
        return books;
    }

    public int getCount() {
        return count;
    }

    // Add Book
    public void addBook(Book book) {
        if (count < books.length) {
            books[count] = book;
            count++;
        } else {
            System.out.println("Warning: Library is full!");
        }
    }

    // View Books
    public void displayBooks() {
        for (int i = 0; i < count; i++) {
            System.out.println(books[i]);
        }
    }

    // Search Book
    public Book searchBook(String bid) {
        for (int i = 0; i < count; i++) {
            if (books[i].getBid().equalsIgnoreCase(bid)) {
                return books[i];
            }
        }
        return null;
    }

    // Borrow Book
    public void borrowBook(String bid) {
        Book book = searchBook(bid);

        if (book == null) {
            System.out.println("Warning: Book not found.");
            return;
        }

        try {
            book.borrowItem();
        } catch (BookNotAvailableException e) {
            System.out.println("Warning: " + e.getMessage());
        }
    }

    // Return Book
    public void returnBook(String bid) {
        Book book = searchBook(bid);

        if (book == null) {
            System.out.println("Warning: Book not found.");
            return;
        }

        book.returnItem();
    }

    // Save Books to CSV
    public void saveBooks(String filename) {
        BookWriter.writeBooks(filename, books, count);
        System.out.println("Books saved to " + filename);
    }
}
