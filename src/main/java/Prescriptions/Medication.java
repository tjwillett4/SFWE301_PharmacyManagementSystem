package Prescriptions;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * The system shall store the information about a specific medication, such as drug interactions, 
 * other names, allergy information, and more. 
 */

import InventoryControl.BatchMedication;

/**
 * Represents a Medication with details such as name, category, supplier, stock, 
 * brand names, batches, and drug interactions. This class provides properties for
 * GUI binding, methods for managing medication data, and utilities for displaying 
 * and calculating stock information.
 */
public class Medication {
    
    // Fields for medication attributes
    private SimpleStringProperty name; // Name of the medication
    private SimpleStringProperty category; // Category of the medication
    private SimpleStringProperty supplier; // Supplier of the medication
    private SimpleIntegerProperty stock; // Total stock across all batches

    private ArrayList<String> brandNames = new ArrayList<>(); // List of brand names
    private ArrayList<BatchMedication> batches = new ArrayList<>(); // List of medication batches
    private ArrayList<DrugInteraction> interactions = new ArrayList<>(); // List of drug interactions

    /**
     * Default constructor for Medication.
     */
    public Medication() {
        this.name = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.supplier = new SimpleStringProperty();
        this.stock = new SimpleIntegerProperty();
    }

    /**
     * Parameterized constructor for Medication.
     * @param name The name of the medication.
     * @param category The category of the medication.
     * @param supplier The supplier of the medication.
     * @param stock The initial stock of the medication.
     */
    public Medication(String name, String category, String supplier, int stock) {
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.supplier = new SimpleStringProperty(supplier);
        this.stock = new SimpleIntegerProperty(stock);
    }

    /**
     * Retrieves all modification records from the medication's batches.
     * @return A string containing all modification records.
     */
    public String allRecordsToString() {
        StringBuilder res = new StringBuilder();
        for (BatchMedication batch : batches) {
            res.append("Batch ").append(batch.getArrivalDate().toString()).append(" modification records:\n");
            for (String s : batch.getRecords()) {
                res.append(s).append("\n");
            }
        }
        return res.toString();
    }

    // Getter and setter methods for medication attributes

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ArrayList<String> getBrandNames() {
        return brandNames;
    }

    public void setBrandNames(ArrayList<String> brandNames) {
        this.brandNames = brandNames;
    }

    public ArrayList<BatchMedication> getBatches() {
        return batches;
    }

    public void setBatches(ArrayList<BatchMedication> batches) {
        this.batches = batches;
        this.stock.set(entireStockCount());
    }

    public void setSingleBatch(BatchMedication batch) {
        this.batches = new ArrayList<>();
        this.batches.add(batch);
        this.stock.set(entireStockCount());
    }

    public void addSingleBatch(BatchMedication batch) {
        this.batches.add(batch);
        this.stock.set(entireStockCount());
    }

    public ArrayList<DrugInteraction> getInteractions() {
        return interactions;
    }

    public void setInteractions(ArrayList<DrugInteraction> interactions) {
        this.interactions = interactions;
    }

    // GUI-related properties

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public SimpleStringProperty supplierProperty() {
        return supplier;
    }

    public int getStock() {
        return stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    // Stock calculation methods

    public SimpleIntegerProperty entireStockCountSimpleInt() {
        SimpleIntegerProperty sum = new SimpleIntegerProperty(0);
        if (batches == null || batches.isEmpty()) {
            return sum;
        }
        for (BatchMedication b : batches) {
            sum.set(sum.get() + b.getStock());
        }
        return sum;
    }

    public int entireStockCount() {
        int sum = 0;
        if (batches == null || batches.isEmpty()) {
            return sum;
        }
        for (BatchMedication b : batches) {
            sum += b.getStock();
        }
        return sum;
    }

    // Utility methods

    /**
     * Prints detailed information about the medication, including interactions
     * and stock information.
     */
    public void printMedicationInfo() {
        String info =
                "Name: " + name.get() + "\n" +
                "Brand Names: " + brandNames.toString() + "\n" +
                "Drug Interactions:\n";
        for (DrugInteraction i : interactions) {
            info += i.getInteractionWith() + ": " + i.getSideEffects().toString() + "\n";
        }
        info += "\nStock Information:";
        for (BatchMedication b : batches) {
            info += "\nArrival date: " + b.getArrivalDate() +
                    "\nExpiration date: " + b.getExpirationDate() +
                    "\nRemainder: " + b.getStock() + "\n" +
                    allRecordsToString();
        }
        System.out.println(info);
    }
}
