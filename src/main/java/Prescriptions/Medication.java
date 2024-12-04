package Prescriptions;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * The system shall store the information about a specific medication, such as drug interactions, 
 * other names, allergy information, and more. 
 */

import InventoryControl.BatchMedication;

public class Medication {
	private SimpleStringProperty name;
    private SimpleStringProperty category;
    private SimpleStringProperty supplier;
    private SimpleIntegerProperty stock;
    private ArrayList<String> brandNames = new ArrayList<>();
    private ArrayList<BatchMedication> batches = new ArrayList<>();
    private ArrayList<DrugInteraction> interactions = new ArrayList<>();
	
    public Medication() {
        this.name = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.supplier = new SimpleStringProperty();
        this.stock = new SimpleIntegerProperty();
    }
    
    public Medication(String name, String category, String supplier, int stock) {
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.supplier = new SimpleStringProperty(supplier);
        this.stock = new SimpleIntegerProperty(stock);
    }
    
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
	}
	public void setSingleBatch(BatchMedication batch) {
		this.batches = new ArrayList<BatchMedication>();
		this.batches.add(batch);
	}
	public ArrayList<DrugInteraction> getInteractions() {
		return interactions;
	}
	public void setInteractions(ArrayList<DrugInteraction> interactions) {
		this.interactions = interactions;
	}
	
	
	//added for GUI development
	public SimpleStringProperty nameProperty() {
        return name;
    }
	public String getCategory() {
	    return category.get();
	}
	public SimpleStringProperty categoryProperty() {
	    return category;
	}
	public void setCategory(String category) {
        this.category.set(category);
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
	
	public void printMedicationInfo() 
	{
		String info = 
				name + "\n" +
				brandNames.toString() + "\n\n" +
				"Drug Interactions:\n";
		for (DrugInteraction i : interactions) {
			info += i.getInteractionWith() + ": " + i.getSideEffects().toString();
		}
		info += "\n Stock information";
		for (BatchMedication b : batches) {
			info += "\nArrival date: " + b.getArrivalDate().toString()
					+ "\nExpiration date: " + b.getExpirationDate().toString()
					+ "\nRemainder: " + b.getStock() + "\n"
					+ allRecordsToString();
		}
				
		
		System.out.println(info);
	}
	
	
}
