package src.librarymanagementsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BookReader {

    // maxSize = maximum number of books expected
    public static Book[] readBooks(String filePath, int maxSize) {
        Book[] books = new Book[maxSize];
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null && count < maxSize) {
                String[] data = line.split(","); // simple split, remove quotes
                if (data.length < 5) continue;

                String bid = data[0].trim().replace("\"", "");
                String title = data[1].trim().replace("\"", "");
                String author = data[2].trim().replace("\"", "");
                String category = data[3].trim().replace("\"", "");
                String status = data[4].trim().replace("\"", "");

                Book book = new Book(bid, title, author, category);

                if ("Borrowed".equalsIgnoreCase(status)) {
                    try {
                        book.borrowItem();
                    } catch (BookNotAvailableException e) {
                        // ignore on initial load
                    }
                }

                books[count++] = book;
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return books;
    }
}
