package src.librarymanagementsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BookWriter {

    // filePath is passed dynamically
    public static void writeBooks(String filePath, Book[] books, int count) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            // Write CSV header
            bw.write("BookID,Title,Author,Category,Status");
            bw.newLine();

            // Write book data
            for (int i = 0; i < count; i++) {
                Book b = books[i];
                String status = b.isBorrowed() ? "Borrowed" : "Available";

                // Wrap fields in quotes to handle commas
                bw.write("\"" + b.getBid() + "\"," +
                         "\"" + b.getTitle() + "\"," +
                         "\"" + b.getAuthor() + "\"," +
                         "\"" + b.getCategory() + "\"," +
                         "\"" + status + "\"");
                bw.newLine();
            }

            System.out.println("Books saved successfully to " + filePath);

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
