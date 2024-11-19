package Prescriptions;

/*
 * The system shall store the information about a specific medication, such as drug interactions, 
 * other names, allergy information, and more. 
 */

import InventoryControl.BatchMedication;

public class Medication {
	String name;
	String[] brandNames;
	BatchMedication[] batches;
	DrugInteraction[] interactions;
	
	
}
