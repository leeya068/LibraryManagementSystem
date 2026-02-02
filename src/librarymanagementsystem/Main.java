package src.librarymanagementsystem;

public class Main {
    public static void main(String[] args) {

        String filePath = "data\\books.csv"; // relative path for GitHub

        // Read books from CSV
        Book[] books = BookReader.readBooks(filePath, 1000);

        // Create Library and add books
        Library library = new Library(1000);
        for (Book b : books) {
            if (b != null) library.addBook(b);
        }

        // Start menu
        LibraryMenu menu = new LibraryMenu(library);
        menu.start();

        // Save updated books when exiting
        BookWriter.writeBooks(filePath, library.getBooksArray(), library.getCount());
    }
}
