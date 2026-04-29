package model;

/**
 * ADT Interface for Library System
 * Ensures Information Hiding - internal data structures are hidden from user
 * From: Smart Library Project (ADT Designer task)
 */
public interface LibraryItemADT {
    
    /**
     * Returns unique identifier (ISBN/BID)
     */
    int getId();
    
    /**
     * Returns book title
     */
    String getTitle();
    
    /**
     * Returns author name
     */
    String getAuthor();
    
    /**
     * Returns book category
     */
    String getCategory();
    
    /**
     * Returns availability status
     */
    String getStatus();
    
    /**
     * Updates availability status
     */
    void setStatus(String status);
    
    /**
     * Prints book details in formatted manner
     * From: University assignment printCourseDetails()
     */
    void printDetails();
    
    /**
     * Calculates late fee (polymorphism - different for different book types)
     * From: University assignment calculateTotalWorkload()
     */
    double calculateLateFee(int daysLate);
    
    /**
     * Returns string representation for file saving
     */
    String toCSVString();
}