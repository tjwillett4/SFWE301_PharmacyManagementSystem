package Prescriptions;
import java.util.ArrayList;

/*
 * The system shall store the information about a specific medication, such as drug interactions, 
 * other names, allergy information, and more. 
 */

import InventoryControl.BatchMedication;

public class Medication {
	String name;
	ArrayList<String> brandNames;
	ArrayList<BatchMedication> batches;
	ArrayList<DrugInteraction> interactions;
	
	public Medication() {}
	public Medication(String name) { 
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
			info += "\nArrival date: " + b.getArrivalDate().dateAsString()
					+ "\nExpiration date: " + b.getExpirationDate().dateAsString()
					+ "\nRemainder: " + b.getStock() + "\n";
		}
				
		
		System.out.println(info);
	}
	
	
}
