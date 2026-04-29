package managers;

import model.LibraryItemADT;
import java.util.*;

/**
 * Generic class that manages any type extending LibraryItemADT
 * From: University Assignment - CourseManager<T extends Course>
 */
public class BookManager<T extends LibraryItemADT> {
    private ArrayList<T> items;
    
    public BookManager() {
        items = new ArrayList<>();
    }
    
    public void addItem(T item) {
        items.add(item);
        System.out.println("Added: " + item.getTitle());
    }
    
    public boolean removeItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                System.out.println("Removed item with ID: " + id);
                return true;
            }
        }
        System.out.println("Item with ID " + id + " not found.");
        return false;
    }
    
    public T getItemWithHighestLateFee() {
        if (items.isEmpty()) return null;
        
        T highest = items.get(0);
        for (T item : items) {
            if (item.calculateLateFee(1) > highest.calculateLateFee(1)) {
                highest = item;
            }
        }
        return highest;
    }
    
    public void sortItemsByLateFee() {
        items.sort(Comparator.comparingDouble(item -> item.calculateLateFee(1)));
        System.out.println("Items sorted by late fee (ascending).");
    }
    
    public void printAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in collection.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.printf("| %-6s | %-35s | %-12s |\n", "ID", "Title", "Fee/Day");
        System.out.println("-".repeat(70));
        
        for (T item : items) {
            String title = item.getTitle();
            if (title.length() > 35) title = title.substring(0, 32) + "...";
            
            System.out.printf("| %-6d | %-35s | $%-11.2f |\n",
                item.getId(), title, item.calculateLateFee(1));
        }
        System.out.println("=".repeat(70));
    }
    
    public int getSize() {
        return items.size();
    }
}