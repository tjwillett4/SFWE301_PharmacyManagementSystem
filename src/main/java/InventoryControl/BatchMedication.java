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
	public int getStock() {
        return stock.get();
    }
	public void setStock(int stock) {
        this.stock.set(stock);
    }
	public ArrayList<String> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<String> records) {
		this.records = records;
		
	}
	public LocalDate getArrivalDate() {
        return arrivalDate.get();
    }
	public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate.set(arrivalDate);
    }
	public LocalDate getExpirationDate() {
        return expirationDate.get();
    }
	public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate.set(expirationDate);
    }
	// JavaFX property methods
    public String getMedicationName() {
        return medicationName.get();
    }
    public IntegerProperty quantityProperty() {
        return stock;
    }
    public ObjectProperty<LocalDate> expirationDateProperty() {
        return expirationDate;
    }


    public void setMedicationName(String medicationName) {
        this.medicationName.set(medicationName);
    }

    public StringProperty medicationNameProperty() {
        return medicationName;
    }
    public ObjectProperty<LocalDate> arrivalDateProperty() {
        return arrivalDate;
    }
	
	
}
