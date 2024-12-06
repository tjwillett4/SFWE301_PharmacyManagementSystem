package InventoryControl;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
/*
 * List attached to a medication. 
 * This shall contain information about a specific shipment of a medication, meeting the requirement to 
 * check expiration dates. As the "batch" of medication from 6 months ago has a different expiration then the
 * "batch" of medication from this week, but both are in the pharmacy inventory. 
 */

public class BatchMedication {
	private final StringProperty medicationName;
    private final ObjectProperty<LocalDate> arrivalDate;
    private final ObjectProperty<LocalDate> expirationDate;
    private final IntegerProperty stock;
    private ArrayList<String> records;
	
	public BatchMedication() {
		this.medicationName = new SimpleStringProperty();
        this.arrivalDate = new SimpleObjectProperty<>();
        this.expirationDate = new SimpleObjectProperty<>();
        this.stock = new SimpleIntegerProperty();
        this.records = new ArrayList<>();
    }
	
	public BatchMedication(String medicationName, int stock, LocalDate expirationDate) {
        this.medicationName = new SimpleStringProperty(medicationName);
        this.arrivalDate = new SimpleObjectProperty<>(LocalDate.now()); // Defaults to today
        this.expirationDate = new SimpleObjectProperty<>(expirationDate);
        this.stock = new SimpleIntegerProperty(stock);
        this.records = new ArrayList<>();
    }
	
	public long daysToExpiration(LocalDate currentDate) {
	    return currentDate.until(expirationDate.get(), ChronoUnit.DAYS);
	}
	public String recordsAsString() {
		String res = "";
		for (String s : records)
			res += s + "\n";
		return res;
	}
	
	public boolean updateStock(int newStock, String name) {
        if (newStock < 0) {
            return false;
        }
        if (records == null) {
            records = new ArrayList<>();
        }
        String edit;
        int currentStock = stock.get(); // Extract the integer value from IntegerProperty
        if (newStock < currentStock) {
            edit = "removed " + (currentStock - newStock) + " from";
        } else {
            edit = "added " + (newStock - currentStock) + " to";
        }

        String item = LocalDate.now() + ": " + edit + " the batch by " + name + ".";
        records.add(item);
        this.stock.set(newStock); // Update the IntegerProperty
        return true;
    }
	/**
	 * Getter and setter methods for managing stock, records, arrival date, expiration date, and medication properties.
	 * Includes JavaFX property methods for binding to UI elements.
	 */

	// Gets the current stock level
	public int getStock() {
	    return stock.get();
	}

	// Sets the stock level
	public void setStock(int stock) {
	    this.stock.set(stock);
	}

	// Gets the modification records associated with the medication
	public ArrayList<String> getRecords() {
	    return records;
	}

	// Sets the modification records for the medication
	public void setRecords(ArrayList<String> records) {
	    this.records = records;
	}

	// Gets the arrival date of the medication batch
	public LocalDate getArrivalDate() {
	    return arrivalDate.get();
	}

	// Sets the arrival date for the medication batch
	public void setArrivalDate(LocalDate arrivalDate) {
	    this.arrivalDate.set(arrivalDate);
	}

	// Gets the expiration date of the medication batch
	public LocalDate getExpirationDate() {
	    return expirationDate.get();
	}

	// Sets the expiration date for the medication batch
	public void setExpirationDate(LocalDate expirationDate) {
	    this.expirationDate.set(expirationDate);
	}

	// Gets the medication name
	public String getMedicationName() {
	    return medicationName.get();
	}

	// Sets the medication name
	public void setMedicationName(String medicationName) {
	    this.medicationName.set(medicationName);
	}

	// Returns the stock as a JavaFX property for UI bindings
	public IntegerProperty quantityProperty() {
	    return stock;
	}

	// Returns the expiration date as a JavaFX property for UI bindings
	public ObjectProperty<LocalDate> expirationDateProperty() {
	    return expirationDate;
	}

	// Returns the medication name as a JavaFX property for UI bindings
	public StringProperty medicationNameProperty() {
	    return medicationName;
	}

	// Returns the arrival date as a JavaFX property for UI bindings
	public ObjectProperty<LocalDate> arrivalDateProperty() {
	    return arrivalDate;
	}
	
	
}
